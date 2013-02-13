
import java.util.HashSet;

public class PhraseHeader {

    final int BGNUMDECADES = 3;
    final int FGNUMDECADES = 1;
    HashSet<String> bgDecades;
    HashSet<String> fgDecades;
    final String FILEDELIM = "\t";
    final String MYDELIM = ";";
    final String COUNTDELIM = ",";

    // 0s at the front hardcoded :( to ensure it is at the top after sorting
    final String UNIGRAMVOCABMSG = "000000_UNIGRAM_VOCAB_TOTAL_COUNT";
    final String BIGRAMVOCABMSG = "000000_BIGRAM_VOCAB_TOTAL_COUNT";
    final String UNIGRAMVOCABSIZE = "000000_UNIGRAM_VOCAB_SIZE";
    final String BIGRAMVOCABSIZE = "000000_BIGRAM_VOCAB_SIZE";

    public PhraseHeader() {
        bgDecades = new HashSet<String>();
        bgDecades.add("1960");
        bgDecades.add("1970");
        bgDecades.add("1980");
        fgDecades = new HashSet<String>();
        fgDecades.add("1990");
    }

    public String createUnigramValueString(long bgCount, long fgCount) {
        return Long.toString(bgCount) + COUNTDELIM + Long.toString(fgCount);
    }

    public String createBigramValueString(long bgCount, long fgCount) {
        return "B12=" + Long.toString(bgCount) + COUNTDELIM + "F12=" + Long.toString(fgCount);
    }

    public long[] returnCounts(String msg) {
        long[] counts = new long[2];
        String tok[] = msg.split(COUNTDELIM);
        counts[0] = Long.parseLong(tok[0]);
        counts[1] = Long.parseLong(tok[1]);
        return counts;
    }
}