import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class NBLargeVocabTest extends NB {
    String testFile;
    HashMap<String, Integer> labels;    

    HashSet<String> neededWords;
    HashMap<String, Integer> counts;

    final double smoothing_constant = 1.0;
    int vocabSize;
    int numSamples;

    public NBLargeVocabTest(String testFile) throws FileNotFoundException {
        this.testFile = testFile;
        labels = new HashMap<String, Integer>();
        neededWords = new HashSet<String>();
        counts = new HashMap<String, Integer>();
    }

    public void buildNeededSet() throws FileNotFoundException {
        Scanner s = new Scanner(new FileReader(testFile));
        while(s.hasNextLine()) {
            String curLine = s.nextLine();
            ArrayList<String> instance = tokenizeDoc(curLine);
            for (int i = 1; i < instance.size(); i++) {
                neededWords.add(instance.get(i));
            }
        }
        neededWords.add("*");
    }

    public void readNeededCounts() {
        String curLine;
        Scanner s = new Scanner(System.in);
        while (s.hasNext()) {
            String key = s.next();
            int count = s.nextInt();
            // System.out.println(key);
            if(key.contains(";")){
                String[] tok = key.split(";");
                assert tok.length==2;
                if(neededWords.contains(tok[0])){
                    counts.put(key, count);
                }
            } else {
                counts.put(key, count);
                if(key.equals("numSamples")){
                    numSamples = count;
                } else if(key.equals("vocabSize")){
                    vocabSize = count;
                } else{
                    labels.put(key, count);
                }
            }
        }        
    }

    public int correctlyLabeled(String predLabel, String trueLabel){
        if(trueLabel.contains(predLabel)){
            return 1;
        } else {
            return 0;
        }
    }

    public HashSet<String> returnNeededWords(ArrayList<String> instance) {
        HashSet<String> needed = new HashSet<String>();
        for (int i = 1; i < instance.size(); i++) {
            needed.add(instance.get(i));
        }
        return needed;
    }

    public void test() throws FileNotFoundException {
        String curLine;
        buildNeededSet();
        readNeededCounts();
        Scanner s = new Scanner(new FileReader(testFile));
        int corLabeled = 0;
        int totalExamples = 0;
        while (s.hasNextLine()) {
            totalExamples++;
            curLine = s.nextLine();
            ArrayList<String> tokens = tokenizeDoc(curLine);
            String trueLabel = tokens.get(0);
            double max = Double.NEGATIVE_INFINITY;
            String maxLabel = "";
            for (String label : labels.keySet()) {
                double prob = calcLogProb(counts, tokens, label);
                if(prob>max){
                    max = prob;
                    maxLabel = label;
                }
            }
            corLabeled += correctlyLabeled(maxLabel, trueLabel);
            System.out.println("["+trueLabel+"]\t"+maxLabel+"\t"+max);
        }
        System.err.println("Percent Correct = " + corLabeled*100.0 / totalExamples+" ("+corLabeled+"/"+totalExamples+")");
    }

     private double calcLogProb(HashMap<String, Integer> counts, ArrayList<String> tokens, String label) {
        int numLabels = labels.size();
        double numer = labels.get(label)+smoothing_constant;
        double denom = numSamples+smoothing_constant*numLabels;
        double logProb = Math.log(numer/denom);
        for (int i = 1; i < tokens.size(); i++) { //tokens[0] contains true label
            String key = tokens.get(i)+";"+label;
            numer = (this.counts.containsKey(key) ? this.counts.get(key) : 0)+smoothing_constant;
            denom = this.counts.get("*;"+label) + vocabSize;
            logProb += Math.log(numer/denom);
        }
        return logProb;
    }


    public static void main(String[] args){        
        try {
            String testFile = "";
            String modelFile = "";
            for (int i = 0; i < args.length; i+=2) {
                if(args[i].equals("-t")){
                    testFile = args[i+1];
                }
            }
            if(!testFile.equals("")){
                NBLargeVocabTest nbtest = new NBLargeVocabTest(testFile);
                nbtest.test();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}