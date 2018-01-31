package gui.fxgui;

import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.util.Pair;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import nlp.Context;
import nlp.Verb;
import nlp.VerbContextExtractor;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Sentence ss = new Sentence("take a photo");
        List<Pair<Verb, Context>> verbContextPairs = VerbContextExtractor.getVerbContextPairs(ss);

        Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("fxGUI.fxml"));
        Scene scene = new Scene(root, 845, 490);
        primaryStage.setTitle("Tourist Helper");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
