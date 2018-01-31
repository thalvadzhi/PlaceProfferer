package gui.fxgui;

import IR.index.ActivityPlace;
import IR.index.IndexManager;
import IR.queryParser.QueryParser;
import constants.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controller {
    List<TextField> textFields = new ArrayList<>();
    IndexManager manager;
    QueryParser parser;

    @FXML
    VBox listContainer;
    @FXML
    Button addActivity;
    @FXML
    ScrollPane activities;
    @FXML
    TextField lat;
    @FXML
    TextField lon;
    @FXML
    TextField distance;
    @FXML
    ListView<ActivityPlace> results;
    @FXML
    ComboBox<String> chooseCountry;
    @FXML
    ComboBox<String> chooseCity;
    @FXML
    ComboBox<String> chooseCategory;
    @FXML
    Button search;
    @FXML
    VBox rating;
    @FXML
    Text currentRating;
    @FXML
    Button rateButton;
    @FXML
    TextField rateField;


    public void initialize() {
        manager = new IndexManager("Bulgaria.txt", true);
        parser = new QueryParser(manager);

        rating.setVisible(false);
        activities.setFitToWidth(true);
        addTextFieldToContainer();
        results.setCellFactory(new ActivityPlaceListViewCallback());
        results.setOnMouseClicked(new MouseClickEventHandler());

        NumericListener latListener = new NumericListener(lat);
        NumericListener lonListener = new NumericListener(lon);
        NumericListener distanceListener = new NumericListener(distance);
        NumericListener ratingListener = new NumericListener(rateField);
        lat.textProperty().addListener(latListener);
        lon.textProperty().addListener(lonListener);
        distance.textProperty().addListener(distanceListener);
        rateField.textProperty().addListener(ratingListener);

        ArrayList<String> categories = new ArrayList<>(manager.getAllCategories());
        ArrayList<String> countries = new ArrayList<>(manager.getAllCountries());
        ArrayList<String> cities = new ArrayList<>(manager.getAllCities());
        addAllConstant(categories);
        addAllConstant(countries);
        addAllConstant(cities);


        chooseCategory.getItems().addAll(categories);
        chooseCity.getItems().addAll(cities);
        chooseCountry.getItems().addAll(countries);

    }

    public void addAllConstant(ArrayList<String> ls){
        ls.add(0, Constants.ALL);
    }

    public void onActionAddActivity(ActionEvent e){
        addTextFieldToContainer();
    }

    public void onSearchAction(ActionEvent e){
        rating.setVisible(false);
        String category = chooseCategory.getSelectionModel().getSelectedItem();
        String country = chooseCountry.getSelectionModel().getSelectedItem();
        String city = chooseCity.getSelectionModel().getSelectedItem();

        HashMap<String, String> queryValues = new HashMap<String, String>();
        putOrAll(Constants.COUNTRY, country, queryValues);
        putOrAll(Constants.CATEGORY, category, queryValues);
        putOrAll(Constants.CITY, city, queryValues);

        String latRes = lat.getText();
        String lonRes = lon.getText();
        String dis = distance.getText();

        putOrNone(Constants.LAT, latRes, queryValues);
        putOrNone(Constants.LON, lonRes, queryValues);
        putOrNone(Constants.DISTANCE, dis, queryValues);

        ArrayList<String> activities = new ArrayList<>();
        for(TextField textField : textFields){
            if(!"".equals(textField.getText())){
                activities.add(textField.getText());
            }
        }

        List<ActivityPlace> places = parser.parseGetPlaces(queryValues, activities);
        results.getItems().clear();
        results.getItems().addAll(places);
    }

    private void putOrAll(String key, String value, HashMap<String, String> queryValues){
        if(value == null){
            queryValues.put(key, Constants.ALL);
        }else{
            queryValues.put(key, value);
        }
    }

    private void putOrNone(String key, String value, HashMap<String, String> queryValues){
        if("".equals(value)){
            queryValues.put(key, Constants.NONE);
        }else{
            queryValues.put(key, value);
        }
    }

    private void addTextFieldToContainer(){
        TextField f = new TextField();
        textFields.add(f);
        listContainer.getChildren().add(f);
    }

    private class ActivityPlaceListViewCallback implements Callback<ListView<ActivityPlace>, ListCell<ActivityPlace>>{
        @Override
        public ListCell<ActivityPlace> call(ListView<ActivityPlace> param) {
            ListCell<ActivityPlace> cell = new ListCell<ActivityPlace>(){
                @Override
                protected void updateItem(ActivityPlace t, boolean empty) {
                    super.updateItem(t, empty);
                    if (t == null || empty) {
                        setText(null);
                    }else{
                        setText(t.getName());
                    }
                }
            };
            return cell;
        }
    }
    private class NumericListener implements ChangeListener<String>{

        TextField f;
        public NumericListener(TextField f){
            this.f = f;
        }
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d{0,10}([\\.]\\d{0,10})?")) {
                f.setText(oldValue);
            }
        }
    }
    private class MouseClickEventHandler implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent event) {
            ActivityPlace selectedItem = results.getSelectionModel().getSelectedItem();
            rating.setVisible(true);
            currentRating.textProperty().setValue(String.format("%.2f", selectedItem.getRating().getRating()));
            rateButton.setOnAction(event1 -> {
                double rating = Double.parseDouble(rateField.getText());
                manager.updateRatingById(selectedItem.getId(), rating);
                currentRating.textProperty().setValue(String.format("%.2f", selectedItem.getRating().getRating()));
            });
        }
    }
}
