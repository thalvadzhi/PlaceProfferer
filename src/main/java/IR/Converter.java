/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import constants.Constants;
import edu.stanford.nlp.util.Pair;
import nlp.Context;
import nlp.Verb;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static String serializeParserOutput(HashMap<Integer, List<Pair<Verb, Context>>> activities){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<Pair<Verb, Context>>>>() {}.getType();
        String json = gson.toJson(activities, type);
        return json;
    }

    public static void writeJsonToFile(String json, String fileName){
        try (FileWriter writer = new FileWriter(fileName)){
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<Integer, List<Pair<Verb, Context>>> deserializeParserOutput(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, List<Pair<Verb, Context>>>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static String readJsonFromFile(String fileName){
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
