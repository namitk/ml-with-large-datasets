
import java.util.ArrayList;

/* To avoid copying code in both train and test, I have put the tokenizeDoc function here 
 * and NBTrain and NBTest both extend this class
 */
public class NB {
    public ArrayList<String> tokenizeDoc(String cur_doc) {
        String[] words = cur_doc.split("\\s+");
        ArrayList<String> tokens = new ArrayList<String>();
        tokens.add(words[0]);
        for (int i = 1; i < words.length; i++) {
            words[i] = words[i].replaceAll("\\W", "");
            if (words[i].length() > 0) {
                tokens.add(words[i]);
            }
        }
        return tokens;
    }
}
