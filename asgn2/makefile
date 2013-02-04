ATINY_TRAIN = ../asgn2_data/abstract.tiny.train
ATINY_TEST = ../asgn2_data/abstract.tiny.test

ASMALL_TRAIN = ../asgn2_data/abstract.small.train
ASMALL_TEST = ../asgn2_data/abstract.small.test

AFULL_TRAIN = ../asgn2_data/abstract.train
AFULL_TEST = ../asgn2_data/abstract.test

LTINY_TRAIN = ../asgn2_data/links.tiny.train
LTINY_TEST = ../asgn2_data/links.tiny.test

LSMALL_TRAIN = ../asgn2_data/links.small.train
LSMALL_TEST = ../asgn2_data/links.small.test

LFULL_TRAIN = ../asgn2_data/links.train
LFULL_TEST = ../asgn2_data/links.test

af1 = ../asgn2_data/xaa
af2 = ../asgn2_data/xab
af3 = ../asgn2_data/xac
af4 = ../asgn2_data/xad

lf1 = ../asgn2_data/laa
lf2 = ../asgn2_data/lab
lf3 = ../asgn2_data/lac
lf4 = ../asgn2_data/lad
lf5 = ../asgn2_data/lae
lf6 = ../asgn2_data/laf
lf7 = ../asgn2_data/lag
lf8 = ../asgn2_data/lah
lf9 = ../asgn2_data/lai
lf10 = ../asgn2_data/laj
lf11 = ../asgn2_data/lak
lf12 = ../asgn2_data/lal
lf13 = ../asgn2_data/lam
lf14 = ../asgn2_data/lan

JAVA = java
JFLAGS = -Xmx128m

MODELFILE = model.out

.SUFFIXES: .java .class 

.java.class:
	javac $<

CLASSES = NB.class NBLargeVocabTrain.class NBLargeVocabTest.class Aggregator.class

all: $(CLASSES)

NBLVTrain: NBLargeVocabTrain.class

NBLVTest: NBLargeVocabTest.class

Aggregator: Aggregator.class

demo: NBLVTrain NBLVTest Aggregator
	cat $(ATINY_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(ATINY_TEST)

demo_asmall: NBLVTrain NBLVTest Aggregator
	cat $(ASMALL_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(ASMALL_TEST)

demo_afull: NBLVTrain NBLVTest Aggregator
	cat $(AFULL_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(AFULL_TEST)

demo_ltiny: NBLVTrain NBLVTest Aggregator
	cat $(LTINY_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(LTINY_TEST)

demo_lsmall: NBLVTrain NBLVTest Aggregator
	cat $(LSMALL_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(LSMALL_TEST)

demo_lfull: NBLVTrain NBLVTest Aggregator
	cat $(LFULL_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(LFULL_TEST)

test: NBLVTrain NBLVTest Aggregator
	cat $(TINY_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator | $(JAVA) NBLargeVocabTest -t $(TESTFILE)

demo_aftrain: NBLVTrain NBLVTest Aggregator
	cat $(AFULL_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator > model.txt
demo_af1: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(af1) < model.txt
demo_af2: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(af2) < model.txt
demo_af3: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(af3) < model.txt
demo_af4: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(af4) < model.txt

demo_lftrain: NBLVTrain NBLVTest Aggregator
	cat $(LFULL_TRAIN) | $(JAVA) $(JFLAGS) NBLargeVocabTrain | sort -k1,1 -t ';' -T . | $(JAVA) $(JFLAGS) Aggregator > lmodel.txt
demo_lf1: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf1) < lmodel.txt
demo_lf2: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf2) < lmodel.txt
demo_lf3: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf3) < lmodel.txt
demo_lf4: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf4) < lmodel.txt
demo_lf5: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf5) < lmodel.txt
demo_lf6: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf6) < lmodel.txt
demo_lf7: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf7) < lmodel.txt
demo_lf8: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf8) < lmodel.txt
demo_lf9: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf9) < lmodel.txt
demo_lf10: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf10) < lmodel.txt
demo_lf11: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf11) < lmodel.txt
demo_lf12: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf12) < lmodel.txt
demo_lf13: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf13) < lmodel.txt
demo_lf14: NBLVTest
	$(JAVA) NBLargeVocabTest -t $(lf14) < lmodel.txt

clean:
	rm $(CLASSES)

