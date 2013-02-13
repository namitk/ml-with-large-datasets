
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UnigramMessaging extends PhraseHeader {

    private BufferedReader br;

    public void createUnigramCountMessages(String uniFile) {
        try {
            String line;
            long bVocabCount = 0, fVocabCount = 0;
            long bVocab = 0, fVocab=0;
            long bgCount = 0, fgCount = 0;
            br = new BufferedReader(new FileReader(uniFile));
            String prevWord = "";
            String[] tok;
            long count;
            boolean addedToBVocab = false, addedToFVocab = false;
            while (true) {
                line = br.readLine();
                if (line == null || line.equals("")) {
                    System.out.println(prevWord + MYDELIM + createUnigramValueString(bgCount, fgCount));
                    System.out.println(UNIGRAMVOCABMSG + MYDELIM + createUnigramValueString(bVocabCount, fVocabCount));
                    System.out.println(UNIGRAMVOCABSIZE + MYDELIM + createUnigramValueString(bVocab, fVocab));
                    break;
                }
                tok = line.split(FILEDELIM);
                assert tok.length == 3;
                if (!tok[0].equals(prevWord)) {
                    if (!prevWord.equals("")) {
                        System.out.println(prevWord + MYDELIM + createUnigramValueString(bgCount, fgCount));
                        bgCount = 0;
                        fgCount = 0;

                    }
                    addedToBVocab = false;
                    addedToFVocab = false;
                    prevWord = tok[0];
                }
                count = Long.parseLong(tok[2]);
                if (bgDecades.contains(tok[1])) {
                    if (!addedToBVocab) {
                        bVocab++;
                        addedToBVocab = true;
                    }
                    bVocabCount += count;
                    bgCount += count;
                } else {
                    if (!addedToFVocab) {
                        fVocab++;
                        addedToFVocab = true;
                    }
                    fVocabCount += count;
                    fgCount += count;
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
            String prevPhrase = "";
            String[] tok;
            while (true) {
                line = br.readLine();
                if (line == null || line.equals("")) {
                    tok = prevPhrase.split(" ");
                    assert tok.length == 2;
                    System.out.println(tok[0] + MYDELIM + prevPhrase);
                    System.out.println(tok[1] + MYDELIM + prevPhrase);
                    break;
                }
                tok = line.split(FILEDELIM);
                String phrase = tok[0];
                if (!phrase.equals(prevPhrase)) {
                    if (!prevPhrase.equals("")) {
                        tok = prevPhrase.split(" ");
                        assert tok.length == 2;
                        System.out.println(tok[0] + MYDELIM + prevPhrase);
                        System.out.println(tok[1] + MYDELIM + prevPhrase);
                    }
                    prevPhrase = phrase;
                    // System.out.println(prevPhrase);
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