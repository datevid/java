package com.datevid.util;

import org.apache.log4j.Logger;

public class RestClientTest {
    private static Logger logger = Logger.getLogger(RestClientTest.class);

    public static void main(String[] args) {
        String url = "https://www.americanexpress1.com/content/dam/amex/us/staticassets/pdf/GCO/Test_PDF.pdf";
        String pathStr = "E:\\David\\pdfs\\pdf1.pdf";
        try {
            RestClient.getAndSaveFile(url,pathStr);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("error en "+e.getMessage());

        }
    }
}
