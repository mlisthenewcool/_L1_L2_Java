/**
 * * * * * * * * * * * * * * * * * * * * * * * *
 * @author jewelsea
 * @see https://gist.github.com/jewelsea/6460130
 *  
 * @author MagicBanana
 * 
 * Lance l'application
 * * * * * * * * * * * * * * * * * * * * * * * *
 */

package app;

import java.io.IOException;

import controllers.LayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Bootloader extends Application {    
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	// Test pour réduction
    	// new TraitementParallele();
    	
        primaryStage.setTitle("Logiciel de facturation");
        
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(40);
        
        primaryStage.setScene(createScene(loadLayout()));
        primaryStage.show();
    }
    
    private Pane loadLayout() throws IOException {
    	FXMLLoader loader = new FXMLLoader();
    	
    	Pane layout = (Pane) loader.load(getClass().getResourceAsStream(ViewNavigator.PATH_LAYOUT));
    	
    	LayoutController layoutController = loader.getController();
    	
    	ViewNavigator.setLayoutController(layoutController);
    	ViewNavigator.loadView(ViewNavigator.PATH_CLIENT);
    	
    	return layout;
    }
    
    private Scene createScene(Pane layout) {
    	Scene scene = new Scene (layout);
    	
    	scene.getStylesheets().setAll(
    		getClass().getResource(ViewNavigator.PATH_STYLE).toExternalForm());
    	
    	return scene;
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}