/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 * @author ILIYAN
 */
public class Converter {
    public static String serialization() {
        Crawler crawler = new Crawler();
        crawler.getPageLinks("https://www.tripadvisor.com/Tourism-g187427-Spain-Vacations.html", 0);
        
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Place>>() {}.getType();
        
        String json = gson.toJson(crawler.getPlaces(), type);
        return json;
    }

    public static ArrayList<Place> deserialization(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Place>>() {}.getType();

        ArrayList<Place> places = gson.fromJson(json, type);
        return places;
    }
    public static void main(String[] args) throws IOException {
//        String json = Converter.serialization();
        
        File file = new File("Bulgaria.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String json = reader.readLine();
        ArrayList<Place> places = deserialization(json);
        System.out.println(places);
    }
}
