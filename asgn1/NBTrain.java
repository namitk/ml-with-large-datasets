import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NBTrain extends NB {
    HashMap<String, Integer> counts; // hash table that contains the counts

    public NBTrain() {
        counts = new HashMap<String, Integer>();
    }  

    //
    public void incrementCount(String key, int increment_by) {
        if (counts.containsKey(key)) {
            int curCount = counts.get(key);
            counts.put(key, curCount + increment_by);
        } else {
            counts.put(key, increment_by);
        }
    }

    public void train() {
        String curLine;
        Scanner s = new Scanner(System.in);

        while (s.hasNextLine()) {
            curLine = s.nextLine();
            ArrayList<String> tokens = tokenizeDoc(curLine);
            // get labels ending with CAT            
            String[] labels = tokens.get(0).split(",");
            Pattern p = Pattern.compile(".+CAT");
            for (int i = 0; i < labels.length; i++) {
                Matcher m = p.matcher(labels[i]);
                if (m.find()) {
                    incrementCount("numSamples", 1);
                    incrementCount(labels[i], 1);
                    for (int j = 1; j < tokens.size(); j++) {
                        incrementCount(labels[i] + ";" + tokens.get(j), 1);
                    }
                    incrementCount(labels[i] + ";*", tokens.size() - 1);
                }
            }
        }
        print_hashmap();
    }

    private void print_hashmap() {
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    
    public static void main(String[] args) {
        NBTrain nbtrain = new NBTrain();
        nbtrain.train();
    }   
}
