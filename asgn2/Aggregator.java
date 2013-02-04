import java.util.Scanner;

public class Aggregator {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String lastWord = "";
        int vocabSize = 0;
        String previousKey = s.nextLine();
        String[] tok = previousKey.split(";");
        if(tok.length == 2) {
            lastWord = tok[0];
            vocabSize++;
        }
        int sumForPreviousKey = 1;
        String curKey;
        while(s.hasNextLine()) {
            curKey = s.nextLine();
            tok = curKey.split(";");
            if(tok.length == 2 && !tok[0].equals(lastWord)) {
                lastWord = tok[0];
                vocabSize++;
            }
            if(curKey.equals(previousKey)) {
                sumForPreviousKey++;
            } else {
                System.out.println(previousKey+" "+sumForPreviousKey);
                previousKey = curKey;
                sumForPreviousKey = 1;
            }
        }
        System.out.println(previousKey+" "+sumForPreviousKey);
        System.out.println("vocabSize "+vocabSize);
    }
}
