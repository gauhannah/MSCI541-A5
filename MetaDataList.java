package com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class holds all metadata for all files.
 */
public class MetaDataList {
    public class MetaData {
        private String docNo;
        private String date;
        private String headline;
        private int wordCount;
        private String path;

        public MetaData(String docNo) {
            this.docNo = docNo;
        }

        public String getDocNo(){return this.docNo;}
        public String getDate() {return this.date;}
        public String getHeadline() {return this.headline;}
        public int getWordCount(){return this.wordCount;}
        public String getPath(){return this.path;}


        public void setHeadline(String headline) {this.headline = headline;}
        public void setWordCount(int wordCount) {this.wordCount = wordCount;}
        public void setPath(String path) {this.path = path;}

        // Assumes dates are formatted M/D/Y
        public void setDate(String date) {
            String[] tmp = date.split(",|\\s");

            switch(tmp[0].trim()){
                case "January":
                    this.date = "1/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "February":
                    this.date = "2/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "March":
                    this.date = "3/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "April":
                    this.date = "4/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "May":
                    this.date = "5/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "June":
                    this.date = "6/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "July":
                    this.date = "7/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "August":
                    this.date = "8/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "September":
                    this.date = "9/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "October":
                    this.date = "10/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "November":
                    this.date = "11/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                case "December":
                    this.date = "12/" + tmp[1].trim() + "/" + tmp[3].trim();
                    break;
                default:
                    this.date = "INVALID DATE FORMAT";

            }
        }
    }

    private HashMap<Integer, MetaData> metaList;
    private double avgWordCount;

    public MetaDataList(String path) throws IOException{
        metaList = new HashMap<>();
        BufferedReader br = null;
        String line;
        String [] tmp;
        MetaData document = null;
        int tmpWC = 0;
        double avgWordCount = 0;
        Integer internalID = 0;
        try {
            br = new BufferedReader(new FileReader(path + "/metadata.txt"));
            while((line=br.readLine())!= null){
                tmp = line.split("~");
                switch (tmp[0]){
                    case "docno":
                        document = new MetaData(tmp[1].trim());
                        break;
                    case "internal id":
                        internalID = Integer.parseInt(tmp[1].trim());
                        metaList.put(internalID, document);
                        break;
                    case "word count":
                        tmpWC = Integer.parseInt(tmp[1].trim());
                        metaList.get(internalID).setWordCount(tmpWC);
                        avgWordCount += (double)tmpWC;
                        break;
                    case "date":
                        metaList.get(internalID).setDate(tmp[1].trim());
                        break;
                    case "headline":
                        metaList.get(internalID).setHeadline(tmp[1].trim());
                        break;
                    case "path":
                        metaList.get(internalID).setPath(tmp[1].trim());
                        break;
                    default:
                        System.out.println("Invalid input file.");
                        break;
                }
                this.avgWordCount = avgWordCount / metaList.size();
            }
        } catch (Exception ex) {
            System.out.println("Index path specified not valid. Please try again");
            ex.printStackTrace();
        } finally {
            br.close();
        }

    }

    public double GetAvgWordCount() {
       return avgWordCount;
    }

    public double getDoclength(Integer docId) {
        return this.metaList.get(docId).getWordCount();
    }

    public String getDate(Integer docId){
        return this.metaList.get(docId).getDate();
    }

    public String getHeadLine(Integer docId){
        return this.metaList.get(docId).getHeadline();
    }

    public String getPath(Integer docId){
        try {
            return this.metaList.get(docId).getPath();
        } catch (NullPointerException ex) {
            return "invalid file";
        }
    }
}
