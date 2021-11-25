/**
 * * * * * * * * * * * * * * * *
// * @author MagicBanana
 * 
 * Contrôleur de la page client
 * * * * * * * * * * * * * * * *
 */

package controllers;

import java.util.ArrayList;

import dao.factory.DaoFactory;
import dao.pojo.ClientDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import pojo.Client;

public class ClientController {
	
    @FXML private TextField textRecherche;
    @FXML private TableView<Client> tableClient;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String> colNom;
    @FXML private TableColumn<Client, String> colPrenom;
    @FXML private TextField textNom;
    @FXML private TextField textPrenom;
    @FXML private Label lblMessage;
    
    private ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
    private ArrayList<Client> clients =  new ArrayList<Client>();
    private ClientDao daoClient = DaoFactory.getCurrentDao().getClientDao();
    
    // Permet d'annuler la sélection dans la TableView si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
    
    @FXML public void initialize() {
    	System.out.println("contrôleur client appelé");
    	
    	setupTable();
    	updateTable();
    	rechercher();
    }
    
    /**
     * * * * * * * * * * * * * *
     * Initialise la TableView
     * * * * * * * * * * * * * *
     */
    private void setupTable() {  	
    	// Affecte les données aux colonnes
    	this.colId.setCellValueFactory(cellData -> cellData.getValue().idClientProperty().asObject());
    	this.colNom.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
    	this.colPrenom.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
    	
    	// Fait interagir la sélection sur le tableau pour afficher les détails du client
    	this.tableClient.getSelectionModel().selectedItemProperty().addListener(
    		(observable, oldValue, newValue) ->  afficherDetailsClient(newValue)
    	);
    	
		// Gère le clic de la souris
		this.tableClient.setOnMouseClicked(
			(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = tableClient.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (oldSelectedIndex != selectedIndex) {
    	    		oldSelectedIndex = selectedIndex ;
    	    		tableClient.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		tableClient.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
    		}
		);
    }
    
    @FXML private void updateTable() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.clientObservableList.clear();
    	//this.clients.clear();
    	this.textRecherche.setText("");
    	
    	try {
    		// Récupère les clients dans la solution de persistance
    		this.clients = daoClient.getAll();
    		
            // Ajoute la liste de clients à la liste observable
            this.clientObservableList.addAll(this.clients);
            
            // Fixe la table avec la liste observable
            this.tableClient.setItems(this.clientObservableList);
            
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText("La solution de persistance contient des erreurs, veuillez contacter l'administrateur");
            
    	} catch (DataAccessException dae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(dae.getMessage());
		}
    }
    
    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * Permet d'utiliser la recherche seulement quand
     * la touche ENTRER est appuyée sur le focus du TextField
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */
    private void rechercher() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.textRecherche.setOnKeyPressed(
    		(event) -> { 
    			if(event.getCode() == KeyCode.ENTER) { 
    		    	this.clientObservableList.clear();
    		    	this.clients.clear();
    		    	try {
    		    		this.clients = this.daoClient.rechercherNomOuPrenom(this.textRecherche.getText());
    		    		
    		    		this.clientObservableList.addAll(clients);
    		    		
    		    		this.tableClient.setItems(this.clientObservableList);
    		    		
			    	} catch (IllegalArgumentException iae) {
			    		this.lblMessage.setTextFill(Color.RED);
			    		this.lblMessage.setText("La base de données contient des erreurs, veuillez contacter l'administrateur");
			    		
        	    	} catch (DataAccessException dae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(dae.getMessage());
        			}
    			} 
    		}
    	);
    }
       
    private void afficherDetailsClient (Client client) {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	if (client != null) {
    		this.textNom.setText(client.getNom());
    		this.textPrenom.setText(client.getPrenom());
    	}
    	// Client null
    	else {
    		this.textNom.setText("");
    		this.textPrenom.setText("");
    	}
    }
    
    private void updateLblMessage() {
    	this.lblMessage.setText(null);
    }
    
    @FXML private void ajouter() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère les saisies pour créer un nouveau client
    	try {
        	Client client = new Client(this.textNom.getText(), this.textPrenom.getText());

        	// Ouvre une fenêtre d'alerte pour confirmer
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(
            	"Ajouter le client " +
            	client.getNom() + " " + 
            	client.getPrenom() + " ?");
            
            // Permet l'utilisation native du composant Alert pour la validation
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {  
                		// On insère le client dans la solution de persistance
                		// si les saisies sont correctes
            			int idClient = this.daoClient.create(client);
            			client.setIdClient(idClient);
            			
            			// On ajoute le client dans la TableView
            			this.tableClient.getItems().add(client);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Client ajouté");
            			
        	    	} catch (ObjectAlreadyExistsException oaee) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(oaee.getMessage());
        	    		
        	    	} catch (DataAccessException dae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(dae.getMessage());
        			}
            	}
            });
        // Si les saisies de l'utilisateur sont incorrectes 
    	} catch (IllegalArgumentException iae) {
       		this.lblMessage.setTextFill(Color.RED);
       		this.lblMessage.setText(iae.getMessage());
      	}
    }
    
    @FXML private void modifier() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = tableClient.getSelectionModel().getSelectedIndex();
    	
    	try {   	
			// Si un item a été sélectionné
			if (selectedIndex >= 0) {
		    	// On teste les saisies de l'utilisateur
				new Client(this.textNom.getText(), this.textPrenom.getText());
				
				// Ouvre une fenêtre d'alerte pour confirmer
				Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setHeaderText(null);
		        alert.setContentText(
		        	"Valider les modifications sur le client " +
		        	clientObservableList.get(selectedIndex).toString() + " ?");
		
		        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		        alert.showAndWait().ifPresent(response -> {
		        	if (response == ButtonType.OK) {
		            	try {
		            		clientObservableList.get(selectedIndex).setNom(this.textNom.getText());
		    	    		clientObservableList.get(selectedIndex).setPrenom(this.textPrenom.getText());
		            		
			    			this.daoClient.update(clientObservableList.get(selectedIndex));
			    			
			    			// On met à jour la TableView
			    			tableClient.getItems().set(selectedIndex, clientObservableList.get(selectedIndex));
			    			
	            			// Message de succès
	            			this.lblMessage.setTextFill(Color.GREEN);
	            			this.lblMessage.setText("Client mis à jour");
			    			
	        	    	} catch (DataAccessException dae) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(dae.getMessage());
	        			
	        	    	} catch (ObjectAlreadyExistsException oaee) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(oaee.getMessage());
	        	    		
	        	    	} catch (ObjectNotExistsException onee) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(onee.getMessage());
	        	    	}
		        	}
		        });
			}
		    // Aucun item n'a été sélectionné
		    else {
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Erreur");
		        alert.setHeaderText(null);
		        alert.setContentText("Veuillez sélectionner un client");
		
		        alert.showAndWait();
		    }
			
        // Si les saisies de l'utilisateur sont incorrectes 
    	} catch (IllegalArgumentException iae) {
       		this.lblMessage.setTextFill(Color.RED);
       		this.lblMessage.setText(iae.getMessage());
      	}
    }
    
    @FXML private void supprimer() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = tableClient.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setHeaderText(null);
	        alert.setContentText(
	        		"Supprimer le client " +
	        		this.clientObservableList.get(selectedIndex).toString() + " ?");
	
	        // Permet l'utilisation native du composant Alert pour la validation
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {
    	    			this.daoClient.delete(this.colId.getCellData((this.clientObservableList.get(selectedIndex))));
	            		
    	    			// On met à jour la TableView
    	    			this.tableClient.getItems().remove(selectedIndex);
    	    			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Client supprimé");
    	    			
        	    	} catch (DataAccessException dae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(dae.getMessage());
        	    		
        	    	} catch (ObjectNotExistsException onee) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(onee.getMessage());
	            	
	            	} catch (ObjectConstraintException oce) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(oce.getMessage());
	            	}	
	        	}
	        });
		}
        // Aucun item n'a été sélectionné
        else {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Erreur");
	        alert.setHeaderText(null);
	        alert.setContentText("Veuillez sélectionner un client");
	
	        alert.showAndWait();
        }
    }
}