PA = .

VS_TRAIN = $(PA)/RCV1.very_small_train.txt
VS_TEST = $(PA)/RCV1.very_small_test.txt

S_TRAIN = $(PA)/RCV1.small_train.txt
S_TEST = $(PA)/RCV1.small_test.txt

FULL_TRAIN = $(PA)/RCV1.full_train.txt
FULL_TEST = $(PA)/RCV1.full_test.txt

.SUFFIXES: .java .class 

.java.class:
	javac $<

CLASSES = NB.class NBTrain.class NBTest.class

all: $(CLASSES)

NBTrain: NBTrain.class

NBTest: NBTest.class

demo: NBTrain NBTest
	cat $(VS_TRAIN) | java NBTrain | java NBTest -t $(VS_TEST)

demo_small: NBTrain NBTest
	cat $(S_TRAIN) | java NBTrain | java NBTest -t $(S_TEST)

demo_full: NBTrain NBTest
	cat $(FULL_TRAIN) | java NBTrain | java NBTest -t $(FULL_TEST)

test: NBTrain NBTest
	cat $(VS_TRAIN) | java NBTrain | java NBTest -t $(TESTFILE)

clean:
	rm $(CLASSES) *~
