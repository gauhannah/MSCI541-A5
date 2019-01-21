package com.app;

import java.io.Console;
import java.io.IOException;

/* This class contains all GUI functionality and calls all other methods and classes
* that are required for the functioning of the applicaiton.
* */
public class Main {


    public static void main(String[] args) throws IOException {
        String indexPath = args[0];
        Console console = System.console();
        System.out.println("Loading....");
        MetaDataList metaData = new MetaDataList(indexPath);
        Index index = new Index(indexPath);
        Lexicon lexicon = new Lexicon(indexPath);
        InvertedIndex invertedIndex = new InvertedIndex(indexPath);
        Tokenizer tokenizer;
	    System.out.println("Welcome to the Search Engine!");
	    System.out.println("You can do the following things:");
	    System.out.println("1. Type N and press enter to input a query");
	    System.out.println("2. Type Q and press enter to quit the program");
	    System.out.println("3. Type H and press enter to see your list possible commands");
	    String command;
	    String query;
	    Results results = null;
	    boolean inQuery = false;
	    while(true) {
            command = console.readLine("Please enter a command: ").toUpperCase();
            if (command.equals("N")) {
                inQuery = true;
                query = console.readLine("please enter your query and press enter:");
                long startTime = System.currentTimeMillis();
                tokenizer = new Tokenizer();
                tokenizer.Tokenize(query);
                results = new Results();

                BM25.CalculateBM25Scores(results, tokenizer.getTermCounts(), invertedIndex, index, lexicon, metaData);

                // get the documents and snippets for the user
                for(int i = 0; i < 10 && i < results.Size(); ++i) {
                    results.setDocument(i, GetDoc.Get(results.getDocNo(i), metaData, index));
                    results.setSnippet(i, tokenizer.getTokens(), lexicon);
                }
                if(results.Size() == 0){
                    System.out.println("Your query returned no results");
                }

                // output the results with snippets
                for (int i = 0; i < 10 && i < results.Size(); ++i){
                    if(metaData.getHeadLine(index.GetDocId(results.getDocNo(i)))!= null) {
                        System.out.println((i + 1) + ". " + metaData.getHeadLine(index.GetDocId(results.getDocNo(i))) +
                                "; (" + metaData.getDate(index.GetDocId(results.getDocNo(i))) + ")");
                    } else {
                        System.out.println((i + 1) + ". " +results.getSnippet(i).substring(0,50) +
                                "; (" + metaData.getDate(index.GetDocId(results.getDocNo(i))) + ")");
                    }
                    System.out.println(results.getSnippet(i) + " (" + results.getDocNo(i) + ")");
                    System.out.println(System.lineSeparator());
                }
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("it took: " + ((double)duration/1000) + " seconds");


            }

            // Quit the program
            else if(command.equals("Q")){
                System.out.println("Thank you for using the Search Engine! Goodbye.");
                return;
            }

            // Gives the user the options if they forget
            else if(command.equals("H")){
                inQuery = false;
                System.out.println("The options for this program are as follows:");
                System.out.println("1. Type N and press enter to input a query");
                System.out.println("2. Type Q and press enter to quit the program");
                System.out.println("3. Type H and press enter to see your list possible commands");
            }

            // Outputs the document if requested by the user
            else if(inQuery == true && TryParseInt(command)){
                int doc = Integer.parseInt(command)-1;
                if(!(doc > 9 || doc < 0)) {
                    System.out.println(metaData.getHeadLine(index.GetDocId(results.getDocNo(doc))));
                    System.out.println("Date: " + metaData.getDate(index.GetDocId(results.getDocNo(doc))));
                    System.out.println(results.getDocument(doc));
                }
                else {
                    System.out.println("please select a valid document.");
                }
            }
            else {
                System.out.println("Invalid command. Please try again or press H to see all available commands.");
            }
        }
    }


    // Making sure that the user has entered a valid integer
    static boolean TryParseInt(String cmd){
        try {
            int test = Integer.parseInt(cmd);
            return true;
        } catch (Exception ex){
            return false;
        }
    }

}
