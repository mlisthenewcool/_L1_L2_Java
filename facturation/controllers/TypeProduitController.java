/**
 * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Contrôleur de la page type produit
 * * * * * * * * * * * * * * * * * * *
 */

package controllers;

import java.util.ArrayList;

import dao.factory.DaoFactory;
import dao.pojo.TVADao;
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
import pojo.TVA;
import pojo.TypeProduit;

public class TypeProduitController {

	@FXML private TextField textRecherche;
    @FXML private TableView<TypeProduit> tableTypeProduit;
    @FXML private TableColumn<TypeProduit, Integer> colIdType;
    @FXML private TableColumn<TypeProduit, String> colLibelle;
    @FXML private TableColumn<TypeProduit, String> colTva;
    @FXML private TextField textLibelle;
    @FXML private ComboBox<TVA> comboTva;
    @FXML private Label lblMessage;
    
    private ObservableList<TypeProduit> typeProduitObservableList = FXCollections.observableArrayList();
    private ArrayList<TypeProduit> typesProduit = new ArrayList<TypeProduit>();
    private TypeProduitDao daoType = DaoFactory.getCurrentDao().getTypeProduitDao();
    
    private ObservableList<TVA> TVAObservableList = FXCollections.observableArrayList();
    private ArrayList<TVA> tvas = new ArrayList<TVA>();
    private TVADao daoTVA = DaoFactory.getCurrentDao().getTVADao();
    
    // Permet d'annuler la sélection dans la table si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
    
    @FXML public void initialize() {
    	System.out.println("contrôleur type produit appelé");
    	
		setupTable();
		updateTable();
		setupComboBox();
		rechercher();
    }
    
    /**
     * * * * * * * * * * * * * *
     * Initialise la TableView
     * * * * * * * * * * * * * *
     */
    private void setupTable() {
    	// Affecte les données aux colonnes
    	this.colIdType.setCellValueFactory(cellData -> cellData.getValue().idTypeProperty().asObject());
    	this.colLibelle.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
    	this.colTva.setCellValueFactory(cellData -> cellData.getValue().getTVA().libelleProperty());
    	
    	// Fait interagir la sélection sur le tableau pour afficher les détails du type produit
    	this.tableTypeProduit.getSelectionModel().selectedItemProperty().addListener(
    		(observable, oldValue, newValue)-> afficherDetailsTypeProduit(newValue));
    	
    	// Gère le clic de la souris
    	this.tableTypeProduit.setOnMouseClicked(
    		(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = tableTypeProduit.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (oldSelectedIndex != selectedIndex) {
    	    		oldSelectedIndex = selectedIndex ;
    	    		tableTypeProduit.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		tableTypeProduit.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
	    	}
    	);
    }
    
    @FXML private void updateTable() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.typeProduitObservableList.clear();
    	this.textRecherche.setText("");
    	try {
        	// Récupère la liste des types produit en base de données
            this.typesProduit = daoType.getAll();
            
            // Ajoute la liste bdd à la liste observable
            this.typeProduitObservableList.addAll(this.typesProduit);
            
            // Fixe la table avec la liste observable
            this.tableTypeProduit.setItems(this.typeProduitObservableList);
            
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText("La base de données contient des erreurs, veuillez contacter l'administrateur");
    	
    	} catch (DataAccessException dae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(dae.getMessage());
		}
    }
    
    private void setupComboBox() {
    	try {
    		// Récupère la liste des tva
    		this.tvas = daoTVA.getAll();
    		
        	// Ajoute la liste bdd à la liste observable
        	this.TVAObservableList.addAll(this.tvas);
        	
        	// Fixe le comboBox avec la liste observable
        	this.comboTva.setItems(this.TVAObservableList);
    		
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
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.textRecherche.setOnKeyPressed(
    		(event) -> {
    			if (event.getCode() == KeyCode.ENTER) {
			    	this.typeProduitObservableList.clear();
			    	this.typesProduit.clear();
			    	try {
			        	// Récupère la liste des types produit
			            this.typesProduit = this.daoType.rechercherTypeOuTva(this.textRecherche.getText());
			            
			            // Ajoute la liste bdd à la liste observable
			            this.typeProduitObservableList.addAll(this.typesProduit);
			            
			            // Fixe la table avec la liste observable
			            this.tableTypeProduit.setItems(this.typeProduitObservableList);
			            
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
    
    private void afficherDetailsTypeProduit (TypeProduit typeProduit) {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	if (typeProduit != null) {
    		this.textLibelle.setText(typeProduit.getLibelle());
    		//TODO this.comboTva.setValue(typeProduit.getIdTva());
    	}
    	// TypeProduit null
    	else {
    		this.textLibelle.setText("");
    		this.comboTva.setValue(null);
    	}
    }
    
    private void updateLblMessage() {
    	this.lblMessage.setText(null);
    }
    
    @FXML private void ajouter() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère les saisies pour créer un nouveau produit
    	try {
    		if (this.comboTva.getValue() == null)
    			throw new IllegalArgumentException("Veuillez choisir une TVA");
    		
        	TypeProduit typeProduit = new TypeProduit(this.textLibelle.getText(), this.comboTva.getValue().getIdTva());

        	// Ouvre une fenêtre d'alerte pour confirmer
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(
            	"Ajouter le type produit " +
            	typeProduit.getLibelle() + " ?");
            
            // Permet l'utilisation native du composant Alert pour la validation
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {  
                		// On insère le type produit dans la base de données si les saisies sont correctes
            			int idTypeProduit = this.daoType.create(typeProduit);
            			typeProduit.setIdType(idTypeProduit);
            			typeProduit.getTVA().setLibelle(this.comboTva.getValue().getLibelle());
            			
            			// On ajoute le type produit dans la TableView
            			tableTypeProduit.getItems().add(typeProduit);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Type produit ajouté");
            			
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
    	int selectedIndex = tableTypeProduit.getSelectionModel().getSelectedIndex();
    	
    	try {
			// Si un item a été sélectionné
			if (selectedIndex >= 0) {
	    		if (this.comboTva.getValue() == null)
	    			throw new IllegalArgumentException("Veuillez choisir une TVA");
	    		
				// On teste les saisies de l'utilisateur
				new TypeProduit (this.textLibelle.getText(), this.comboTva.getValue().getIdTva());
				
				// Ouvre une fenêtre d'alerte pour confirmer
		        Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setHeaderText(null);
		        alert.setContentText(
		        	"Valider les modifications sur le produit " +
		        	this.typeProduitObservableList.get(selectedIndex).getLibelle() + " ?");
		
		        // Permet l'utilisation native du composant Alert pour la validation
		        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		        alert.showAndWait().ifPresent(response -> {
		        	if (response == ButtonType.OK) {
		            	try {
		            		this.typeProduitObservableList.get(selectedIndex).setLibelle(this.textLibelle.getText());
		    	    		this.typeProduitObservableList.get(selectedIndex).getTVA().setIdTva(this.comboTva.getValue().getIdTva());
		            		
		    	    		// On modifie le type produit dans la bdd si les saisies sont correctes
			    			this.daoType.update(this.typeProduitObservableList.get(selectedIndex));
			    			
			    			// On met à jour la TableView
			    			this.tableTypeProduit.getItems().set(selectedIndex, this.typeProduitObservableList.get(selectedIndex));
			    			
			    			// Message de succès
			    			this.lblMessage.setTextFill(Color.GREEN);
			    			this.lblMessage.setText("Type produit mis à jour");
			    			
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
		        alert.setContentText("Veuillez sélectionner un type produit");
		
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
    	int selectedIndex = this.tableTypeProduit.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setHeaderText(null);
	        alert.setContentText(
	        	"Supprimer le type produit " +
	        	this.typeProduitObservableList.get(selectedIndex).getLibelle() + " ?");
	
	        // Permet l'utilisation native du composant Alert pour la validation
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {  
    	    			this.daoType.delete(this.colIdType.getCellData(typeProduitObservableList.get(selectedIndex)));
    	    			
            			// On met à jour la TableView
            			this.tableTypeProduit.getItems().remove(selectedIndex);
    	    			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Type produit supprimé");
            			
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
	        alert.setContentText("Veuillez sélectionner un type produit");
	
	        alert.showAndWait();
        }
    }
}