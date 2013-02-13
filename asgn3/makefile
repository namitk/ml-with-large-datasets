DATAD = ../asgn3_data

SMALLU = $(DATAD)/unigram_apple.txt 
SMALLB = $(DATAD)/bigram_apple.txt

FULLU = $(DATAD)/unigram_full_sorted.txt
FULLB = $(DATAD)/bigram_full_sorted.txt

SORTTEMPDIR = .

JAVA = java
JFLAGS = -Xmx128m

LC_ALL='C'

.SUFFIXES: .java .class 

.java.class:
	javac $<

CLASSES = PhraseHeader.class UnigramMessaging.class BigramMessaging.class PhrasinessInformativeness.class

all: $(CLASSES)

PHeader: PhraseHeader.class
UMsg: UnigramMessaging.class
BMsg: BigramMessaging.class
PI: PhrasinessInformativeness.class

demo: PHeader UMsg BMsg PhrasinessInformativeness.class
	$(JAVA) $(JFLAGS) UnigramMessaging -u $(SMALLU) -b $(SMALLB) | sort -k1,1 -t';' -T $(SORTTEMPDIR) | $(JAVA) $(JFLAGS) BigramMessaging -b $(SMALLB) | sort -k1,1 -t';' -T $(SORTTEMPDIR) | uniq | $(JAVA) $(JFLAGS) PhrasinessInformativeness | sort -r -g -k2,2 -t"	" -T $(SORTTEMPDIR) | head -n 20

full: PHeader UMsg BMsg PhrasinessInformativeness.class
	$(JAVA) $(JFLAGS) UnigramMessaging -u $(FULLU) -b $(FULLB) | sort -k1,1 -t';' -T $(SORTTEMPDIR) | $(JAVA) $(JFLAGS) BigramMessaging -b $(FULLB) | sort -k1,1 -t';' -T $(SORTTEMPDIR) | uniq | $(JAVA) $(JFLAGS) PhrasinessInformativeness | sort -r -g -k2,2 -t"	" -T $(SORTTEMPDIR) | head -n 20

clean:
	rm $(CLASSES)