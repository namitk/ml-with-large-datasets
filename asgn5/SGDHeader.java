import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SGDHeader {
	int DICTIONARY_SIZE; // number of features
	int NUM_LABELS;
	HashMap<String, Integer> labelIds;
	final double OVERFLOW = 20;
	String[] allLabels = { "ca", "de", "el", "es", "fr", "ga", "hr", "hu",
			"nl", "pl", "pt", "ru", "sl", "tr" };
	
	public SGDHeader(int dictionarySize) {		
		this.DICTIONARY_SIZE = dictionarySize;				
		NUM_LABELS = allLabels.length;
		labelIds = new HashMap<String, Integer>();
		for (int i = 0; i < allLabels.length; i++)
			labelIds.put(allLabels[i], i);
	}

	public ArrayList<String> tokenizeDoc(String curDoc) {
		String[] words = curDoc.split("\\s+");
		ArrayList<String> tokens = new ArrayList<String>();
		tokens.add(words[0]);
		for (int i = 1; i < words.length; i++) {
			words[i] = words[i].replaceAll("\\W", "");
			if (words[i].length() > 0) {
				tokens.add(words[i]);
			}
		}
		return tokens;
	}

	int getHashCode(String word) {
		int hash = word.hashCode() % DICTIONARY_SIZE;
		if (hash < 0)
			hash += DICTIONARY_SIZE;
		return hash;
	}
	
	// 0 to DICTIONARY_SIZE-1 will be mapped to word counts. DICTIONARY_SIZE is
	// mapped to 1
	public HashMap<Integer, Integer> createFeatureVector(
			ArrayList<String> exampleTokens) {		
		HashMap<Integer, Integer> featureVector = new HashMap<Integer, Integer>();
		for (int i = 1; i < exampleTokens.size(); i++) {
			Integer featureId = getHashCode(exampleTokens.get(i)); 
			if (featureVector.containsKey(featureId))
				featureVector.put(featureId, featureVector.get(featureId) + 1);
			else
				featureVector.put(featureId, 1);
		}
		featureVector.put(DICTIONARY_SIZE, 1);
		return featureVector;
	}

	public double getScore(HashMap<Integer, Integer> example,
			double[] classifier) {
		double score = 0.0;
		for (Map.Entry<Integer, Integer> entry : example.entrySet()) {
			int feature = entry.getKey();
			double value = entry.getValue();
			score += value * classifier[feature];
			if (score > OVERFLOW){
				score = OVERFLOW;
				break;
			} else if (score < -OVERFLOW) {
				score = -OVERFLOW;
				break;
			}
		}	
		return score;
	}

	public int[] belongsToWhichClass(String label) {
		String[] posLabels = label.split(",");
		int[] labels = new int[NUM_LABELS];
		Arrays.fill(labels, 0);
		for (int i = 0; i < posLabels.length; i++) {
			labels[labelIds.get(posLabels[i])] = 1;
		}
		return labels;
	}
}
