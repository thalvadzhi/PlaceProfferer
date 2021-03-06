package IR;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String placeName = "";
    private ArrayList<Place> places;
    private int i = -1; // counter for places
    private int j = -1; // counter for landmarks
    private boolean isNewLandmark = false;
    private static int id = 0;
    private String country = "";

    public Crawler() {
        links = new HashSet<String>();
        places = new ArrayList<>();
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

                    String heading = document.title();
                    String[] filteredHeading = heading.split("[0-9]", 2);
                    country = filteredHeading[0];

                    Elements linksOnPage = document.select(".popularCities > a");

                    depth++;
                    for (Element page : linksOnPage) {
                        getPageLinks(page.attr("abs:href"), depth);
                    }
                }

                if (depth == 1) {
                    String heading = document.title();
                    String[] filteredHeading = heading.split("[0-9]", 2);
                    placeName = filteredHeading[0];

                    String[] coordsPlace = new String[2];
                    String trickyLink1 = "https://www.tripadvisor.com/MetaPlacementAjax?detail=";
                    String trickyLink2 = "&placementName=location_nearby&disableLazyLoading=true";
                    String arrURL[] = URL.split("-g");
                    String secondURL[] = arrURL[1].split("-");
                    String trickyURL = trickyLink1 + secondURL[0] + trickyLink2;
                    coordsPlace = getCoords(trickyURL);

                    i++;
                    Place place = new Place(placeName, coordsPlace);
                    places.add(i, place);

                    File file = new File(placeName);
                    writeToFile(placeName, placeName);

                    Elements thingsToDo = document.select("a[href^=\"/Attractions\"]");
                    depth++;
                    for (Element page : thingsToDo) {
                        getPageLinks(page.attr("abs:href"), depth);
                    }
                }

                if (depth == 2) {
                    isNewLandmark = true;
                    j = -1;
                    Elements topThingsToDo = document.select("a[href^=\"/Attraction_Review\"]");
                    depth++;
                    for (Element page : topThingsToDo) {
                        if (!page.attr("href").contains("#REVIEWS")) {
                            getPageLinks(page.attr("abs:href"), depth);
                            isNewLandmark = true;
                        }
                    }

                    Elements otherPages = document.select("a[href^=\"/Attractions\"]");
                    depth--;
                    for (Element page : topThingsToDo) {
                        if (page.attr("href").contains("#FILTERED_LIST")) {
                            getPageLinks(page.attr("abs:href"), depth);
                            isNewLandmark = true;
                        }
                    }
                }

                if (depth == 3) {
                    depth++;
                    System.out.println("BOOLEAN = " + isNewLandmark);
                    if (isNewLandmark) {
                        isNewLandmark = false;
                        j++;

                        String title = "\n" + document.title();

                        Element spanRating = document.select("span.overallRating").first();
                        String overallRating;
                        if (spanRating != null) {
                            overallRating = "RATING: " + spanRating.html();
                        } else {
                            overallRating = new String("0");
                        }

                        String[] coords = new String[2];
                        String trickyLink1 = "https://www.tripadvisor.com/MetaPlacementAjax?detail=";
                        String trickyLink2 = "&placementName=location_nearby&disableLazyLoading=true";
                        String arrURL[] = URL.split("-d");
                        String secondURL[] = arrURL[1].split("-");
                        String trickyURL = trickyLink1 + secondURL[0] + trickyLink2;
                        coords = getCoords(trickyURL);

                        Elements categories = document.select(".detail > a");
                        String category = categories.html();

                        Landmark landmark = new Landmark(title, coords, overallRating, id, country, placeName, category);
                        Elements comments = document.select(".partial_entry");
                        Elements commentHeadline = document.select(".noQuotes");
                        landmark.addReviews(commentHeadline.html());
                        landmark.addReviews(comments.html());

                        id++;

                        Place placeFromList = places.get(i);
                        placeFromList.addLandmark(landmark);
                        places.set(i, placeFromList);
                    } else if (!isNewLandmark) {
                        Place placeFromList = places.get(i);
                        ArrayList<Landmark> landmarksList = placeFromList.getLandmarks();
                        Landmark landmarkFromList = landmarksList.get(j);

                        Elements comments = document.select(".partial_entry");
                        Elements commentHeadline = document.select(".noQuotes");

                        landmarkFromList.addReviews(commentHeadline.html());
                        landmarkFromList.addReviews(comments.html());

                        landmarksList.set(j, landmarkFromList);
                        places.set(i, new Place(placeFromList.getName(), placeFromList.getLocation(), landmarksList));
                    }

                    depth--;
                    Elements hrefs = document.getElementsByClass("pageNum taLnk ");
                    for (Element hr : hrefs) {
                        if (hr.hasAttr("data-href")) {
                            String attr = hr.attr("data-href");
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

            String secondSplitSrc[] = new String[2];
            if (splitSrc.length > 1) {
                secondSplitSrc = splitSrc[1].split("&maptype");
                coords = secondSplitSrc[0].split(",");
            } else {
                coords[0] = null;
                coords[1] = null;
            }
        } catch (IOException e) {
        }
        return coords;
    }

    public void writeToFile(String fileName, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.append(content + "\n");
        writer.close();
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageLinks("https://www.tripadvisor.com/Tourism-g294451-Bulgaria-Vacations.html", 0);
        //crawler.getPageLinks("https://www.tripadvisor.com/Tourism-g188045-Switzerland-Vacations.html", 0);
        //crawler.getPageLinks("https://www.tripadvisor.com/Tourism-g499086-Sunny_Beach_Burgas_Province-Vacations.html", 1);
    }
}
