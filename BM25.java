package com.app;

import java.util.HashMap;

/**
 This class calculates the BM25 scores for all documents for a topic */
public class BM25 {

    // formula constants
    private static final double k1 = 1.2;
    private static final double k2 = 7;
    private static final double b = 0.75;

    public static void CalculateBM25Scores(Results resultSet, HashMap<String, Integer> terms, InvertedIndex invertedIdx, Index index, Lexicon lexicon, MetaDataList metaData){
        for(Integer i = 0; i < index.Size(); ++i){
            double docLength = metaData.getDoclength(i);
            double score = CalculateDocumentScore(lexicon, docLength, metaData.GetAvgWordCount(), invertedIdx, i, terms, index);
            if(score > 0.0) {
                resultSet.Add(index.GetDocNo(i), score);
            }
        }
        resultSet.Sort();

    }

    // calculates the BM25 score for 1 document
    private static double CalculateDocumentScore(Lexicon lexicon, double docLength, double avgDocLength,
                                                 InvertedIndex invertedIndex, Integer docId, HashMap<String, Integer> termCounts, Index idx){
        double K = CalculateK(docLength, avgDocLength);
        double result = 0;
        for(String term: termCounts.keySet()){
            Integer termID = lexicon.GetTermId(term);
            double fi = getDocOccurances(termID,docId, invertedIndex);
            // calculate inside the summation for bm25 score
            double term1 = ((k1 + 1)*fi)/(K + fi);
            double term2 = ((k2 + 1)/(k2+termCounts.get(term))*termCounts.get(term));
            double term3 = Math.log((idx.Size()-getNumDocs(termID, invertedIndex)+0.5)/(getNumDocs(termID, invertedIndex)+0.5));
            result += term1*term2*term3;
        }
        return result;
    }

    private static double CalculateK(double docLength, double avgDocLength) {
        return k1*((1-b)+b*(docLength/avgDocLength));
    }


    // get the number of docs containing a term
    private static double getNumDocs(Integer termID, InvertedIndex invertedIndex) {
        try {
            return invertedIndex.getPostingsList(termID).Length() / 2.0;
        } catch (NullPointerException ex){
            return 0;
        }
    }

    // get the number of occurences inside a doc
    public static double getDocOccurances(Integer termID, int docId, InvertedIndex invertedIndex){
        SimpleListOfInt postings = null;
        try {
            postings = invertedIndex.getPostingsList(termID);
        } catch(NullPointerException ex) {
            return 0;
        }
        if(postings != null){
            for(int i = 0; i < postings.Length(); i+=2){
                if(postings.Get(i) == docId){
                    return postings.Get(i+1);
                }
            }
        }
        return 0;
    }




}
