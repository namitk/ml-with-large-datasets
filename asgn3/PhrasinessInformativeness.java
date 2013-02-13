
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PhrasinessInformativeness extends PhraseHeader {
    BufferedReader br;
    long[] unigramVocabCount;
    long[] bigramVocabCount;
    long[] unigramVocabSize;
    long[] bigramVocabSize;

    final double SMOOTHING_CONSTANT = 1.0;

    private void populateCounts(BufferedReader br) throws IOException {
        String line = br.readLine();
        System.out.println(line);
        String[] tok = line.split(MYDELIM);
        if(tok[0].equals(UNIGRAMVOCABMSG))
            unigramVocabCount = returnCounts(tok[1]);
        else if(tok[0].equals(BIGRAMVOCABMSG))
            bigramVocabCount = returnCounts(tok[1]);
        else if (tok[0].equals(UNIGRAMVOCABSIZE))
            unigramVocabSize = returnCounts(tok[1]);
        else if (tok[0].equals(BIGRAMVOCABSIZE))
            bigramVocabSize = returnCounts(tok[1]);
    }

    public PhrasinessInformativeness() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            // first four lines assumed to be the vocab sizes. Hardcoded :(
            for (int i=0; i<4; i++) {
                populateCounts(br);
            }
            /* System.out.println(unigramVocabCount[0]+" "+unigramVocabCount[1]+" "+bigramVocabCount[0]+" "
                    + ""+bigramVocabCount[1]+" "+unigramVocabSize[0]+" "+unigramVocabSize[1]+" "+bigramVocabSize[0]+" "+bigramVocabSize[1]); */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    

    private double calcPointwiseKLD(double p, double q) {
        return p*Math.log(p/q);
    }

    private double calculatePhrasiness(HashMap<String, Long> map) {
        double p = (map.get("F12")*1.0+SMOOTHING_CONSTANT)/(bigramVocabCount[1]+SMOOTHING_CONSTANT*bigramVocabSize[1]);
        double q1 = (map.get("F1")*1.0+SMOOTHING_CONSTANT)/(unigramVocabCount[1]+SMOOTHING_CONSTANT*unigramVocabSize[1]);
        double q2 = (map.get("F2")*1.0+SMOOTHING_CONSTANT)/(unigramVocabCount[1]+SMOOTHING_CONSTANT*unigramVocabSize[1]);
        double q = q1*q2;        
        return calcPointwiseKLD(p, q);
    }

    private double calculateInformativeness(HashMap<String, Long> map) {
        double p = (map.get("F12")*1.0+SMOOTHING_CONSTANT)/(bigramVocabCount[1]+SMOOTHING_CONSTANT*bigramVocabSize[1]);
        double q = (map.get("B12")*1.0+SMOOTHING_CONSTANT)/(bigramVocabCount[0]+SMOOTHING_CONSTANT*bigramVocabSize[0]);
        return calcPointwiseKLD(p, q);
    }

    private double calculateTotalScore(double phrasiness, double informativeness) {
        return phrasiness + informativeness;
    }

    public void analyzePhrases() {
        try {                       
            HashMap<String, Long> map;
            String[] lines = new String[3];
            while (true) {
                map = new HashMap<String, Long>();
                // caution: Hardcoding :( Assuming there are 3 lines per phrase
                lines[0] = br.readLine();
                if (lines[0] == null || lines[0].equals("")) {
                    // Do something
                    break;
                }
                lines[1] = br.readLine();
                lines[2] = br.readLine();
                // System.out.println(lines[0]+" "+lines[1]+" "+lines[2]);
                String phrase = "";
                for (int i = 0; i < 3; i++) {
                    String[] tok = lines[i].split(MYDELIM);
                    assert tok.length == 2;
                    phrase = tok[0];
                    tok = tok[1].split(COUNTDELIM);
                    for (int j = 0; j < 2; j++) {
                        String[] tok1 = tok[j].split("=");
                        map.put(tok1[0], Long.parseLong(tok1[1]));
                    }
                }                
                double phrasiness = calculatePhrasiness(map);
                double informativeness = calculateInformativeness(map);
                double totalScore = calculateTotalScore(phrasiness, informativeness);
                System.out.println(phrase + FILEDELIM + totalScore + FILEDELIM + phrasiness + FILEDELIM + informativeness);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        PhrasinessInformativeness pi = new PhrasinessInformativeness();
        pi.analyzePhrases();
    }
}