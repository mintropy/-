import nltk
import pandas as pd
from rake_nltk import Rake
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer
from rake_nltk import Rake
import nltk
import random


# nltk.download("popular")

def recommend(caption):
    df = pd.read_csv("./diaries/views/flower.csv")
    captioned_st = caption
    r = Rake()

    new_data= {'f_name': captioned_st, 'f_color' :'n', 'f_language': captioned_st, 'f_property': 'n'}
    df= df.append(new_data, ignore_index=True)
    
    #꽃말에서 키워드 추출
    df['Key_words']=''
    
    #행에 대해 순환 반복
    for index, row in df.iterrows():
        r.extract_keywords_from_text(row['f_language']) #꽃말의 키워드 추출
        key_words_dict_scores = r.get_word_degrees()  #관련성이 높은 단어 
        row['Key_words'] = list(key_words_dict_scores.keys()) #새 열 키워드에 키워드 목록 할당
    
    #df['f_name'] = df['f_name'].map(lambda x: x.split(','))
    # df['f_color'] = df['f_color'].map(lambda x: x.split(',')[:3])
    # df['f_property'] = df['f_property'].map(lambda x: x.split(','))
    for index, row in df.iterrows():
        #row['f_name'] = [x.lower().replace(' ','') for x in row['f_name']]
        row['f_color'] = [x.lower().replace(' ','') for x in row['f_color']]
        row['f_property'] = [x.lower().replace(' ','') for x in row['f_property']]
    
    #열 속성을 Merge_of_words에 결합하여 단어 표현 생성
    df['Merge_of_words'] = ''
    columns = ['f_color', 'f_property', 'Key_words']
    for index, row in df.iterrows():
        words = ''
        for col in columns:
            words += ' '.join(row[col]) + ' '
        row['Merge_of_words'] = words

    df["Merge_of_words"] = ""
    columns = ["f_color", "f_property", "Key_words"]
    for _, row in df.iterrows():
        words = ""
        for col in columns:
            words += " ".join(row[col]) + " "
        row["Merge_of_words"] = words
    df = df[["f_name", "Merge_of_words"]]

    count = CountVectorizer()
    count_matrix = count.fit_transform(df["Merge_of_words"])
    cosine_sim = cosine_similarity(count_matrix, count_matrix)
    indices = pd.Series(df["f_name"])
    recommended_flower = []
    idx = indices[indices == captioned_st].index[0]
    score_series = pd.Series(cosine_sim[idx]).sort_values(ascending = False)
    top_indices = list(score_series.iloc[1:6].index)
    rv=random.choice(top_indices)
    
    recommended_flower.append(list(df['f_name'])[rv])
          
    flower_num=df.index[df['f_name']==recommended_flower[0]].tolist()[0]      
          
    return flower_num

# print(recommend("Everything is good")) 
