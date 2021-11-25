package controllers.reduction;

import java.util.ArrayList;

import dao.factory.DaoFactory;
import dao.pojo.ProduitDao;
import dao.pojo.reduction.ReductionSurProduitDao;
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
import javafx.scene.paint.Color;
import pojo.Produit;
import pojo.reduction.ReductionSurProduit;

public class ReductionSurProduitController {

	@FXML private TextField textRecherche;
	@FXML private TableView<ReductionSurProduit> tableReduction;
	@FXML private TableColumn<ReductionSurProduit, Integer> colId;
	@FXML private TableColumn<ReductionSurProduit, Integer> colTaux;
	@FXML private TableColumn<ReductionSurProduit, String> colProduit;
	@FXML private TableColumn<ReductionSurProduit, Integer> colQuantite;
	@FXML private ComboBox<Produit> comboProduit;
	@FXML private TextField textTaux;
	@FXML private TextField textQuantite;
	@FXML private Label lblMessage;
	
	private ObservableList<ReductionSurProduit> reductionObservableList = FXCollections.observableArrayList();
	private ArrayList<ReductionSurProduit> reductions = new ArrayList<ReductionSurProduit>();
	private ReductionSurProduitDao daoReduction = DaoFactory.getCurrentDao().getReductionSurProduitDao();
	
	private ObservableList<Produit> produitObservableList = FXCollections.observableArrayList();
	private ArrayList<Produit> produits = new ArrayList<Produit>();
	private ProduitDao daoProduit = DaoFactory.getCurrentDao().getProduitDao();
	
    // Permet d'annuler la sélection dans la TableView si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
	
	@FXML public void initialize() {
		System.out.println("contrôleur réduction sur produit appelé");
		
    	setupTable();
    	setupComboBox();
    	updateTable();
    	//rechercher();
	}
	
	/**
     * * * * * * * * * * * * * *
     * Initialise la TableView
     * * * * * * * * * * * * * *
     */
    private void setupTable() {  	
    	// Affecte les données aux colonnes
    	this.colId.setCellValueFactory(cellData -> cellData.getValue().idReductionProperty().asObject());
    	this.colTaux.setCellValueFactory(cellData -> cellData.getValue().tauxReductionProperty().asObject());
    	this.colProduit.setCellValueFactory(cellData -> cellData.getValue().getProduit().libelleProperty());
    	this.colQuantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty().asObject());
    	
    	// Fait interagir la sélection sur le tableau pour afficher les détails du client
    	this.tableReduction.getSelectionModel().selectedItemProperty().addListener(
    		(observable, oldValue, newValue) ->  afficherDetailsReduction(newValue)
    	);
    	
		// Gère le clic de la souris
		this.tableReduction.setOnMouseClicked(
			(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = this.tableReduction.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (this.oldSelectedIndex != selectedIndex) {
    	    		this.oldSelectedIndex = selectedIndex ;
    	    		this.tableReduction.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		this.tableReduction.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
    		}
		);
    }
    
    @FXML private void updateTable() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.reductionObservableList.clear();
    	this.textRecherche.setText("");
    	
    	try {
    		// Récupère les réductions dans la solution de persistance
    		this.reductions = this.daoReduction.getAll();
    		
            // Ajoute la liste de réductions à la liste observable
            this.reductionObservableList.addAll(this.reductions);
            
            // Fixe la table avec la liste observable
            this.tableReduction.setItems(this.reductionObservableList);
            
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText("La solution de persistance contient des erreurs, veuillez contacter l'administrateur");
            
    	} catch (DataAccessException dae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(dae.getMessage());
		}
    }
    
	private void setupComboBox() {
		try {
			// Récupère la liste des produits
			this.produits = this.daoProduit.getAll();
			
			// Ajoute l'array liste à la liste observable
			this.produitObservableList.addAll(this.produits);
			
			// Fixe le comboBox avec la liste observable
			this.comboProduit.setItems(this.produitObservableList);
			
    	} catch (DataAccessException dae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(dae.getMessage());
		}
	}
    
    private void afficherDetailsReduction (ReductionSurProduit reduction) {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	if (reduction != null) {
    		this.textQuantite.setText(Integer.toString(reduction.getQuantite()));
    		this.textTaux.setText(Integer.toString(reduction.getTauxReduction()));
    		// TODO combobox
    	}
    	// Réduction null
    	else {
    		this.textQuantite.setText("");
    		this.textTaux.setText("");
    		// TODO combobox
    	}
    }
    
    private void updateLblMessage() {
    	this.lblMessage.setText(null);
    }
    
    @FXML 
    private void ajouter() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	try {
    		if (this.textTaux.getText().trim().length() == 0)
    			throw new IllegalArgumentException("Veuillez choisir un taux");
    		
    		else if (this.comboProduit.getValue() == null)
    			throw new IllegalArgumentException("Veuillez choisir un produit");
    		
    		else if (this.textQuantite.getText().trim().length() == 0)
    			throw new IllegalArgumentException("Veuillez saisir une quantité");
    		
    		ReductionSurProduit reduction = new ReductionSurProduit(Integer.parseInt(this.textTaux.getText()), this.comboProduit.getValue().getIdProduit(), Integer.parseInt(this.textQuantite.getText()));
    		
        	// Ouvre une fenêtre d'alerte pour confirmer
        	Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText(
            	"Ajouter la réduction ?");
            
            // Permet l'utilisation native du composant Alert pour la validation
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {  
                		// On insère la réduction si les saisies sont correctes
            			int idReduction = this.daoReduction.create(reduction);
            			reduction.setIdReduction(idReduction);
            			
            			// On ajoute la réduction dans la TableView
            			this.tableReduction.getItems().add(reduction);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Réduction ajoutée");
            			
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
    
    @FXML 
    private void modifier() {
       	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = tableReduction.getSelectionModel().getSelectedIndex();
    	
    	try {
			// Si un item a été sélectionné
			if (selectedIndex >= 0) {
	    		if (this.textTaux.getText().trim().length() == 0)
	    			throw new IllegalArgumentException("Veuillez choisir un taux");
	    		
	    		else if (this.comboProduit.getValue() == null)
	    			throw new IllegalArgumentException("Veuillez choisir un produit");
	    		
	    		else if (this.textQuantite.getText().trim().length() == 0)
	    			throw new IllegalArgumentException("Veuillez saisir une quantité");
				
	    		// On teste les saisies de l'utilisateur
				new ReductionSurProduit(
					Integer.parseInt(this.textTaux.getText()),
					this.comboProduit.getValue().getIdProduit(),
					Integer.parseInt(this.textQuantite.getText())
				);
		        
				// Ouvre une fenêtre d'alerte pour confirmer
				Alert alert = new Alert(AlertType.CONFIRMATION);
		        alert.setHeaderText(null);
		        alert.setContentText(
		        	"Valider les modifications sur la réduction ?");
		
		        // Permet l'utilisation native du composant Alert pour la validation
		        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		        alert.showAndWait().ifPresent(response -> {
		        	if (response == ButtonType.OK) {
		            	try {  
		            		this.reductionObservableList.get(selectedIndex).setTauxReduction(Integer.parseInt(this.textTaux.getText()));
		            		this.reductionObservableList.get(selectedIndex).getProduit().setIdProduit(this.comboProduit.getValue().getIdProduit());
		            		this.reductionObservableList.get(selectedIndex).setQuantite(Integer.parseInt(this.textQuantite.getText()));
		            		
		    	    		// On modifie la réduction si les saisies sont correctes
			    			this.daoReduction.update(this.reductionObservableList.get(selectedIndex));
			    			
			    			// On met à jour la TableView
			    			this.tableReduction.getItems().set(selectedIndex, this.reductionObservableList.get(selectedIndex));
			    			
			    			// Message de succès
			    			this.lblMessage.setTextFill(Color.GREEN);
			    			this.lblMessage.setText("Réduction mise à jour");
			    			
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
		        alert.setContentText("Veuillez sélectionner une réduction");
		
		        alert.showAndWait();
		    }
		// Si les saisies de l'utilisateur sont incorrectes
		} catch (IllegalArgumentException iae) {
	   		this.lblMessage.setTextFill(Color.RED);
	   		this.lblMessage.setText(iae.getMessage());
		}
    }
    
    @FXML 
    private void supprimer() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = this.tableReduction.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setHeaderText(null);
	        alert.setContentText("Supprimer la réduction ?");
	
	        // Permet l'utilisation native du composant Alert pour la validation
	        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
	        alert.showAndWait().ifPresent(response -> {
	        	if (response == ButtonType.OK) {
	            	try {  
    	    			this.daoReduction.delete(this.colId.getCellData(reductionObservableList.get(selectedIndex)));
    	    			
    	    			// On met à jour la TableView
    	    			this.tableReduction.getItems().remove(selectedIndex);
    	    			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Réduction supprimée");
    	    			
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
	        alert.setContentText("Veuillez sélectionner une réduction");
	
	        alert.showAndWait();
        }	
    }
}