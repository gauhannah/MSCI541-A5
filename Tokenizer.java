package com.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class tokenizes all inputs into the program that require tokenization. Eg. The
 * Query and the documents to form the inverted index
 */
public class Tokenizer {
    private ArrayList<String> tokens;
    private HashMap<String, Integer> termCounts;

    public Tokenizer() {
        tokens = new ArrayList <>();
        termCounts = new HashMap<>();
    }

    public ArrayList<String> getTokens(){
        return this.tokens;
    }

    public HashMap<String, Integer> getTermCounts(){ return this.termCounts; }

    /*This method breaks the document into tokens for processing*/

    public void Tokenize(String text) {
        text = text.toLowerCase();
        int start = 0;
        int i;
        String token;
        for (i = 0; i < text.length(); ++i){
            char c = text.charAt(i);
            if(!Character.isLetterOrDigit(c)){
                if(start != i) {
                    token = text.substring(start,i);
                    if(token.length() > 0) {
                        this.tokens.add(token);
                        if(termCounts.containsKey(token)){
                            termCounts.put(token, termCounts.get(token)+1);
                        } else {
                            termCounts.put(token, 1);
                        }
                    }
                }
                start = i + 1;
            }
        }

        if(start != i) {
            token = text.substring(start,i);
            if(token.length() > 0) {
                tokens.add(token);
                if (termCounts.containsKey(token)) {
                    termCounts.put(token, termCounts.get(token) + 1);
                } else {
                    termCounts.put(token, 1);
                }
            }
        }

    }




}
