# URL Shotgun Networking

## Word analysis via scraping URL networks.

## :cinema: Video:
* 

## :grey_question: What is it?
* Creates a network-tree of URLs based on a seed URL. Each found URL can create a new branch. From those branches, you can scrape the most common Japanese (or any language) words. Creates a CSV of the most frequent words from all of the URLs stored in the tree.
###### Why "shotgun"?
* Because the way that URL retrieval occurs is by taking random spread of "pellets". Each "pellet" represents a URL. Each URL pellet can turn into a "shotgun" that then creates more URLS by "shooting" out more pellets.

## :zap: Features:
* Creates a network out of a single URL!
* Can control the length (iterations)  and density (cap) of that network!
* Can see connections between articles / links!
* Parses all HTML of the URLs to find the most common language!
* Puts that parsed info into a CSV file sorted by frequency!

## :package: Modules / Packages:
* kuromoji: https://www.atilika.org/
* jsoup: https://jsoup.org/

###### :hammer: To do:
I will likely create a similar version of this for webscraping. Maybe later on I will make a GUI for it too. Good way to learn Java!

