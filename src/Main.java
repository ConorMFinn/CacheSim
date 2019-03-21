import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static final String FILENAME = "19-cf338-big.in";
    private static int k;
    private static int blocks;
    private static int indexBits;
    private static int offsetBits;
    private ArrayList<ArrayList<Integer>> cache = new ArrayList<>(blocks);

    public static void main(String[] args){
        Main m = new Main();
        writeFile(m.process());
    }

    private String process() {
        String toReturn = "";
        double[] arr = makeArray(readFile());
        for(int i = 0; i < blocks; i++) {
            cache.add(new ArrayList<>(k));
        }
        System.out.println(offsetBits);
        System.out.println(indexBits);
        for(Double i: arr){
            /*String s = Double.toBinaryString(n);
            s = s.substring(0,s.length() - offsetBits);
            String indexStr = s.substring(s.length() - indexBits);
            s = s.substring(0, s.length() - indexBits);
            int tag = Integer.parseInt(s,2);
            int index = Integer.parseInt(indexStr, 2);
            */
            Double d = (i - (i % (2*offsetBits)));
            // System.out.println(d);
            int n = d.intValue();
            int index = n % (2*indexBits);
            int tag = n - index;

            if(cache.get(index).contains(tag)) {
                cache.get(index).remove((Integer) tag);
                cache.get(index).add(0, tag);
                toReturn += "C";
            } else if (cache.get(index).isEmpty()) {
                cache.get(index).add(tag);
                toReturn += "M";
            } else if (cache.get(index).size() <= k) {
                cache.get(index).add(0,tag);
                toReturn += "M";
            } else {
                cache.get(index).remove(k);
                cache.get(index).add(0,tag);
                toReturn += "M";
            }
        }
        return  toReturn;
    }

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
        String desc = a.get(0);
        String[] descArr = desc.split(" ");
        int c = Integer.parseInt(descArr[1]);
        int b = Integer.parseInt(descArr[2]);
        k = Integer.parseInt(descArr[3]);
        blocks = c / b;
        int bytes = b / k;
        indexBits = blocks/2;
        offsetBits = bytes /2;
        a.remove(0);
        return a;
    }

    private double[] makeArray(ArrayList<String> strings) {
        double[] arr = new double[strings.size()];
        for(int i = 0; i < strings.size(); i++) {
            arr[i] = Double.parseDouble(strings.get(i));
        }
        return arr;
    }

    private static void writeFile(String s) {
        try {
            FileWriter fw = new FileWriter(FILENAME, true);
            fw.write(s);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
