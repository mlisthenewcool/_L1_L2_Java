package controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import app.ViewNavigator;
import dao.factory.DaoFactory;
import dao.pojo.FactureDao;
import exceptions.DataAccessException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import pojo.Facture;

public class FactureController {
	
	@FXML private TextField textRecherche;
    @FXML private TableView<Facture> tableFacture;
    @FXML private TableColumn<Facture, Integer> colIdFacture;
    @FXML private TableColumn<Facture, String> colClient;
    @FXML private TableColumn<Facture, String> colDate;
    @FXML private TableColumn<Facture, Double> colTotal;
    @FXML private Label lblMessage;
    
    private ObservableList<Facture> factureObservableList = FXCollections.observableArrayList();
    private ArrayList<Facture> factures = new ArrayList<Facture>();
	private FactureDao daoFacture = DaoFactory.getCurrentDao().getFactureDao();
	
    // Permet d'annuler la sélection dans la table si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
	
    @FXML public void initialize() {
    	System.out.println("contrôleur facture appelé");
    	
    	setupTable();
    	updateTable();
    	rechercher();
    }
    
    private void setupTable() {
    	// Affecte les données aux colonnes
    	this.colIdFacture.setCellValueFactory(cellData -> cellData.getValue().idFactureProperty().asObject());

    	this.colClient.setCellValueFactory(
    		cellData -> {
    			SimpleStringProperty property = new SimpleStringProperty();
    			property.setValue(
    				cellData.getValue().getClient().getNom() + " " + 
    				cellData.getValue().getClient().getPrenom());
    			return property;
    		});
    	this.colDate.setCellValueFactory(
    			cellData -> {
    				SimpleStringProperty property = new SimpleStringProperty();
    				DateFormat dateFormat = new SimpleDateFormat("EEEEEEEE d MMMMMMMMM yyyy");
    				property.setValue(dateFormat.format(cellData.getValue().getDateFacture())); // .getTime() pour Date
    				return property;
    			});
    	this.colTotal.setCellValueFactory(cellData -> cellData.getValue().totalTTCProperty().asObject());
    	
		// Gère le clic de la souris
		this.tableFacture.setOnMouseClicked(
			(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = tableFacture.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (oldSelectedIndex != selectedIndex) {
    	    		oldSelectedIndex = selectedIndex ;
    	    		tableFacture.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		tableFacture.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
    		}
	    );
    }
    
    @FXML private void updateTable() {
    	this.factureObservableList.clear();
    	this.textRecherche.setText("");
    	try {
        	// Récupère la liste des types produit en base de données
            this.factures = daoFacture.getAll();
            
            // Ajoute la liste bdd à la liste observable
            this.factureObservableList.addAll(this.factures);
            
            // Fixe la table avec la liste observable
            this.tableFacture.setItems(this.factureObservableList);
            
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText("La base de données contient des erreurs, veuillez contacter l'administrateur");
           
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
    	this.textRecherche.setOnKeyPressed(
    		(event) -> { 
    			if(event.getCode() == KeyCode.ENTER) { 
			    	this.factureObservableList.clear();
			    	try {
			        	// Récupère la liste des types produit en base de données
			            this.factures = daoFacture.rechercherClient(this.textRecherche.getText());
			            
			            // Ajoute la liste bdd à la liste observable
			            this.factureObservableList.addAll(this.factures);
			            
			            // Fixe la table avec la liste observable
			            this.tableFacture.setItems(this.factureObservableList);
			            
			    	} catch (DataAccessException dae) {
			    		this.lblMessage.setTextFill(Color.RED);
			    		this.lblMessage.setText(dae.getMessage());
					}
    			}
    		}
    	);
    }
    
    private void updateLblMessage() {
    	this.lblMessage.setText(null);
    }
    
	@FXML private void ajouter() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_FACTURATION);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
    
	@FXML private void supprimer() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = this.tableFacture.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setHeaderText(null);
	        alert.setContentText(
	        		"Supprimer la facture " +
	        		this.factureObservableList.get(selectedIndex).getIdFacture() + " ?");
	
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {  
    	    			this.daoFacture.delete(this.colIdFacture.getCellData(factureObservableList.get(selectedIndex)));
            			
    	    			// On met à jour la TableView
            			this.tableFacture.getItems().remove(selectedIndex);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Facture supprimée");

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
	        alert.setContentText("Veuillez sélectionner une facture");
	
	        alert.showAndWait();
        }
	}
}