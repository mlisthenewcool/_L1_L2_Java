/**
 * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Contrôleur de la page TVA
 * * * * * * * * * * * * * * *
 */

package controllers;

import java.util.ArrayList;

import dao.factory.DaoFactory;
import dao.pojo.TVADao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
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
import pojo.TVA;

public class TVAController {

	@FXML private TextField textRecherche;
    @FXML private TableView<TVA> tableTVA;
    @FXML private TableColumn<TVA, Integer> colId;
    @FXML private TableColumn<TVA, String> colLibelle;
    @FXML private TableColumn<TVA, Double> colTaux;
    @FXML private TextField textLibelle;
    @FXML private TextField textTaux;
    @FXML private Label lblMessage;
    
    private ObservableList<TVA> TVAObservableList = FXCollections.observableArrayList();
    
    // Permet d'annuler la sélection dans la table si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
	
    @FXML public void initialize() {
    	System.out.println("contrôleur TVA appelé");
    	
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
    	this.colId.setCellValueFactory(cellData -> cellData.getValue().idTvaProperty().asObject());
    	this.colLibelle.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
    	this.colTaux.setCellValueFactory(cellData -> cellData.getValue().tauxProperty().asObject());
    	
    	// Fait interagir la sélection sur le tableau pour afficher les détails de la TVA
    	this.tableTVA.getSelectionModel().selectedItemProperty().addListener(
    			(observable, oldValue, newValue)-> afficherDetailsTVA(newValue));
    	
		// Gère le clic de la souris
		this.tableTVA.setOnMouseClicked(
			(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = tableTVA.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (oldSelectedIndex != selectedIndex) {
    	    		oldSelectedIndex = selectedIndex ;
    	    		tableTVA.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		tableTVA.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
	    	}
    	);
    }
    
    @FXML private void updateTable() {
    	ArrayList<TVA> TVABdd = new ArrayList<TVA>();
    	this.TVAObservableList.clear();
    	this.textRecherche.setText("");
    	try {
        	// Récupère la liste des TVA en bdd
    		TVADao daoSQLTVA = DaoFactory.getCurrentDao().getTVADao();
            TVABdd = daoSQLTVA.getAll();
            
            // Ajoute la liste bdd à la liste observable
            this.TVAObservableList.addAll(TVABdd);
            
            // Fixe la TableView avec la liste observable
            this.tableTVA.setItems(this.TVAObservableList);
            
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
			    	ArrayList<TVA> TVABdd = new ArrayList<TVA>();
			    	this.TVAObservableList.clear();
			    	try {
			        	// Récupère la liste des TVA en bdd
			    		TVADao daoSQLTVA = DaoFactory.getCurrentDao().getTVADao();
			            TVABdd = daoSQLTVA.rechercherLibelle(this.textRecherche.getText());
			            
			            // Ajoute la liste bdd à la liste observable
			            this.TVAObservableList.addAll(TVABdd);
			            
			            // Fixe la TableView avec la liste observable
			            this.tableTVA.setItems(this.TVAObservableList);
			            
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
       
    private void afficherDetailsTVA (TVA tva) {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	if (tva != null) {
    		this.textLibelle.setText(tva.getLibelle());
    		this.textTaux.setText(Double.toString(tva.getTaux()));
    	}
    	// TVA null
    	else {
    		this.textLibelle.setText("");
    		this.textTaux.setText("");
    	}
    }
    
    private void updateLblMessage() {
    	this.lblMessage.setText(null);
    }
    
    @FXML private void ajouter() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère les saisies pour créer une nouvelle TVA
    	try {
    		if (this.textTaux.getText().trim().length() == 0)
    			throw new IllegalArgumentException("Veuillez saisir un taux");
        	
    		TVA tva = new TVA(this.textLibelle.getText(), Double.parseDouble(this.textTaux.getText()));

    		// Ouvre une fenêtre d'alerte pour confirmer
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(
            	"Ajouter la TVA " +
            	tva.getLibelle() + " ?");
            
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {  
                		// On insère le client dans la base de données si les saisies sont correctes
                		TVADao daoSQLTVA = DaoFactory.getCurrentDao().getTVADao();
            			int id_tva = daoSQLTVA.create(tva);
            			tva.setIdTva(id_tva);
            			
            			// On ajoute le client à la TableView
            			tableTVA.getItems().add(tva);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("TVA ajoutée");
            			
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
    	int selectedIndex = tableTVA.getSelectionModel().getSelectedIndex();
    	
    	try {
			// Si un item a été sélectionné
			if (selectedIndex >= 0) {
				// On teste les saisies de l'utilisateur
				new TVA(this.textLibelle.getText(), Double.parseDouble(this.textTaux.getText().replace(",", ".")));
				
				// Ouvre une fenêtre d'alerte pour confirmer
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setHeaderText(null);
		        alert.setContentText(
		        	"Valider les modifications sur la TVA " +
		        	this.TVAObservableList.get(selectedIndex).getLibelle() + " ?");
		
		        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		        alert.showAndWait().ifPresent(response -> {
		        	if (response == ButtonType.OK) {
		            	try {  
		            		this.TVAObservableList.get(selectedIndex).setLibelle(this.textLibelle.getText());
		    	    		this.TVAObservableList.get(selectedIndex).setTaux(Double.parseDouble(this.textTaux.getText()));
		            		
		    	    		// On modifie la TVA dans la bdd si les saisies sont correctes
		            		TVADao daoSQLTVA = DaoFactory.getCurrentDao().getTVADao();
				    		daoSQLTVA.update(this.TVAObservableList.get(selectedIndex));
				    		
				    		// On met à jour la TableView
				    		this.tableTVA.getItems().set(selectedIndex, TVAObservableList.get(selectedIndex));
				    			
			    			// Message de succès
			    			this.lblMessage.setTextFill(Color.GREEN);
			    			this.lblMessage.setText("TVA mise à jour");
			    			
	        	    	} catch (DataAccessException dae) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(dae.getMessage());
	        	    		
	        	    	} catch (ObjectNotExistsException onee) {
	        	    		this.lblMessage.setTextFill(Color.RED);
	        	    		this.lblMessage.setText(onee.getMessage());
	        	    		
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
		        alert.setContentText("Veuillez sélectionner une TVA");
		
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
    	int selectedIndex = this.tableTVA.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setHeaderText(null);
	        alert.setContentText(
	        	"Supprimer la TVA " +
	        	this.TVAObservableList.get(selectedIndex).getLibelle() + " ?");
	
	        // Permet l'utilisation native du composant Alert pour la validation
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {  
	            		TVADao daoSQLTVA = DaoFactory.getCurrentDao().getTVADao();
    	    			daoSQLTVA.delete(this.colId.getCellData(TVAObservableList.get(selectedIndex)));
    	    			
    	    			// On met à jour la TableView
    	    			this.tableTVA.getItems().remove(selectedIndex);
    	    			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("TVA supprimée");
    	    			
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
	        alert.setContentText("Veuillez sélectionner une TVA");
	
	        alert.showAndWait();
        }
    }
}