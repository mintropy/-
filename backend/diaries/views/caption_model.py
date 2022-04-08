# -*- coding: utf-8 -*-
import ast
import numpy as np
import os

os.environ["TF_CPP_MIN_LOG_LEVEL"] = "2"

import tensorflow as tf

from back.settings import BASE_DIR




def cap(image_):
    train_captions = open(
        os.path.join(".", "diaries", "views", "weight4_train_captions.txt"),
        encoding="utf-8",
    )
    train_captions = train_captions.readlines()
    train_captions = ast.literal_eval(train_captions[0])
    top_k = 5000
    tokenizer = tf.keras.preprocessing.text.Tokenizer(
        num_words=top_k, oov_token="<unk>", filters='!"#$%&()*+.,-/:;=?@[\]^_`{|}~'
    )
    tokenizer.fit_on_texts(train_captions)
    tokenizer.word_index["<pad>"] = 0
    tokenizer.index_word[0] = "<pad>"
    train_seqs = tokenizer.texts_to_sequences(train_captions)

    def calc_max_length(tensor):
        return max(len(t) for t in tensor)

    max_length = calc_max_length(train_seqs)
    embedding_dim = 256
    units = 512
    vocab_size = top_k + 1
    attention_features_shape = 64

    class BahdanauAttention(tf.keras.Model):
        def __init__(self, units):
            super(BahdanauAttention, self).__init__()
            self.W1 = tf.keras.layers.Dense(units)
            self.W2 = tf.keras.layers.Dense(units)
            self.V = tf.keras.layers.Dense(1)

        def call(self, features, hidden):
            hidden_with_time_axis = tf.expand_dims(hidden, 1)
            attention_hidden_layer = tf.nn.tanh(
                self.W1(features) + self.W2(hidden_with_time_axis)
            )
            score = self.V(attention_hidden_layer)
            attention_weights = tf.nn.softmax(score, axis=1)
            context_vector = attention_weights * features
            context_vector = tf.reduce_sum(context_vector, axis=1)
            return context_vector, attention_weights

    class CNN_Encoder(tf.keras.Model):
        def __init__(self, embedding_dim):
            super(CNN_Encoder, self).__init__()
            self.fc = tf.keras.layers.Dense(embedding_dim)

        def call(self, x):
            x = self.fc(x)
            x = tf.nn.relu(x)
            return x

    class RNN_Decoder(tf.keras.Model):
        def __init__(self, embedding_dim, units, vocab_size):
            super(RNN_Decoder, self).__init__()
            self.units = units
            self.embedding = tf.keras.layers.Embedding(vocab_size, embedding_dim)
            self.gru = tf.keras.layers.GRU(
                self.units,
                return_sequences=True,
                return_state=True,
                recurrent_initializer="glorot_uniform",
            )
            self.fc1 = tf.keras.layers.Dense(self.units)
            self.fc2 = tf.keras.layers.Dense(vocab_size)
            self.attention = BahdanauAttention(self.units)

        def call(self, x, features, hidden):
            context_vector, attention_weights = self.attention(features, hidden)
            x = self.embedding(x)
            x = tf.concat([tf.expand_dims(context_vector, 1), x], axis=-1)
            output, state = self.gru(x)
            x = self.fc1(output)
            x = tf.reshape(x, (-1, x.shape[2]))
            x = self.fc2(x)
            return x, state, attention_weights

        def reset_state(self, batch_size):
            return tf.zeros((batch_size, self.units))

    encoder2 = CNN_Encoder(embedding_dim)
    encoder2.load_weights(
        os.path.join(BASE_DIR, "diaries", "views", "encoder_model_weights4", "encoder")
    )
    decoder2 = RNN_Decoder(embedding_dim, units, vocab_size)
    decoder2.load_weights(
        os.path.join(BASE_DIR, "diaries", "views", "decoder_model_weights4", "decoder")
    )

    def evaluate2(image):
        attention_plot = np.zeros((max_length, attention_features_shape))
        hidden = decoder2.reset_state(batch_size=1)
        temp_input = tf.expand_dims(load_image(image), 0)
        img_tensor_val = image_features_extract_model(temp_input)
        img_tensor_val = tf.reshape(
            img_tensor_val, (img_tensor_val.shape[0], -1, img_tensor_val.shape[3])
        )
        features = encoder2(img_tensor_val)
        dec_input = tf.expand_dims([tokenizer.word_index["<start>"]], 0)
        result = []
        for i in range(max_length):
            predictions, hidden, attention_weights = decoder2(
                dec_input, features, hidden
            )
            attention_plot[i] = tf.reshape(attention_weights, (-1,)).numpy()
            predicted_id = tf.random.categorical(predictions, 1)[0][0].numpy()
            result.append(tokenizer.index_word[predicted_id])
            if tokenizer.index_word[predicted_id] == "<end>":
                return result, attention_plot
            dec_input = tf.expand_dims([predicted_id], 0)
        attention_plot = attention_plot[: len(result), :]
        return result, attention_plot

    def load_image(image_path):
        img = tf.io.read_file(image_path)
        img = tf.image.decode_jpeg(img, channels=3)
        img = tf.image.resize(img, (299, 299))
        img = tf.keras.applications.inception_v3.preprocess_input(img)
        return img

    image_model = tf.keras.applications.InceptionV3(
        include_top=False, weights="imagenet"
    )
    new_input = image_model.input
    hidden_layer = image_model.layers[-1].output
    image_features_extract_model = tf.keras.Model(new_input, hidden_layer)
    image_url = image_
    result, attention_plot = evaluate2(image_url)
    return " ".join(result[:-1])
