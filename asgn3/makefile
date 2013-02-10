DATAD = ../asgn3_data

SMALLU = $(DATAD)/unigram_apple.txt 
SMALLB = $(DATAD)/bigram_apple.txt

FULLU = $(DATAD)/unigram_full.txt
FULLB = $(DATAD)/bigram_full.txt

JAVA = java
JFLAGS = -Xmx128m

.SUFFIXES: .java .class 

.java.class:
	javac $<

CLASSES = PhraseHeader.class UnigramMessaging.class BigramMessaging.class PhrasinessInformativeness.class

all: $(CLASSES)

PHeader: PhraseHeader.class
UMsg: UnigramMessaging.class
BMsg: BigramMessaging.class
PI: PhrasinessInformativeness.class

l1: PHeader UMsg
	$(JAVA) $(JFLAGS) UnigramMessaging -u $(SMALLU) -b $(SMALLB)

l2: PHeader UMsg
	$(JAVA) $(JFLAGS) UnigramMessaging -u $(SMALLU) -b $(SMALLB) | sort -k1,1 -t';' 

l3: PHeader UMsg BMsg
	$(JAVA) $(JFLAGS) UnigramMessaging -u $(SMALLU) -b $(SMALLB) | sort -k1,1 -t';' | $(JAVA) $(JFLAGS) BigramMessaging -b $(SMALLB)

demo: PHeader UMsg BMsg PhrasinessInformativeness.class
	$(JAVA) $(JFLAGS) UnigramMessaging -u $(SMALLU) -b $(SMALLB) | sort -k1,1 -t';' | $(JAVA) $(JFLAGS) BigramMessaging -b $(SMALLB) | sort -k1,1 -t';' | $(JAVA) $(JFLAGS) PhrasinessInformativeness

clean:
	rm $(CLASSES)