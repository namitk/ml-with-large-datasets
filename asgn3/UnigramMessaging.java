
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UnigramMessaging extends PhraseHeader {

    private BufferedReader br;

    private String createValueString(long bgCount, long fgCount) {
        return Long.toString(bgCount) + COUNTDELIM + Long.toString(fgCount);
    }

    public void createUnigramCountMessages(String uniFile) {
        try {
            String line;
            long bgCount = 0, fgCount = 0;
            br = new BufferedReader(new FileReader(uniFile));
            line = br.readLine();
            String[] tok = line.split(FILEDELIM);
            assert tok.length == 3;
            String prevWord = tok[0];
            if (bgDecades.contains(tok[1])) {
                bgCount += Long.parseLong(tok[2]);
            } else {
                fgCount += Long.parseLong(tok[2]);
            }
            while (true) {
                line = br.readLine();
                if (line == null || line.equals("")) {
                    System.out.println(prevWord + MYDELIM + createValueString(bgCount, fgCount));
                    break;
                }
                tok = line.split(FILEDELIM);
                assert tok.length == 3;
                if (!tok[0].equals(prevWord)) {
                    System.out.println(prevWord + MYDELIM + createValueString(bgCount, fgCount));
                    prevWord = tok[0];
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

    public void createBigramMessages(String biFile) {
        try {
            String line;
            long bgCount = 0, fgCount = 0;
            br = new BufferedReader(new FileReader(biFile));
            line = br.readLine();
            if (line == null || line.equals("")) {
                return;
            }
            String[] tok = line.split(FILEDELIM);
            String prevPhrase = tok[0];
            tok = prevPhrase.split(" ");
            assert tok.length == 2;
            System.out.println(tok[0] + MYDELIM + prevPhrase);
            System.out.println(tok[1] + MYDELIM + prevPhrase);
            while (true) {
                line = br.readLine();
                if (line == null || line.equals("")) {
                    break;
                }
                tok = line.split(FILEDELIM);
                String phrase = tok[0];
                if (!phrase.equals(prevPhrase)) {
                    tok = phrase.split(" ");
                    assert tok.length == 2;
                    System.out.println(tok[0] + MYDELIM + phrase);
                    System.out.println(tok[1] + MYDELIM + phrase);
                    prevPhrase = phrase;
                } else {
                    // Do nothing
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

    public static void main(String[] args) {
        UnigramMessaging pf = new UnigramMessaging();
        String uniFile = "";
        String biFile = "";
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-u")) {
                uniFile = args[i + 1];
            } else if (args[i].equals("-b")) {
                biFile = args[i + 1];
            }
        }
        pf.createUnigramCountMessages(uniFile);
        pf.createBigramMessages(biFile);
    }
}
