import csv
f = open("flower.csv", "w")

f_name = ["yellow chrysanthemum", "red chrysanthemum", "purple chrysanthemum", "red rose", "blue rose", "yellow rose", "orange tulip", "purple tulip", "pink tulip", "pink hydrangea", "purple hydrangea", "blue hydrangea", "sunflower", "clover", "Forsythia", "cherry blossom", "lily", "freesia", "Cosmos", "azalea", "Mugunghwa", "dandelion", "lotus"]
f_color = ["yellow", "red", "purple", "red", "blue", "yellow", "orange", "purple", "pink", "pink", "purple", "blue", "yellow", "green", "yellow", "pink", "white", "yellow", "pink", "pink", "pink", "yellow", "pink"]
f_language = ["disappointment, crush", "I love you", "Everything about me to you", "Love, beauty", "Miracle, Love that never gives up", "Breakups, unchanging love", "shyness", "Everlasting love", "The beginning of love, caring for love", "a girl's dream", "Sincerely, capricious", "cold, arrogant, heartless", "yearning, worship, waiting", "Promise, good luck, peace", "hope", "beauty", "Pure love", "Pure, friendship", "pure", "Love, joy", "Tenacity", "The apostle of love, happiness, immortality", "estranged love"]
f_property = ["negative", "positive", "positive", "positive", "positive", "negative", "positive", "positive", "positive", "positive", "positive", "negative", "positive", "positive", "positive", "positive", "positive", "positive", "positive", "positive", "positive", "positive", "positive"]


for i in range(len(f_name)):
    f.write(f_name[i] + ',' + f_color[i] + ',' + f_language[i] + ',' + f_property[i] + '\n')

f.close()