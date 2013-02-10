
import java.util.HashSet;

public class PhraseHeader {

    final int BGNUMDECADES = 3;
    final int FGNUMDECADES = 1;
    HashSet<String> bgDecades;
    HashSet<String> fgDecades;
    
    final String FILEDELIM = "\t";
    final String MYDELIM = ";";
    final String COUNTDELIM = ",";

    public PhraseHeader() {
        bgDecades = new HashSet<String>();
        bgDecades.add("1960");
        bgDecades.add("1970");
        bgDecades.add("1980");
        fgDecades = new HashSet<String>();
        fgDecades.add("1990");
    }
}
