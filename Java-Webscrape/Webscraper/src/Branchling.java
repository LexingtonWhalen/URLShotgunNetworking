import java.io.IOException;
import java.util.ArrayList;

//jsoup
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class Branchling extends Brancher{
    //Branchlings are the off-shoots from the initial #CAP urls generated from Brancher.
    //Branchlings will inherit some of the functionality of the Brancher class.


    //items to create a doc to just get the urls from
    private ArrayList<String> URLS = new ArrayList<String>();

    private Document initialDocument;

    private int branchlingID;
    private boolean isBranching = true;
    /*
    TO DO:
    Make branchlings return another #CAP urls to Brancher, using the Branchling.branchURLs
    NEED TO CREATE A DOCUMENT FOR EACH BRANCHLING, AND MAKE "BRANCHURLS" not void
    */

    public Branchling(String initialURL,int iterations, int cap, int branchlingID) throws IOException
    {
        //in super, it appends this to MASTER_URLS, and will add 1 to BRANCH_COUNT
        //in super, appends title to MASTER_TITLES
        super(initialURL,iterations, cap); 
        this.branchlingID = branchlingID;


        this.initialDocument = Jsoup.connect(initialURL).get();

        //append to the overarching MasterURLs

        BRANCH_COUNT += 1;

        this.URLS  = getURLS(this.URLS, this.initialDocument);


        //System.out.println("\n In branchling: " + branchlingID + "URL: " + this.getInitialURL() + "\n");
        
        for(String url:this.URLS)
        {
            WORKING_URLS.add(url);
            //System.out.println("URL in branchling " + branchlingID + ": " + url);

            //add to finished so we can remove once we create new branchling
            FINISHED_URLS.add(url);
        }
    
    }
    

    private void printURLS()
    {
        System.out.println("\n In branchling: " + branchlingID + " - URL: " + this.getInitialURL() + "\n");
        for(String url:this.URLS)
        {
            System.out.println(url);
        }
    }

    public void printMasterURL()
    {
        if(getDocument() != null){
            System.out.println("got doc");
        }
        System.out.println("Branch count: " + BRANCH_COUNT);

        for(int i = 0;i<MASTER_URLS.size();i++)
        {
            System.out.println("Printing Master URL: " + MASTER_URLS.get(i));
        }
    }

    public void printMasterTitles()
    {
        for(int i=0;i<MASTER_TITLES.size();i++)
        {
            System.out.println(i + ":" + MASTER_TITLES.get(i));
        }
    }


    public void killBranch()
    {
        this.isBranching = false;
    }
    public boolean getBranchStatus()
    {
        return this.isBranching;
    }
    
}
