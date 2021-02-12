import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//regex
import java.util.regex.*;

//jsoup
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;

public class Brancher {
    //branches out to #CAP random urls within an entered url.
    //the urls then branch to another #CAP more, continuing on for an amount = ITERATIONS
    //this class is ONLY FOR KEEPING THE URLS. WE SCRAPE WITH MASTERSCRAPER

    //initial document information
    private String initialURL; // the initial URL
    private Document initialDocument;

    //iteration information
    private int iterations;
    private int cap;

    //start the count of iterations at 1
    //pattern example: #CAP = 2, iterations = 3 -> iCounter = 1,1 URLS ; iCounter =2, 1 + 2 URLS, iCounter = 3, 1 + 2 + 2^2 URLS.
    //thus number of URLS on iteration i = sigma(CAP^(i-1));

    private int MAX_ITERATIONS;

    //to store all of the URLS from each branch
    static ArrayList<String> MASTER_URLS = new ArrayList<String>();

    //to see what the random titles are
    static ArrayList<String> MASTER_TITLES = new ArrayList<String>();

    //the URLS you are currently making branchlings with. This adjusts after the URL is made into a branchling (See StartBranch method)
    static ArrayList<String> WORKING_URLS = new ArrayList<String>();

    //the URLS you need to remove from WORKING_URLS
    static ArrayList<String> FINISHED_URLS = new ArrayList<String>();

    static int BRANCH_COUNT = 1; //start at one, this is technically the first branch

    //give a pattern so you do not get bad URLS to work with / only relevant ones
    static Pattern home_url_pattern = Pattern.compile("(https://ja\\.wikipedia\\.org.+)");

    

    
    //URLS contains all of the urls to make a MasterScrape of.
    private ArrayList<String> URLS = new ArrayList<String>();

    static boolean FirstRun = true;

    public Brancher(String initialURL, int iterations, int cap) throws IOException
    {

        this.initialURL = initialURL;
        //append to MASTER_URLS
        MASTER_URLS.add(initialURL);

        //append the initial url to the arraylist
        this.URLS.add(this.initialURL);

        //create the initial document so that we can get started making Branchlings
        this.initialDocument = Jsoup.connect(this.initialURL).get();

        MASTER_TITLES.add(this.initialDocument.title());

        this.iterations = iterations;
        this.cap = cap;

        if(FirstRun){
            for(int i = 0;i < this.iterations; i++)
            {
                MAX_ITERATIONS += (int) Math.pow(this.cap,i);
            }
            FirstRun = false;
            System.out.println("Max iterations for iter = " + this.iterations + " and cap: " + this.cap + " = " + MAX_ITERATIONS);
        }
    }

    public void initializeBrancher() throws IOException
    {
        getURLS(this.URLS, this.initialDocument);
        startBranch();
    }

    protected ArrayList<String> getURLS(ArrayList<String> urlList, Document initDoc)

    {
        //this returns ALL of the URLS in the initial document. We only want #CAP of them.
        Elements links = initDoc.select("a");
        for(Element link: links)
        {
            String absLink = link.attr("abs:href");
            Matcher matcher = home_url_pattern.matcher(absLink);
            if((matcher.matches()) &&(MASTER_URLS.contains(absLink) == false)) //CHECK THIS !!!!!!! NOT SURE IF WORK, NEED TO REMOVE DUPS
            {
                urlList.add(absLink);
            }
        }
        
        //note if there are only #CAP urls or less, there is no need to randomize them.
        if(urlList.size() > this.cap)
        {
            return thinURLS(urlList);
        }else{
            return thinURLS(urlList);
        }
    }

    private ArrayList<String> thinURLS(ArrayList<String> urlList)
    {
        //thins the list of all URLS obtained from branchURLS to #CAP
        Random rand = new Random();

        while(urlList.size() > this.cap)
        {
            int randIndex = rand.nextInt(urlList.size()); // .nextInt is exclusive of upper bound, so no need to subtract 1
            urlList.remove(randIndex);
        }
        return urlList;
    }

    private void startBranch() throws IOException
    {
        //start the initial branch
        for(int i = 0;i<this.URLS.size();i++)
        {
            String seedURL = this.URLS.get(i);
        
            new Branchling(seedURL,this.iterations,
            this.cap, BRANCH_COUNT);
        }

        while(BRANCH_COUNT < MAX_ITERATIONS)
        {
            //need to fix the concurrent issue
            
            ArrayList<String> temp = new ArrayList<>(WORKING_URLS);
            for(String URL : temp)
            {
                new Branchling(URL,this.iterations,this.cap,BRANCH_COUNT);
            }
            WORKING_URLS.removeAll(FINISHED_URLS);
            FINISHED_URLS.clear();
        }
    }
    public void showAllTitles()
    {
        for(String title:MASTER_TITLES)
        {
            System.out.println(title);
        }
    }
    public void showAllURLS(boolean withTitles)
    {
        if(withTitles)//print the titles as well
        {
            for(int i=0;i<MASTER_TITLES.size();i++)
            {
                System.out.println(i + " - " + MASTER_TITLES.get(i) + ": "+ MASTER_URLS.get(i) + "\n");
            }
        }
        else
        {
            for(String url:MASTER_URLS)
            {
                System.out.println(url);
            }
        }
    }
    public Document getDocument()
    {
        return this.initialDocument;
    }
    public ArrayList<String> getURLArrayList()
    {
        return this.URLS;
    }
    public String getInitialURL()
    {
        return this.initialURL;
    }
    //getters for use in Branchlings
    public int getIterations()
    {
        return this.iterations;
    }

    public int getCap()
    {
        return this.cap;
    }
}
