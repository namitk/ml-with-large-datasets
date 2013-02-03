import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class NBTest extends NB {
    HashSet<String> labels;
    HashSet<String> vocab;
    HashMap<String, Integer> counts;
    final double smoothing_constant = 1.0;

    public NBTest() {
        labels = new HashSet<String>();
        vocab = new HashSet<String>();
        counts = new HashMap<String, Integer>();
    }

    public void readCounts() {
        String curLine;
        Scanner s = new Scanner(System.in);
        while (s.hasNext()) {            
            String key = s.next();
            // System.out.println(key);
            if(!key.equals("numSamples") && !key.contains(";")){
                labels.add(key);
            } else if(key.contains(";")){
                String[] tok = key.split(";");
                assert tok.length==2;
                if(!tok[1].equals("*")){
                    vocab.add(tok[1]);
                }
            }
            int count = s.nextInt();
            counts.put(key, count);
        }
    }

    public int correctlyLabeled(String predLabel, String trueLabel){
        if(trueLabel.contains(predLabel)){
            return 1;
        } else {
            return 0;
        }
    }
    
    public double test(String filename) throws FileNotFoundException {
        String curLine;
        readCounts();
        Scanner s = new Scanner(new FileReader(filename));
        int corLabeled = 0;
        int totalExamples = 0;
        while (s.hasNextLine()) {
            totalExamples++;
            curLine = s.nextLine();
            ArrayList<String> tokens = tokenizeDoc(curLine);
            String trueLabel = tokens.get(0);
            double max = Double.NEGATIVE_INFINITY;
            String maxLabel = "";
            for (String label : labels) {                
                double prob = calcLogProb(tokens, label);
                if(prob>max){
                    max = prob;
                    maxLabel = label;
                }
            }            
            corLabeled += correctlyLabeled(maxLabel, trueLabel);
            System.out.println("["+trueLabel+"]\t"+maxLabel+"\t"+max);            
        }
        return corLabeled*100.0 / totalExamples;
    }

    private double calcLogProb(ArrayList<String> tokens, String label) {
        int numLabels = labels.size();
        int vocab_size = vocab.size();
        double numer = counts.get(label)+smoothing_constant;
        double denom = counts.get("numSamples")+smoothing_constant*numLabels;
        double logProb = Math.log(numer/denom);
        for (int i = 1; i < tokens.size(); i++) { //tokens[0] contains true label
            String key = label+";"+tokens.get(i);
            numer = (counts.containsKey(key) ? counts.get(key) : 0)+smoothing_constant;
            denom = counts.get(label) + vocab_size;
            logProb += Math.log(numer/denom);
        }
        return logProb;
    }        
    
    public static void main(String[] args){
        NBTest nbtest = new NBTest();        
        try {
            String filename = "";
            for (int i = 0; i < args.length; i+=2) {
                if(args[i].equals("-t")){
                    filename = args[i+1];
                }
            }
            if(!filename.equals("")){
                System.out.println("Percent Correct = " + nbtest.test(filename));
            }            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}