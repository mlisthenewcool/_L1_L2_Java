/**
 * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Le controlleur pour la vue client
 * * * * * * * * * * * * * * * * * * * *
 */

package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.Bootloader;
import dao.factory.DaoFactory;
import dao.pojo.ClientDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pojo.Client;
import controllers.ControlledScreen;

public class ClientController implements ControlledScreen {
	
    @FXML private TableView<Client> tableClients;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNom;
    @FXML private TableColumn<Client, String> colPrenom;
    @FXML private Label lblIdTitre;
    @FXML private Label lblNom;
    @FXML private Label lblPrenom;
    @FXML private Label lblId;
    @FXML private Label lblMessage;
    @FXML private TextField textNom;
    @FXML private TextField textPrenom;
    
    private ObservableList<Client> clientsObservableList = FXCollections.observableArrayList();

    ScreensController myController;
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    } 
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(Bootloader.ACCUEIL_ID);
    }
    
    public void ClientControllerSetup() {
    	try {
    		setClients();
    		tableClients.setItems(this.getClients());
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(iae.getMessage());
    	}
    }
    
    public void setClients() {
    	ArrayList<Client> clientsBdd = new ArrayList<Client>();
    	
    	try {
        	// Récupère la liste des clients en base de données
    		ClientDao daoSQLClient = DaoFactory.getDaoFactory("MySql").getClientDao();
            clientsBdd = daoSQLClient.getAll();
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(iae.getMessage());
    	} catch (SQLException sqle) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(sqle.getMessage());
    	}

        // Ajoute la liste Bdd à la liste observable
        this.clientsObservableList.addAll(clientsBdd);
    }
    
    public ObservableList<Client> getClients() {
    	return this.clientsObservableList;
    }
    
    public void afficherDetailsClient (Client client) {
    	if (client != null) {
    		this.lblId.setText(Integer.toString(client.getIdClient()));
    		this.textNom.setText(client.getNom());
    		this.textPrenom.setText(client.getPrenom());
    	}
    	// Client null
    	else {
    		this.lblId.setText("");
    		this.textNom.setText("");
    		this.textPrenom.setText("");
    	}
    }
    
    @FXML private void ajouterClient() {
    	updateLblMessage();
    	// On récupère les saisies pour créer un nouveau client

    	try {
        	Client client = new Client(this.textNom.getText(), this.textPrenom.getText());

        	Alert alert = new Alert(AlertType.CONFIRMATION);
        	//alert.initOwner(window);
            alert.setHeaderText(null);
            alert.setContentText(
            		"Ajouter le client " +
            		client.toString() +
            		" ?");
            // Permet l'utilisation native du composant Alert pour la validation
            // Utilisation complexe d'une fonction lambda trouvée sur la doc api d'oracle
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {  
                		// On insère le client dans la base de données si les saisies sont correctes
                		ClientDao daoSQLClient = DaoFactory.getDaoFactory("MySql").getClientDao();
            			int id_client = daoSQLClient.create(client);
            			client.setIdClient(id_client);
            			
            			// On ajoute le client dynamiquement à la liste des clients
            			tableClients.getItems().add(client);
        	    	} catch (IllegalArgumentException iae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(iae.getMessage());
        	    	} catch (SQLException e) {
        	    		this.lblMessage.setTextFill(Color.RED);
        				this.lblMessage.setText(e.getMessage());
        			}
            	}
            });
    	}
       	 catch (IllegalArgumentException iae) {
       		//this.textNom.setStyle("-fx-control-inner-background : red");
       		this.lblMessage.setTextFill(Color.RED);
       		this.lblMessage.setText(iae.getMessage());
      	}
    }
    
    @FXML private void modifierClient() {
    	updateLblMessage();
    	int selectedIndex = tableClients.getSelectionModel().getSelectedIndex();
    	   		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	    	try {
	    		clientsObservableList.get(selectedIndex).setNom(this.textNom.getText());
	    		clientsObservableList.get(selectedIndex).setPrenom(this.textPrenom.getText());
			
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        //alert.initOwner(window);
		        alert.setHeaderText(null);
		        alert.setContentText(
		        		"Valider les modifications sur le client " +
		        		clientsObservableList.get(selectedIndex).toString() +
		        		" ?");
		
		        // Permet l'utilisation native du composant Alert pour la validation
		        // Utilisation complexe d'une fonction lambda trouvée sur la doc api d'oracle
		        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		        alert.showAndWait().ifPresent(response -> {
		        	if (response == ButtonType.OK) {
		            	try {  
		            		ClientDao daoSQLClient = DaoFactory.getDaoFactory("MySql").getClientDao();
			    			daoSQLClient.update(clientsObservableList.get(selectedIndex));
			    			tableClients.getItems().set(selectedIndex, clientsObservableList.get(selectedIndex));
			    			
		    	    	} catch (IllegalArgumentException iae) {
		    	    		this.lblMessage.setTextFill(Color.RED);
		    	    		this.lblMessage.setText(iae.getMessage());
		    	    		
		    	    	} catch (SQLException e) {
		    	    		this.lblMessage.setTextFill(Color.RED);
		    				this.lblMessage.setText(e.getMessage());
		    			}
		        	}
		        });
			} catch (IllegalArgumentException iae) {
	    		this.lblMessage.setTextFill(Color.RED);
	    		this.lblMessage.setText(iae.getMessage());
	    	}
		}
	    // Aucun item n'a été sélectionné
	    else {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        //alert.initOwner(window);
	        alert.setTitle("Erreur");
	        alert.setHeaderText(null);
	        alert.setContentText("Veuillez sélectionner un client");
	
	        alert.showAndWait();
	    }
    	
    }
    
    @FXML private void supprimerClient() {
    	updateLblMessage();
    	int selectedIndex = tableClients.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        //alert.initOwner(window);
	        alert.setHeaderText(null);
	        alert.setContentText(
	        		"Supprimer le client " +
	        		clientsObservableList.get(selectedIndex).toString() +
	        		" ?");
	
	        // Permet l'utilisation native du composant Alert pour la validation
	        // Utilisation complexe d'une fonction lambda trouvée sur la doc api d'oracle
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {  
	            		ClientDao daoSQLClient = DaoFactory.getDaoFactory("MySql").getClientDao();
    	    			daoSQLClient.delete(this.colId.getCellData((clientsObservableList.get(selectedIndex))));
    	    			tableClients.getItems().remove(selectedIndex);
    	    			
	    	    	} catch (IllegalArgumentException iae) {
	    	    		this.lblMessage.setTextFill(Color.RED);
	    	    		this.lblMessage.setText(iae.getMessage());
	    	    	} catch (SQLException e) {
	    	    		this.lblMessage.setTextFill(Color.RED);
	    				this.lblMessage.setText(e.getMessage());
	    			}
	        	}
	        });
		}
        // Aucun item n'a été sélectionné
        else {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        //alert.initOwner(window);
	        alert.setTitle("Erreur");
	        alert.setHeaderText(null);
	        alert.setContentText("Veuillez sélectionner un client");
	
	        alert.showAndWait();
        }
    }
    
    public void updateLblMessage() {
    	this.lblMessage.setText("");
    }
    
    @FXML public void initialize() {
    	// Affecte les donnees aux colonnes
    	this.colId.setCellValueFactory(cellData -> cellData.getValue().idClientProperty().asObject());
    	this.colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
    	this.colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
    	
    	// Initialise les details du client
    	afficherDetailsClient(null);
    	
    	// Fait interagir la selection sur le tableau et affiche les details du client
    	this.tableClients.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)-> afficherDetailsClient(newValue));
    }
}