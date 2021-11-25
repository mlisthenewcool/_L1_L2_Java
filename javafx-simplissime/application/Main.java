package application;

import classesObjets.Client;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Main extends Application {
	@FXML private Label lblErreur;
	@FXML private Label lblPersonne;
	@FXML private TextField textNom;
	@FXML private TextField textPrenom;
	
	@Override
	public void start(Stage primaryStage) {
	    try {
	        VBox root = (VBox)FXMLLoader.load(getClass().getResource("Td4.fxml"));
	        
	        Scene scene = new Scene(root, 635, 400);
	        
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        primaryStage.setScene(scene);
	        primaryStage.show();
            
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
        launch(args);
    }

	public void creerModele() {
		try {
			new Client (this.textNom.getText(), this.textPrenom.getText());
			this.lblPersonne.setText(textNom.getText() + " " +textPrenom.getText());
			this.lblPersonne.setVisible(true);
			this.lblErreur.setVisible(false);
			
		} catch (IllegalArgumentException iae) {
			this.lblErreur.setText(iae.getMessage());
			this.lblErreur.setTextFill(Color.RED);
			this.lblErreur.setVisible(true);
			this.lblPersonne.setVisible(false);
		}
	}
}

