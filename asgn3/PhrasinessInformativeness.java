
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PhrasinessInformativeness extends PhraseHeader {

    BufferedReader br;

    private double calculatePhrasiness(HashMap<String, Long> map) {
        return 0.0;
    }

    private double calculateInformativeness(HashMap<String, Long> map) {
        return 0.0;
    }

    private double calculateTotalScore(HashMap<String, Long> map) {
        return 0.0;
    }

    public void analyzePhrases() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            HashMap<String, Long> map;
            String[] lines = new String[3];
            while (true) {
                map = new HashMap<String, Long>();
                // caution: Hardcoding. Assume there are 3 lines per phrase
                lines[0] = br.readLine();
                if (lines[0] == null || lines[0].equals("")) {
                    // Do something
                    break;
                }
                lines[1] = br.readLine();
                lines[2] = br.readLine();
                String phrase = "";
                for (int i = 0; i < 3; i++) {
                    String[] tok = lines[0].split(MYDELIM);
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
                double totalScore = calculateTotalScore(map);
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
