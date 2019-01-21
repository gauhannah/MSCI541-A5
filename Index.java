package com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class contains the index, which maps docid to docno
 */
public class Index {
    private  HashMap<Integer, String> index;
    private  HashMap<String, Integer> reverseIndex;

    public Index(String path) throws IOException {
        this.index = new HashMap<>();
        this.reverseIndex = new HashMap<>();
        buildIndex(path + "/index.txt");
    }

    public void buildIndex(String indexPath) throws IOException {
        BufferedReader ir = null;
        try {
            ir = new BufferedReader(new FileReader(indexPath));
            String line = ir.readLine();
            while (line != null) {
                String[] tmp = line.split(",");
                this.index.put(Integer.parseInt(tmp[0]), tmp[1]);
                this.reverseIndex.put(tmp[1], Integer.parseInt(tmp[0]));
                line = ir.readLine();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ir.close();
        }
    }


    public  String GetDocNo(int id) {
        return this.index.get(id);
    }
    public Integer GetDocId(String docNo) {
        return this.reverseIndex.get(docNo);
    }
    public int Size() { return this.index.keySet().size(); }

}
