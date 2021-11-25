/**
 * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Contrôleur de la page produit
 * * * * * * * * * * * * * * * *
 */

package controllers;

import java.util.ArrayList;

import dao.factory.DaoFactory;
import dao.pojo.ProduitDao;
import dao.pojo.TypeProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import pojo.Produit;
import pojo.TypeProduit;

public class ProduitController {

	@FXML private TextField textRecherche;
    @FXML private TableView<Produit> tableProduit;
    @FXML private TableColumn<Produit, Integer> colIdProduit;
    @FXML private TableColumn<Produit, String> colLibelle;
    @FXML private TableColumn<Produit, String> colType;
    @FXML private TableColumn<Produit, Double> colPrix;
    @FXML private TextField textLibelle;
    @FXML private TextField textPrix;
    @FXML private ComboBox<TypeProduit> comboTypeProduit;
    @FXML private Label lblMessage;
    
    private ObservableList<Produit> produitObservableList = FXCollections.observableArrayList();
	private ArrayList<Produit> produits = new ArrayList<Produit>();
	private ProduitDao daoProduit = DaoFactory.getCurrentDao().getProduitDao();
    
    private ObservableList<TypeProduit> typeProduitObservableList = FXCollections.observableArrayList();
	private ArrayList<TypeProduit> typesProduit = new ArrayList<TypeProduit>();
	private TypeProduitDao daoType = DaoFactory.getCurrentDao().getTypeProduitDao();
    
    // Permet d'annuler la sélection dans la table si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
	
    @FXML public void initialize() {
    	System.out.println("contrôleur produit appelé");

    	setupTable();
    	setupComboBox();
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
    	this.colIdProduit.setCellValueFactory(cellData -> cellData.getValue().idProduitProperty().asObject());
    	this.colLibelle.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
    	this.colType.setCellValueFactory(cellData -> cellData.getValue().getTypeProduit().libelleProperty());
    	this.colPrix.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
    	
    	// Fait interagir la sélection sur le tableau pour afficher les détails du produit
    	this.tableProduit.getSelectionModel().selectedItemProperty().addListener(
    		(observable, oldValue, newValue) ->  afficherDetailsProduit(newValue));
    	
    	// Gère le clic de la souris
    	this.tableProduit.setOnMouseClicked(
    		(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = tableProduit.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (oldSelectedIndex != selectedIndex) {
    	    		oldSelectedIndex = selectedIndex ;
    	    		tableProduit.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		tableProduit.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
    		}
    	);
    }
    
    @FXML private void updateTable() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.produitObservableList.clear();
    	this.textRecherche.setText("");
    	try {
        	// Récupère la liste des produits dans la persistance
            this.produits = this.daoProduit.getAll();
            
            // Ajoute la liste à la liste observable
            this.produitObservableList.addAll(produits);
            
            // Fixe la TableView avec la liste observable
            this.tableProduit.setItems(this.produitObservableList);
            
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
			    	this.produitObservableList.clear();
			    	this.produits.clear();
			    	try {
			        	// Récupère la liste des produits
			            this.produits = this.daoProduit.rechercherLibelleOuType(this.textRecherche.getText());
			            
			            // Ajoute la liste bdd à la liste observable
			            this.produitObservableList.addAll(this.produits);
			            
			            // Fixe la TableView avec la liste observable
			            this.tableProduit.setItems(this.produitObservableList);
			    		
        	    	} catch (DataAccessException dae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(dae.getMessage());
        			}
    			}
        	}
        );
    }
    
    private void setupComboBox() {
    	try {
    		// Récupère la liste des types produit dans la persistence
    		this.typesProduit = this.daoType.getAll();
    		
        	// Ajoute la liste bdd à la liste observable
        	this.typeProduitObservableList.addAll(this.typesProduit);
        	
        	// Fixe le comboBox avec la liste observable
        	this.comboTypeProduit.setItems(this.typeProduitObservableList);
            
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText("La base de données contient des erreurs, veuillez contacter l'administrateur");
           
    	} catch (DataAccessException dae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(dae.getMessage());
		}
    }
    
    private void afficherDetailsProduit (Produit produit) {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	if (produit != null) {
    		/*
    		TypeProduit t = new TypeProduit();
    		t.setLibelle(produit.getTypeProduit().getLibelle());
    		produit.getTypeProduit().setIdType(this.typeProduitObservableList.indexOf(t));
    		*/
    		//TODO
    		//produit.getTypeProduit().setIdType(1);
    		
    		this.textLibelle.setText(produit.getLibelle());
    		//this.comboTypeProduit.setValue(produit.getTypeProduit());
    		this.textPrix.setText(Double.toString(produit.getPrix()));
    	}
    	// Produit null
    	else {
    		this.textLibelle.setText("");
    		this.comboTypeProduit.setValue(null);
    		this.textPrix.setText("");
    	}
    }
    
    public void updateLblMessage() {
    	this.lblMessage.setText(null);
    }
    
    @FXML private void ajouter() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère les saisies pour créer un nouveau produit
    	try {
    		if (this.comboTypeProduit.getValue() == null)
    			throw new IllegalArgumentException("Veuillez choisir un type produit");
    		
    		else if (this.textPrix.getText().trim().length() == 0)
    			throw new IllegalArgumentException("Veuillez saisir un prix unitaire");
    		
        	Produit produit = new Produit(this.textLibelle.getText(), this.comboTypeProduit.getValue().getIdType(), Double.parseDouble(this.textPrix.getText().replace(",",".")));
        	
        	// Ouvre une fenêtre d'alerte pour confirmer
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(
            	"Ajouter le produit " +
            	produit.getLibelle() + " ?");
            
            // Permet l'utilisation native du composant Alert pour la validation
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {  
                		// On insère le produit dans la base de données si les saisies sont correctes
            			int idProduit = daoProduit.create(produit);
            			produit.setIdProduit(idProduit);
            			produit.getTypeProduit().setLibelle(this.comboTypeProduit.getValue().getLibelle());
            			
            			// On ajoute le produit dans la TableView
            			tableProduit.getItems().add(produit);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Produit ajouté");
            			
        	    	} catch (DataAccessException dae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(dae.getMessage());
        	    		
        	    	} catch (ObjectAlreadyExistsException oaee) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(oaee.getMessage());
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
    	int selectedIndex = tableProduit.getSelectionModel().getSelectedIndex();
    	
    	try {
			// Si un item a été sélectionné
			if (selectedIndex >= 0) {
	    		if (this.comboTypeProduit.getValue() == null)
	    			throw new IllegalArgumentException("Veuillez choisir un type produit");
	    		
	    		else if (this.textPrix.getText().trim().length() == 0)
	    			throw new IllegalArgumentException("Veuillez saisir un prix unitaire");
				
	    		// On teste les saisies de l'utilisateur
				new Produit(this.textLibelle.getText(), this.comboTypeProduit.getValue().getIdType(), Double.parseDouble(this.textPrix.getText().replace(",",".")));
		        
				// Ouvre une fenêtre d'alerte pour confirmer
				Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setHeaderText(null);
		        alert.setContentText(
		        	"Valider les modifications sur le produit " +
		        	this.produitObservableList.get(selectedIndex).getLibelle() + " ?");
		
		        // Permet l'utilisation native du composant Alert pour la validation
		        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		        alert.showAndWait().ifPresent(response -> {
		        	if (response == ButtonType.OK) {
		            	try {  
		            		this.produitObservableList.get(selectedIndex).setLibelle(this.textLibelle.getText());
		            		this.produitObservableList.get(selectedIndex).getTypeProduit().setIdType(this.comboTypeProduit.getValue().getIdType());
		    	    		this.produitObservableList.get(selectedIndex).setPrix(Double.parseDouble(this.textPrix.getText().replace(",", ".")));
		            		
		    	    		// On modifie le produit dans la bdd si les saisies sont correctes
			    			this.daoProduit.update(this.produitObservableList.get(selectedIndex));
			    			
			    			// On met à jour la TableView
			    			this.tableProduit.getItems().set(selectedIndex, this.produitObservableList.get(selectedIndex));
			    			
			    			// Message de succès
			    			this.lblMessage.setTextFill(Color.GREEN);
			    			this.lblMessage.setText("Produit mis à jour");
			    			
	        	    	} catch (DataAccessException dae) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(dae.getMessage());
	        	    		
	        	    	} catch (ObjectNotExistsException one) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(one.getMessage());
	        	    		
	        	    	} catch (ObjectAlreadyExistsException oaee) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(oaee.getMessage());
	        	    	}
		        	}
		        });
			}
			// Aucun item n'a été sélectionné 
		    else {
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle("Erreur");
		        alert.setHeaderText(null);
		        alert.setContentText("Veuillez sélectionner un produit");
		
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
    	int selectedIndex = this.tableProduit.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setHeaderText(null);
	        alert.setContentText(
	        		"Supprimer le produit " +
	        		this.produitObservableList.get(selectedIndex).getLibelle() + " ?");
	
	        // Permet l'utilisation native du composant Alert pour la validation
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {  
    	    			this.daoProduit.delete(this.colIdProduit.getCellData(produitObservableList.get(selectedIndex)));
            			
    	    			// On met à jour la TableView
            			this.tableProduit.getItems().remove(selectedIndex);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Produit supprimé");
    	    			
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
	        alert.setContentText("Veuillez sélectionner un produit");
	
	        alert.showAndWait();
        }
    }
}