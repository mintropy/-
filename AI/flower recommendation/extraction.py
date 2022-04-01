import re
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import RegexpTokenizer
from nltk.stem.porter import PorterStemmer

#문장 가져오기
f = open("./test.txt",'rt',encoding='utf-8')
lines2 = f.readlines()
line2 = []
for i in range(len(lines2)):
    line2.append(lines2[i])
f.close()

#소문자로 내림
compile = re.compile("\W+")
for i in range(len(line2)):

    a = compile.sub(" ",line2[i])
    line2[i] = a.lower()

stop_word_eng = set(stopwords.words('english'))
line2 = [i for i in line2 if i not in stop_word_eng]
print(line2)

ps_stemmer = PorterStemmer()
token = RegexpTokenizer('[\w]+')
result2 = [token.tokenize(i) for i in line2]
middle_result2= [r for i in result2 for r in i]
final_result2 = [ps_stemmer.stem(i) for i in middle_result2 if not i in stop_word_eng] # 불용어 제거
print(final_result2)