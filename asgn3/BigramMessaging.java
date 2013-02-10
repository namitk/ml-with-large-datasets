
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BigramMessaging extends PhraseHeader {

    BufferedReader br;

    private long[] returnCounts(String msg) {
        long[] counts = new long[2];
        String tok[] = msg.split(COUNTDELIM);
        counts[0] = Long.parseLong(tok[0]);
        counts[1] = Long.parseLong(tok[1]);
        return counts;
    }

    private void printMessage(String curWord, String phrase, long[] counts) {
        String[] tok = phrase.split(" ");
        if (tok[0].equals(curWord)) {
            System.out.println(phrase + MYDELIM + "B1=" + counts[0] + COUNTDELIM + "F1=" + counts[1]);
        } else {
            assert tok[1].equals(curWord);
            System.out.println(phrase + MYDELIM + "B2=" + counts[0] + COUNTDELIM + "F2=" + counts[1]);
        }
    }

    public void scanMessages() {
        try {
            long bgCount = 0, fgCount = 0;
            br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            // System.err.println(line);
            String[] tok = line.split(MYDELIM);
            String curWord = tok[0];
            long[] counts = returnCounts(tok[1]);
            while ((line = br.readLine()) != null) {
                tok = line.split(MYDELIM);
                if (tok[0].equals(curWord)) {
                    printMessage(curWord, tok[1], counts);
                } else {
                    curWord = tok[0];
                    counts = returnCounts(tok[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            ;
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String createValueString(long bgCount, long fgCount) {
        return "B12=" + Long.toString(bgCount) + COUNTDELIM + "C12=" + Long.toString(fgCount);
    }

    public void createBigramCountMessages(String biFile) {
        try {
            String line;
            long bgCount = 0, fgCount = 0;
            br = new BufferedReader(new FileReader(biFile));
            line = br.readLine();
            String[] tok = line.split(FILEDELIM);
            assert tok.length == 3;
            String prevPhrase = tok[0];
            if (bgDecades.contains(tok[1])) {
                bgCount += Long.parseLong(tok[2]);
            } else {
                fgCount += Long.parseLong(tok[2]);
            }

            while (true) {
                line = br.readLine();
                if (line == null || line.equals("")) {
                    System.out.println(prevPhrase + MYDELIM + createValueString(bgCount, fgCount));
                    break;
                }
                tok = line.split(FILEDELIM);
                assert tok.length == 3;
                if (!tok[0].equals(prevPhrase)) {
                    System.out.println(prevPhrase + MYDELIM + createValueString(bgCount, fgCount));
                    prevPhrase = tok[0];
                    bgCount = 0;
                    fgCount = 0;
                }
                if (bgDecades.contains(tok[1])) {
                    bgCount += Long.parseLong(tok[2]);
                } else {
                    fgCount += Long.parseLong(tok[2]);
                }
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
        BigramMessaging bm = new BigramMessaging();
        String biFile = "";
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-b")) {
                biFile = args[i + 1];
            }
        }
        bm.scanMessages();
        bm.createBigramCountMessages(biFile);
    }
}
