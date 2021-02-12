import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MasterScraper 
{
    //Scrapes the urls brought by Brancher. Creates a CSV of the most frequent language

    private ArrayList<String> masterArray = new ArrayList<String>();
    private HashMap<String,Integer> freqHash = new HashMap<String,Integer>();
    private String yourFileName;

    public MasterScraper(ArrayList<String> urls, String yourFileName) throws IOException
    {
        this.yourFileName = yourFileName;
        //scrapers is a list of urls to make scrapes of
        for(int i = 0; i< urls.size();i++){
            Scraper temp = new Scraper(urls.get(i));
            this.masterArray.addAll(temp.getMatches());
        }
        putIntHashMap();
        sortByValue();
    }
    private void putIntHashMap()
    {
        for(String i:this.masterArray)
        {
            Integer count = this.freqHash.get(i);
            if(count ==null)
            {
                this.freqHash.put(i,1);
            }
            else
            {
                this.freqHash.put(i,count+1);
            }
        }
    }
    private void sortByValue()
    {
        List<Map.Entry<String,Integer>> list = 
        new LinkedList<Map.Entry<String,Integer>> (this.freqHash.entrySet());

        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String,Integer> o1,
                                Map.Entry<String,Integer> o2)
        {
            int i = o1.getValue().compareTo(o2.getValue());
            if(i!=0) return -i; //reverse sort
            return o1.getValue().compareTo(o2.getValue());
        }
        });
        //put data from sorted list into hm
        HashMap<String,Integer> temp = new LinkedHashMap<String,Integer>();
        for(Map.Entry<String,Integer> aa:list){
            temp.put(aa.getKey(),aa.getValue());
        }
        this.freqHash = temp;
        
    }
    public void createCSV()
    {
        String eol = System.getProperty("line.separator");
        try
        {
           try(Writer writer = new FileWriter(this.yourFileName+".csv"))
           {
               for(Map.Entry<String,Integer> entry: this.freqHash.entrySet())
               {
                   writer.append(entry.getKey())
                   .append(',')
                   .append((entry.getValue()).toString())
                   .append(eol);
               }
               
           }
        }
        catch(IOException ex)
        {
            ex.printStackTrace(System.err);
        }
    }
    public void printFreqHash(){
        for(String key: this.freqHash.keySet()){
            System.out.println(key + ":" + this.freqHash.get(key));
        }
    }
    public void printMasterArray(){
        for(int i=0;i<this.masterArray.size();i++){
            System.out.println(this.masterArray.get(i));
        }
    }
}
