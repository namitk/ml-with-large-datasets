import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SGDTrain extends SGDHeader {

	HashMap<Integer, Integer> featureVector;
	int[] labels;

	int numExamples;
	int numIterationsForTraining;
	double regularizationConstant;
	double initialLearningRate;
	final int numIterations = 20;	

	String trainFile = "";
	
	public SGDTrain(int numExamples, int dictionarySize, double mu, String trainFile) {
		super(dictionarySize);
		this.numExamples = numExamples;		
		this.regularizationConstant = mu;
		this.trainFile = trainFile;
		initialLearningRate = 0.5;
		regularizationConstant = 0.05;
		featureVector = new HashMap<Integer, Integer>();
		labels = new int[NUM_LABELS];
	}

	public double[][] train() throws FileNotFoundException {
		double[][] classifier = new double[NUM_LABELS][DICTIONARY_SIZE + 1];
		int[][] timeOfLastUpdate = new int[NUM_LABELS][DICTIONARY_SIZE + 1];

		double curLearningRate = initialLearningRate;
		int timeInstant = 0;
		/* Also, need to normalize all features to have values between 0 and 1 */

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		/*BufferedReader br = new BufferedReader(
				new FileReader(
						"/media/sdb2/Users/namit/Documents/curriculum/spring13/10605_mlld/NBTrain/asgn2_data/abstract.tiny.train"));*/
		String line;

		try {
			for (int t = 1; t <= numIterations; t++) {				
				for (int count = 0; count < numExamples; count++) {
					line = br.readLine();
					curLearningRate = initialLearningRate / (t * t);

					ArrayList<String> tokens = tokenizeDoc(line);
					featureVector = createFeatureVector(tokens);
					// the current example is a positive example for given
					// classes
					// and negative for the rest
					labels = belongsToWhichClass(tokens.get(0));

					timeInstant++;
					for (int i = 0; i < NUM_LABELS; i++) {
						double logisticTerm = calculateLogisticTerm(
								featureVector, classifier[i]);
						for (Map.Entry<Integer, Integer> entry : featureVector
								.entrySet()) {
							// for(int j=1; j<tokens.size(); j++) {
							int wordId = entry.getKey(); // getHashCode(tokens.get(j));
							int wordCount = entry.getValue(); // 1
							classifier[i][wordId] *= raisePower(
									(1 - 2 * curLearningRate
											* regularizationConstant),
									timeInstant - timeOfLastUpdate[i][wordId]);
							classifier[i][wordId] += curLearningRate
									* (labels[i] - logisticTerm)
									* wordCount;
							timeOfLastUpdate[i][wordId] = timeInstant;
						}
					}
				}
				System.err.println("Iteration " + t + " completed. Log likelihood = " + calcLCL(classifier));				
			}
			for (int i = 0; i < NUM_LABELS; i++) {
				for (int j = 0; j < DICTIONARY_SIZE + 1; j++) {
					classifier[i][j] *= raisePower((1 - 2 * curLearningRate
							* regularizationConstant), timeInstant
							- timeOfLastUpdate[i][j]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return classifier;
	}

	private double calcLCL(double[][] classifier) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(trainFile));
			double logLikelihood = 0.0;
			String line = "";
			for (int i=0; i<numExamples; i++) {
				line = reader.readLine();
				ArrayList<String> tokens = tokenizeDoc(line);
				HashMap<Integer, Integer> featureVector = createFeatureVector(tokens);
				HashSet<String> myLabels = new HashSet<String>(Arrays.asList(tokens.get(0).split(",")));
				for (int j=0; j<NUM_LABELS; j++) {
					if (myLabels.contains(allLabels[j])) {
						logLikelihood += Math.log(calculateLogisticTerm(featureVector, classifier[j]));
					} else {
						logLikelihood += Math.log(1-calculateLogisticTerm(featureVector, classifier[j]));
					}
				}
			}
			return logLikelihood;
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return -1.0;
	}

	private double raisePower(double a, int b) {
		if (b == 0)
			return 1;
		else if (b == 1)
			return a;
		else if (b % 2 == 0)
			return raisePower(a * a, b / 2);
		return a * raisePower(a * a, (b - 1) / 2);
	}

	private double calculateLogisticTerm(HashMap<Integer, Integer> example,
			double[] classifier) {
		double exp = Math.exp(getScore(example, classifier));
		return exp / (1 + exp);
	}

	public static void main(String[] args) throws FileNotFoundException {
		int numExamples = 0;
		int dictionarySize = 0;		
		double mu = 0.;
		String trainFile = "";
		for (int i = 0; i < args.length; i += 2) {
			if (args[i].equals("-ne")) {
				numExamples = Integer.parseInt(args[i + 1]);
			} else if (args[i].equals("-ds")) {
				dictionarySize = Integer.parseInt(args[i + 1]);
			} else if (args[i].equals("-mu")) {
				mu = Double.parseDouble(args[i+1]);
			} else if(args[i].equals("-tf")) {
				trainFile = args[i+1];
			}
		}
		SGDTrain sgd = new SGDTrain(numExamples, dictionarySize, mu, trainFile);
		double[][] classifier = sgd.train();
		for (int i = 0; i < sgd.NUM_LABELS; i++) {
			for (int j = 0; j < sgd.DICTIONARY_SIZE + 1; j++) {
				System.out.print(classifier[i][j] + " ");
			}
			System.out.println();
		}
	}
}