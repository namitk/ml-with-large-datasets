DIR = .

ATINY_TRAIN = $(DIR)/abstract.tiny.train
ATINY_TEST = $(DIR)/abstract.tiny.test

ASMALL_TRAIN = $(DIR)/abstract.small.train
ASMALL_TEST = $(DIR)/abstract.small.test

AFULL_TRAIN = $(DIR)/abstract.train
AFULL_TEST = $(DIR)/abstract.test

TINYLINES = 106
SMALLLINES = 11272
FULLLINES = 1130442

DSIZE ?= 10000
MU ?= 0.1

JAVA = java
JFLAGS = # -Xmx128m

.SUFFIXES: .java .class 

.java.class:
	javac $<

CLASSES = SGDHeader.class SGDTrain.class SGDTest.class

all: $(CLASSES)

SGDHeader: SGDHeader.class

SGDTrain: SGDTrain.class

SGDTest: SGDTest.class

demo: SGDHeader SGDTrain SGDTest
	for i in `seq 1 20`; do shuf $(ATINY_TRAIN); done | $(JAVA) $(JFLAGS) SGDTrain -ne $(TINYLINES) -ds $(DSIZE) -mu $(MU) -tf $(ATINY_TRAIN) | $(JAVA) SGDTest -t $(ATINY_TEST) -ds $(DSIZE)

demo_asmall: SGDHeader SGDTrain SGDTest
	for i in `seq 1 20`; do shuf $(ASMALL_TRAIN); done | $(JAVA) $(JFLAGS) SGDTrain -ne $(SMALLLINES) -ds $(DSIZE) -mu $(MU) -tf $(ASMALL_TRAIN) | $(JAVA) SGDTest -t $(ASMALL_TEST) -ds $(DSIZE)

demo_afull: SGDHeader SGDTrain SGDTest
	for i in `seq 1 20`; do shuf $(AFULL_TRAIN); done | $(JAVA) $(JFLAGS) SGDTrain -ne $(FULLLINES) -ds $(DSIZE) -mu $(MU) -tf $(AFULL_TRAIN) | $(JAVA) SGDTest -t $(AFULL_TEST) -ds $(DSIZE)

test: SGDHeader SGDTrain SGDTest
	for i in `seq 1 20`; do shuf $(ATINY_TRAIN); done | $(JAVA) $(JFLAGS) SGDTrain -ne $(TINYLINES) -ds $(DSIZE) -mu $(MU) -tf $(ATINY_TRAIN) | $(JAVA) SGDTest -t $(TESTFILE) -ds $(DSIZE)

clean:
	rm $(CLASSES)