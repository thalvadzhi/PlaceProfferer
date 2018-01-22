package IR;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ILIYAN
 */
public class Crawler {

    private static final int MAX_DEPTH = 6;
    private HashSet<String> links;
    private String fileName = "";

    public Crawler() {
        links = new HashSet<String>();
    }

    public void getPageLinks(String URL, int depth) {
        if (!links.contains(URL) && depth < MAX_DEPTH) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");

            try {
                links.add(URL);

                /*WebDriver driver = new ChromeDriver();
                WebElement webElement = driver.findElement(By.className("morePopularCities ui_button primary chevron"));
                JavascriptExecutor executor = (JavascriptExecutor)driver;
                executor.executeScript("arguments[0].click();", webElement);*/
                Document document = Jsoup.connect(URL).get();
                if (depth == 0) {

                    //Elements linksOnPage = document.select("a[href^=\"https://www.tripadvisor.com/Tourism-\"]");
                    Elements linksOnPage = document.select(".popularCities > a");

                    depth++;
                    for (Element page : linksOnPage) {
                        getPageLinks(page.attr("abs:href"), depth);
                    }
                }

                if (depth == 1) {
                    String heading = document.title();
                    String[] filteredHeading = heading.split("[0-9]", 2);
                    fileName = filteredHeading[0];

                    File file = new File(fileName);
                    writeToFile(fileName, fileName);

                    Elements thingsToDo = document.select("a[href^=\"/Attractions\"]");
                    //System.out.println("THINGS TO DO: " + thingsToDo);
                    depth++;
                    for (Element page : thingsToDo) {
                        //System.out.println(page.attr("abs:href"));
                        getPageLinks(page.attr("abs:href"), depth);
                    }
                }

                if (depth == 2) {
                    //System.out.println("depth2 here");
                    Elements topThingsToDo = document.select("a[href^=\"/Attraction_Review\"]");
                    depth++;
                    for (Element page : topThingsToDo) {
                        //System.out.println("DEPTH3 NESHTO ?!@#?!");
                        if (!page.attr("href").contains("#REVIEWS")) {
                            getPageLinks(page.attr("abs:href"), depth);
                        }
                    }

                    Elements otherPages = document.select("a[href^=\"/Attractions\"]");
                    depth--;
                    for (Element page : topThingsToDo) {
                        //System.out.println("ATTRACTIONS ?!@#?!");
                        if (page.attr("href").contains("#FILTERED_LIST")) {
                            getPageLinks(page.attr("abs:href"), depth);
                        }
                    }
                }

                if (depth == 3) {
                    depth++;
                    String title = "\n" + document.title();
                    writeToFile(fileName, title);
                    Element spanRating = document.select("span.overallRating").first();
                    String overallRating = "RATING: " + spanRating.html();
                    writeToFile(fileName, overallRating);
                    //TO DO: COORDS !
                    String[] coords = new String[2];
                    String trickyLink1 = "https://www.tripadvisor.com/MetaPlacementAjax?detail=";
                    String trickyLink2 = "&placementName=location_nearby&disableLazyLoading=true";
                    String arrURL[] = URL.split("-d");
                    String secondURL[] = arrURL[1].split("-");
                    String trickyURL = trickyLink1 + secondURL[0] + trickyLink2;
                    coords = getCoords(trickyURL);
                    writeToFile(fileName, "COORDS: " + coords[0] + " " + coords[1]);

                    Elements comments = document.select(".partial_entry");
                    Elements commentHeadline = document.select(".noQuotes");
                    writeToFile(fileName, commentHeadline.html());
                    writeToFile(fileName, comments.html());

                    depth--;
                    Elements hrefs = document.getElementsByClass("pageNum taLnk ");
                    //System.out.println("HREFSSSSSS " + hrefs);
                    for (Element hr : hrefs) {
                        if (hr.hasAttr("data-href")) {
                            String attr = hr.attr("data-href");
                            //System.out.println("DOPYLNITELNI " + attr);
                            //www.tripadvisor.com + attr
                            String mainURL = "https://www.tripadvisor.com" + attr;
                            getPageLinks(mainURL, depth);
                        }
                    }
                }

            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    public String[] getCoords(String URL) {
        String[] coords = new String[2];
        try {
            Document document = Jsoup.connect(URL).get();
            Elements link = document.select("img[src^=//maps.google.com]");
            String src = link.attr("src");
            String splitSrc[] = src.split("center=");

            String secondSplitSrc[] = splitSrc[1].split("&maptype");

            coords = secondSplitSrc[0].split(",");
            System.out.println();
        } catch (IOException e) {
        }
        return coords;
    }

    public void writeToFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(content + "\n");
        writer.close();
    }

    public static void main(String[] args) {
        new Crawler().getPageLinks("https://www.tripadvisor.com/Tourism-g294451-Bulgaria-Vacations.html", 0);
    }
}
