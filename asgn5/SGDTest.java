import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SGDTest extends SGDHeader {
	BufferedReader br;
	double[][] classifier;

	public SGDTest(String testFile, int dictionarySize) throws FileNotFoundException {
		super(dictionarySize);
		br = new BufferedReader(new FileReader(testFile));
		classifier = new double[NUM_LABELS][DICTIONARY_SIZE + 1];
		Scanner s = new Scanner(System.in);
		for (int i = 0; i < NUM_LABELS; i++) {
			for (int j = 0; j < DICTIONARY_SIZE + 1; j++) {
				classifier[i][j] = s.nextDouble();
			}
		}
	}

	public void test() {
		String line;
		int[] correctlyLabeled = new int[NUM_LABELS];
		try {
			long numExamplesSeen = 0;
			while ((line = br.readLine()) != null) {
				numExamplesSeen++;
				ArrayList<String> tokens = tokenizeDoc(line);
				int[] trueLabels = belongsToWhichClass(tokens.get(0));
				HashMap<Integer, Integer> featureVector = createFeatureVector(tokens);
				int[] labels = classifyExample(featureVector);
				for (int i = 0; i < NUM_LABELS; i++) {
					correctlyLabeled[i] += ((trueLabels[i] == labels[i]) ? 1 : 0);
				}
			}
			reportAverageAccuracy(correctlyLabeled, numExamplesSeen);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void reportAverageAccuracy(int[] correctlyLabeled,
			long numExamplesSeen) {
		double sum = 0.0;
		for (int i = 0; i < NUM_LABELS; i++){
			// System.out.println(correctlyLabeled[i] * 100.0 / numExamplesSeen);
			sum += correctlyLabeled[i] * 1.0 / numExamplesSeen;
		}
		System.out.println(sum * 100 / NUM_LABELS);
	}

	private int[] classifyExample(HashMap<Integer, Integer> featureVector) {
		double score = 0.0;
		int[] labels = new int[NUM_LABELS];
		for (int i = 0; i < NUM_LABELS; i++) {
			score = getScore(featureVector, classifier[i]);
			labels[i] = score > 0 ? 1 : 0;
		}
		return labels;
	}

	public static void main(String[] args) {
		String testFile = "";
		int dictionarySize = 0;
		for (int i = 0; i < args.length; i += 2) {
			if (args[i].equals("-t")) {
				testFile = args[i + 1];
			} else if (args[i].equals("-ds")) {
				dictionarySize = Integer.parseInt(args[i + 1]);
			}
		}
		SGDTest sgdtest;
		try {
			sgdtest = new SGDTest(testFile, dictionarySize);
			sgdtest.test();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
