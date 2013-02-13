from nltk.corpus import stopwords
import sys

f = sys.stdin
l = f.readline()
stopwords = set(stopwords.words('english'))
print stopwords
count = 0

while l:
    tok = l.split("\t")
    words = tok[0].split(" ")
    if words[0] not in stopwords and words[1] not in stopwords:
        print l,
        count+=1
        if count>=20:
            exit(0)
    l = f.readline()
            
