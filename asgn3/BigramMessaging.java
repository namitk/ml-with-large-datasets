
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BigramMessaging extends PhraseHeader {
    BufferedReader br;    

    private void printMessage(String curWord, String phrase, long[] counts) {
        String[] tok = phrase.split(" ");
        if (tok[0].equals(curWord)) {
            System.out.println(phrase + MYDELIM + "B1=" + counts[0] + COUNTDELIM + "F1=" + counts[1]);
        }  
        if (tok[1].equals(curWord)) {
            System.out.println(phrase + MYDELIM + "B2=" + counts[0] + COUNTDELIM + "F2=" + counts[1]);
        }
    }

    public void scanMessages() {
        try {
            long bgCount = 0, fgCount = 0;
            br = new BufferedReader(new InputStreamReader(System.in));
            String line = br.readLine();
            System.out.println(line);
            line = br.readLine();
            System.out.println(line);
            String curWord="";
            String[] tok;
            long[] counts = new long[2];
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
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }   

    public void createBigramCountMessages(String biFile) {
        try {
            String line, prevPhrase="";
            String[] tok;
            long bgCount = 0, fgCount = 0;
            long bVocab = 0, fVocab=0;
            long bVocabCount = 0, fVocabCount = 0;
            long count;
            br = new BufferedReader(new FileReader(biFile));
            boolean addedToBVocab = false, addedToFVocab = false;
            while (true) {
                line = br.readLine();
                if (line == null || line.equals("")) {
                    System.out.println(prevPhrase + MYDELIM + createBigramValueString(bgCount, fgCount));                                      
                    System.out.println(BIGRAMVOCABMSG+MYDELIM+createUnigramValueString(bVocabCount, fVocabCount));
                    System.out.println(BIGRAMVOCABSIZE + MYDELIM + createUnigramValueString(bVocab, fVocab));
                    break;
                }
                tok = line.split(FILEDELIM);
                assert tok.length == 3;
                if (!tok[0].equals(prevPhrase)) {
                    if(!prevPhrase.equals("")) {
                        System.out.println(prevPhrase + MYDELIM + createBigramValueString(bgCount, fgCount));
                        bgCount = 0;
                        fgCount = 0;
                    }
                    addedToBVocab = false;
                    addedToFVocab = false;
                    prevPhrase = tok[0];
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