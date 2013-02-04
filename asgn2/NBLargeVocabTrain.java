

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class NBLargeVocabTrain extends NB {

    public void train() throws FileNotFoundException {
        String curLine;
        Scanner s = new Scanner(System.in);        
        // PrintWriter pw = new PrintWriter(vocabFile);
        while (s.hasNextLine()) {            
            curLine = s.nextLine();
            ArrayList<String> tokens = tokenizeDoc(curLine);            
            String[] labels = tokens.get(0).split(",");            
            for (int i = 0; i < labels.length; i++) {
                System.out.println("numSamples 1");
                System.out.println(labels[i]+" 1");
                for (int j = 1; j < tokens.size(); j++) {
                    String word = tokens.get(j);
                    // pw.println(word);
                    System.out.println(word+";"+labels[i]+" 1");
                }
                System.out.println("*;"+labels[i]+" "+(tokens.size()-1));
            }
        }
    }

    public static void main(String[] args) {
        NBLargeVocabTrain nbtrain = new NBLargeVocabTrain();
        try {
            nbtrain.train();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}