package com.app;

import java.util.*;

/**
 * This class builds the resultset, it is based off of the class
 * provided by Dr. Smucker for homework 3. It is used to store the
 * results from BM25 Retrieval
 */
public class Results  {
    public class Result implements Comparable{
        private String docNo;
        private double score;
        private String fullDoc;
        private String snippet;

        public Result(String docNo, double score){
            this.docNo = docNo;
            this.score = score;
        }

        public void setFullDoc(String document) {
            this.fullDoc = document;
        }

        public void setSnippet(String snippet){
            this.snippet = snippet;
        }

        public String getFullDoc(){
            return this.fullDoc;
        }

        public String getSnippet(){
            return this.snippet;
        }

        public void setDocNo(String docNo){
            this.docNo = docNo;
        }

        public String getDocNo(){
            return this.docNo;
        }

        public double getScore(){
            return this.score;
        }

        public void setScore(double score){
            this.score = score;
        }

        public int compareTo(Object r) {
            if(this.score > ((Result) r).getScore()) {
                return -1;
            } else if(this.score < ((Result) r).getScore()){
                return 1;
            } else{
                return 0;
            }
        }
    }

    private List<Result> resultSet;


    public Results() {
        this.resultSet = new ArrayList<>();
    }

    public void Add(String docNo, double score){
        Result toAdd = new Result(docNo, score);
        resultSet.add(toAdd);
    }

    public void Sort(){
        Collections.sort(this.resultSet);
    }


    public List<Result> GetResultSet(){
        return this.resultSet;
    }

    public int Size() {
        return this.resultSet.size();
    }

    public String getDocNo(int i){
        return this.resultSet.get(i).getDocNo();
    }

    public void setDocument(int i, String document){
        this.resultSet.get(i).setFullDoc(document);
    }

    public String getDocument(int i){
        return this.resultSet.get(i).getFullDoc();
    }

    public void setSnippet(int i, ArrayList<String> tokens, Lexicon lexicon){
        String snippet = Snippet.generateSnippet(resultSet.get(i).getFullDoc(), tokens);
        this.resultSet.get(i).setSnippet(snippet);
    }

    public String getSnippet(int i){
        return this.resultSet.get(i).getSnippet();
    }

}
