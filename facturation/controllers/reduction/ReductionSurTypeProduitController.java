package controllers.reduction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import dao.factory.DaoFactory;
import dao.pojo.TypeProduitDao;
import dao.pojo.reduction.ReductionSurTypeProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import pojo.TypeProduit;
import pojo.reduction.ReductionSurTypeProduit;

public class ReductionSurTypeProduitController {
	
	@FXML private TextField textRecherche;
	@FXML private TableView<ReductionSurTypeProduit> tableReduction;
	@FXML private TableColumn<ReductionSurTypeProduit, Integer> colId;
	@FXML private TableColumn<ReductionSurTypeProduit, Integer> colTaux;
	@FXML private TableColumn<ReductionSurTypeProduit, String> colTypeProduit;
	@FXML private TableColumn<ReductionSurTypeProduit, String> colDateStart;
	@FXML private TableColumn<ReductionSurTypeProduit, String> colDateEnd;
	@FXML private TextField textTaux;
	@FXML private ComboBox<TypeProduit> comboTypeProduit;
	@FXML private DatePicker pickerDateStart;
	@FXML private DatePicker pickerDateEnd;
	@FXML private Label lblMessage;
	
	private ObservableList<ReductionSurTypeProduit> reductionObservableList = FXCollections.observableArrayList();	
	private ArrayList<ReductionSurTypeProduit> reductions = new ArrayList<ReductionSurTypeProduit>();
	private ReductionSurTypeProduitDao daoReduction = DaoFactory.getCurrentDao().getReductionSurTypeProduitDao();
	
	private ObservableList<TypeProduit> typeProduitObservableList = FXCollections.observableArrayList();
	private ArrayList<TypeProduit> typesProduit = new ArrayList<TypeProduit>();
	private TypeProduitDao daoTypeProduit = DaoFactory.getCurrentDao().getTypeProduitDao();
	
    // Permet d'annuler la sélection dans la TableView si aucun item n'est sélectionné
    private int oldSelectedIndex = -1;
    
	@FXML public void initialize() {
		System.out.println("contrôleur réduction sur type produit appelé");
		
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
    	this.colTypeProduit.setCellValueFactory(cellData -> cellData.getValue().getTypeProduit().libelleProperty());
    	this.colDateStart.setCellValueFactory(
			cellData -> {
				SimpleStringProperty property = new SimpleStringProperty();
				DateFormat dateFormat = new SimpleDateFormat("EEEEEEEE d MMMMMMMMM yyyy");
				property.setValue(dateFormat.format(cellData.getValue().getDateStart())); // .getTime() pour Date
				return property;
			});
    	this.colDateEnd.setCellValueFactory(
			cellData -> {
				SimpleStringProperty property = new SimpleStringProperty();
				DateFormat dateFormat = new SimpleDateFormat("EEEEEEEE d MMMMMMMMM yyyy");
				property.setValue(dateFormat.format(cellData.getValue().getDateEnd())); // .getTime() pour Date
				return property;
			});
    	
    	// Fait interagir la sélection sur le tableau pour afficher les détails du client
    	tableReduction.getSelectionModel().selectedItemProperty().addListener(
    		(observable, oldValue, newValue) ->  afficherDetailsReduction(newValue)
    	);
    	
		// Gère le clic de la souris
		this.tableReduction.setOnMouseClicked(
			(event) -> {
    			// On récupère l'index sélectionné par l'utilisateur
    	    	int selectedIndex = tableReduction.getSelectionModel().getSelectedIndex();

    	    	// Si l'utilisateur clique sur un item
    	    	if (oldSelectedIndex != selectedIndex) {
    	    		oldSelectedIndex = selectedIndex ;
    	    		tableReduction.getSelectionModel().select(selectedIndex);
    	    		event.consume();
    	    	}
    	    	// Si l'utilisateur ne clique sur aucun item
    	    	else {
    	    		oldSelectedIndex = -1;
    	    		tableReduction.getSelectionModel().clearSelection();
    	    		event.consume();
    	    	}
    		}
		);
    }
    
    @FXML private void updateTable() {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	this.reductionObservableList.clear();
    	
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
    		// Récupère la liste des types produit dans la persistence
    		this.typesProduit = this.daoTypeProduit.getAll();
    		
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
    
    private void afficherDetailsReduction (ReductionSurTypeProduit reduction) {
    	// Met à jour le label d'informations
    	updateLblMessage();
    	
    	if (reduction != null) {
    		// this.pickerDateStart.setValue(reduction.getDateStart());
    		// this.pickerDateEnd.setValue(reduction.getDateEnd());
    		// TODO combobox
    		this.textTaux.setText(Integer.toString(reduction.getTauxReduction()));
    	}
    	// Réduction null
    	else {
    		// TODO combobox
    		this.textTaux.setText("");
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
    		
    		else if (this.comboTypeProduit.getValue() == null)
    			throw new IllegalArgumentException("Veuillez choisir un type de produit");
    		
			LocalDate localDateStart = this.pickerDateStart.getValue();
			Date dateStart = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			LocalDate localDateEnd = this.pickerDateEnd.getValue();
			Date dateEnd = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
    		
    		ReductionSurTypeProduit reduction = new ReductionSurTypeProduit(
    			Integer.parseInt(this.textTaux.getText()),
    			this.comboTypeProduit.getValue().getIdType(),
    			dateStart,
    			dateEnd);
    		
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
	    		
	    		else if (this.comboTypeProduit.getValue() == null)
	    			throw new IllegalArgumentException("Veuillez choisir un type de produit");
	    		
				LocalDate localDateStart = this.pickerDateStart.getValue();
				Date dateStart = Date.from(localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				LocalDate localDateEnd = this.pickerDateEnd.getValue();
				Date dateEnd = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    		
	    		new ReductionSurTypeProduit(
	    			Integer.parseInt(this.textTaux.getText()),
	    			this.comboTypeProduit.getValue().getIdType(),
	    			dateStart,
	    			dateEnd
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
		            		this.reductionObservableList.get(selectedIndex).getTypeProduit().setIdType(this.comboTypeProduit.getValue().getIdType());;
		            		this.reductionObservableList.get(selectedIndex).setDateStart(dateStart);
		            		this.reductionObservableList.get(selectedIndex).setDateEnd(dateEnd);
		            		
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