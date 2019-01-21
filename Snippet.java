package com.app;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class generates snippets for the query results
 */
public class Snippet {
    public static String generateSnippet(String document, ArrayList<String> tokens){
        String snippet = "";
        String[] sentencesArray = document.split("\\.|!|\\?|\\n");
        TreeMap<Integer, String> sentences = new TreeMap<>();
        for( int i = 0; i < sentencesArray.length; ++ i){
            sentences.put(i,sentencesArray[i]);
        }
        TreeMap<Integer, String> relevantSentences = new TreeMap<>();
        Tokenizer sentenceTokens = new Tokenizer();
        for(int i: sentences.keySet()) {
            sentenceTokens.Tokenize(sentences.get(i));
            if((sentenceTokens.getTokens().size()) > 3) {
                int termsInCommon = getCommonTerms(sentenceTokens.getTokens(), tokens);
                if(termsInCommon > 0) {
                    relevantSentences.put(i, sentences.get(i));
                }

            }
            sentenceTokens = new Tokenizer();
        }

        // concatinate sentences until you reach between 50-65 words
        for(Integer i: relevantSentences.keySet()){
            if(snippet.split( " ").length <= 50){
                snippet += relevantSentences.get(i) + ". ";
            }
        }
        return snippet;
    }

    // This method determines the number of terms that the query has in common with the sentence
    static int getCommonTerms(ArrayList<String> sentenceTokens, ArrayList<String> tokens){
        int commonTerms = 0;
        for(int i = 0; i < tokens.size(); ++i){
            for(int j = 0; j < sentenceTokens.size(); ++j){
                if(tokens.get(i).toLowerCase().equals(sentenceTokens.get(j).toLowerCase())){
                    commonTerms += 1;
                }
            }
        }
        return commonTerms;
    }



}
