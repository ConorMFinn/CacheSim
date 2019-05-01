import java.io.*;
import java.util.ArrayList;
import java.math.BigInteger;

public class MainBig {

    private static final String FILENAME = "19-cf338-big.in";
    private static final int BLOCKS = 65536;

    /**
     * Main method to run program.
     * @param args Command line arguments.
     */
    public static void main(String[] args){
        MainBig m = new MainBig();
        writeFile(m.process(16, 10, 4));
    }

    /**
     * Process data collected from file.
     * @param indexBits Number of index bits in each address.
     * @param offsetBits Number of offset bits in address.
     * @param k Number of values in block.
     * @return Completed Cache(Hit)/Memory(Miss) string.
     */
    private String process(int indexBits, int offsetBits, int k) {
        String toReturn = "";
        ArrayList<ArrayList<Integer>> cache = new ArrayList<>();
        for(int i = 0; i < BLOCKS; i++) {
            cache.add(new ArrayList<>());
        }
        ArrayList<String> arr = makeArray(readFile());
        for(String s: arr){
            s = s.substring(0, s.length() - offsetBits);
            String indexS = s.substring(s.length() - indexBits);
            s = s.substring(0, s.length() - indexBits);
            int tag = (int) new BigInteger(s).intValue();
            int index = (int) Long.parseLong(indexS,2);
            if(cache.get(index).contains(tag)) {
                cache.get(index).remove((Integer) tag);
                cache.get(index).add(0, tag);
                toReturn += "C";
            } else if (cache.get(index).isEmpty()) {
                cache.get(index).add(tag);
                toReturn += "M";
            } else if (cache.get(index).size() == k) {
                cache.get(index).remove(k - 1);
                cache.get(index).add(0, tag);
                toReturn += "M";
            } else {
                cache.get(index).add(0, tag);
                toReturn += "M";
            }
        }
        return  toReturn;
    }

    /**
     * Read lines from the text file. First line removed.
     * @return Arraylist containing file numbers.
     */
    private ArrayList<String> readFile() {
        BufferedReader br = null;
        FileReader fr = null;
        ArrayList<String> a = new ArrayList<>();
        try {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null) {
                a.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(br != null) br.close();
                if(fr != null) fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        a.remove(0);
        return a;
    }

    /**
     * Make an array of binary strings from an array of number strings.
     * @param strings Number strings.
     * @return ArrayList of binary strings.
     */
    private ArrayList<String> makeArray(ArrayList<String> strings) {
        ArrayList<String> arr = new ArrayList<>();
        for (String string : strings) {
            BigInteger b = new BigInteger(string);
            arr.add(b.toString(2));
        }

        return arr;
    }

    /**
     * Write a string to the end of the file.
     * @param s The string to append.
     */
    private static void writeFile(String s) {
        try {
            FileWriter fw = new FileWriter(FILENAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
