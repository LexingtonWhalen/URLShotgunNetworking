
import java.util.regex.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//kuromoji
import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
//jsoup
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;


public class Scraper
{
    private String url;
    private Document doc;
    private Element body;
    private ArrayList<String> matches = new ArrayList<String>();

    public Scraper(String url) throws IOException
    {
            this.url = url;
            this.doc = Jsoup.connect(this.url).get();
            this.body = this.doc.body();
            filterJapanese();
    }
    private void filterJapanese()
    {
        Pattern pattern = Pattern.compile("[ぁ-んァ-ン一-龯]+");
        //loop through this.body maybe??
        Matcher matcher = pattern.matcher((this.doc.body()).toString());

        Tokenizer tokenizer = Tokenizer.builder().build();
        //parse the japanese, then tokenize
        while(matcher.find())
        {
            for(Token token: tokenizer.tokenize(matcher.group())){
                String res = token.getBaseForm();
                if(res!=null){
                    this.matches.add(res);
                }
            }
        }
    }
    public String getTitle()
    {
        return this.doc.title();
    }
    public List getMatches(){
        return this.matches;
    }
    public void printBody()
    {
        System.out.println(this.body);
    }
    public void printMatches(){
        for(int i=0; i< this.matches.size();i++){
            System.out.println(this.matches.get(i));
        }
    }
}
