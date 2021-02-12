/*
Written by Lex Whalen 2/12/21
*/


import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException
    {

        String initURL = "https://ja.wikipedia.org/wiki/%E8%A5%BF%E6%9D%91%E5%8D%9A%E4%B9%8B";

        Brancher brancher = new Brancher(initURL,3,3);
        brancher.initializeBrancher();

        System.out.println("\nDone");

        MasterScraper masterScrape = new MasterScraper(brancher.MASTER_URLS, "CSVfile");
        masterScrape.createCSV();
        
        System.out.println("\nCSV file created.");

        System.out.println("Now showing all URLS\n");

        //true means we show titles with the urls, false means just urls
        brancher.showAllURLS(true);
        
    }
}