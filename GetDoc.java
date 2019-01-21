package com.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class gets the raw document from disk and strips out the tags. For the purposes
 * of this assignment, the document is considered everything in the TEXT and GRAPHIC tags
 */
public class GetDoc {
    public static String Get(String docNo, MetaDataList metadata, Index index) throws IOException {
        BufferedReader br = null;
        Integer docId = index.GetDocId(docNo);
        File test = new File(metadata.getPath(docId));
        StringBuilder file = new StringBuilder();
        if (!test.exists()) {
            return "file not found";
        }
        try {
            br = new BufferedReader(new FileReader(metadata.getPath(docId)));
            String line;
                while ((line = br.readLine()) != null) {
                    if(line.equals("<TEXT>")){
                        line = br.readLine();
                        while(!line.equals("</TEXT>")) {
                            file.append(line.replaceAll("<.*>|</.*>", System.lineSeparator()));
                            line = br.readLine();
                        }
                        if (line.equals("<GRAPHIC>")) {
                            line = br.readLine();
                            while(!line.equals("</GRAPHIC>")) {
                                file.append(line.replaceAll("<.*>|</.*>", System.lineSeparator()));
                                line = br.readLine();
                            }
                        }
                    }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            br.close();
        }
        return file.toString();
    }

}
