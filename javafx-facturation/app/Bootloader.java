/**
 * * * * * * * * * * * * * *
 * @author MagicBanana
 *
 * Initialise l'application
 * * * * * * * * * * * * * *
 */

package app;

import java.io.IOException;

import controllers.*;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Bootloader extends Application {
	public static final String PATH_LAYOUT = "/views/Layout.fxml";
	public static final String LAYOUT_ID = "main";
    public static final String PATH_ACCUEIL = "/views/AccueilView.fxml";
    public static final String ACCUEIL_ID = "accueil";
    public static final String PATH_CLIENT = "/views/ClientView.fxml";
    public static final String CLIENT_ID = "client";
    public static final String PATH_STYLE = "/style/main.css";
    //private static final String PATH_STYLE_MENU = "/style/menu.css";
    
    private Stage primaryStage;
    private BorderPane layout;
    
    @FXML private MenuItem btnClient;
    
    @Override public void start(Stage primaryStage) {
    	
    	ScreensController mainContainer = new ScreensController();
    	mainContainer.loadScreen(LAYOUT_ID, PATH_LAYOUT);
    	mainContainer.loadScreen(ACCUEIL_ID, PATH_ACCUEIL);
    	mainContainer.loadScreen(CLIENT_ID, PATH_CLIENT);
    	
    	Group root = new Group();
    	root.getChildren().addAll(mainContainer);
    	Scene scene = new Scene(root);
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Logiciel de facturation");
        this.primaryStage.setMinWidth(300);
        this.primaryStage.setMinHeight(40);
        this.primaryStage.show();    
        /*
        initLayout();
        showAccueilView();
        */
    }
      
    public void initLayout() {
    	try {
    		// Charge le layout contenant le menu
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Bootloader.class.getResource(PATH_LAYOUT));
    		layout = (BorderPane) loader.load();
    		
    		// Affiche la scene contenant le layout
    		Scene scene = new Scene(layout);
    		scene.getStylesheets().add(getClass().getResource(PATH_STYLE).toExternalForm());
    		
    		/*
            MenuButton menuButton = new MenuButton();
            menuButton.getItems().addAll(
                    Stream.of("Clients", "Produits", "Factures", "Types de produit", "Taux de TVA")
                        .map(MenuItem::new).collect(Collectors.toList()));
            BorderPane root = new BorderPane(null, menuButton, null, null, null);
    		Scene menu = new Scene(root);
    		menu.getStylesheets().add(getClass().getResource(PATH_STYLE_MENU).toExternalForm());
    		*/
    		    		
    		// Affecte la scène à la fenêtre (primaryStage)
    		this.primaryStage.setScene(scene);
    		this.primaryStage.show();
    		
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    public void showClientView() {
    	try {
    		// Charge la vue client
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Bootloader.class.getResource(PATH_CLIENT));
    		AnchorPane clientView = (AnchorPane) loader.load();
    		
    		// Place la vue client au centre
    		layout.setCenter(clientView);
    		
    		// Charge le controleur
    		ClientController controller = loader.<ClientController>getController();
    		controller.ClientControllerSetup();
    		
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	}
    	
    	//new MonTraitementLongParallele();
    }
    
    public void showAccueilView() {
    	try {
    		// Charge la vue client
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Bootloader.class.getResource(PATH_ACCUEIL));
    		AnchorPane accueilView = (AnchorPane) loader.load();
    		
    		// Place la vue client au centre
    		layout.setCenter(accueilView);
    		
    		// Charge le controleur
    		LayoutController controller = loader.<LayoutController>getController();
    		controller.LayoutControllerSetup();
    		
    	} catch (IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
}