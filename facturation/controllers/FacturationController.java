/**
 * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Contrôleur de la page de facturation
 * * * * * * * * * * * * * * * * * * * *
 */

package controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import dao.factory.DaoFactory;
import dao.pojo.ClientDao;
import dao.pojo.FactureDao;
import dao.pojo.LigneFactureDao;
import dao.pojo.ProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import pojo.Client;
import pojo.Facture;
import pojo.Produit;

public class FacturationController {
	
	@FXML private TableView <Entry<Produit, Integer>> tableLignes;
	@FXML private TableColumn <Map.Entry<Produit, Integer>, String> colProduit;
	@FXML private TableColumn <Map.Entry<Produit, Integer>, String> colQuantite;
	@FXML private ComboBox <Client> comboClient;
	@FXML private DatePicker datePicker;
	@FXML private ComboBox <Produit> comboProduit;
	@FXML private TextField textQuantite;
	@FXML private Label lblMessage;
	@FXML private Label lblTotalHT;
	@FXML private Label lblTotalTTC;
		
	private Facture facture = new Facture();
	private FactureDao daoFacture = DaoFactory.getCurrentDao().getFactureDao();
	
	private ObservableList<Map.Entry<Produit, Integer>> lignesObservableList = FXCollections.observableArrayList();
	private LigneFactureDao daoLigne = new LigneFactureDao();
	
	private ObservableList<Client> clientObservableList = FXCollections.observableArrayList();
	private ArrayList<Client> clients = new ArrayList<Client>();;
	private ClientDao daoClient = DaoFactory.getCurrentDao().getClientDao();
	
	private ObservableList<Produit> produitObservableList = FXCollections.observableArrayList();
	private ArrayList<Produit> produits = new ArrayList<Produit>();
	private ProduitDao daoProduit = DaoFactory.getCurrentDao().getProduitDao();
	
	private Boolean reductionAppliquee = false;
	
	@FXML public void initialize() {		
		System.out.println("contrôleur accueil appelé");
		
		this.datePicker.setValue(LocalDate.now());
		this.facture.setDateFacture(new Date());
		
		setupTable();
		setupComboBoxClient();
		setupComboBoxProduit();
	}
	
	private void setupTable() {		
        this.colProduit.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Produit, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Produit, Integer>, String> p) {
                return p.getValue().getKey().libelleProperty();
            }
        });
        
        this.colQuantite.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Produit, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Produit, Integer>, String> p) {
            	SimpleStringProperty test = new SimpleStringProperty();
            	test.set(p.getValue().getValue().toString());
            	return test;
            }
        });
	}
	
	private void setupComboBoxClient() {
		try {
			// Récupère la liste des clients en bdd
			this.clients = daoClient.getAll();
			
			// Ajoute la liste bdd à la liste observable
			this.clientObservableList.addAll(this.clients);
			
			// Fixe le comboBox avec la liste des clients
			this.comboClient.setItems(this.clientObservableList);
			
    	} catch (DataAccessException dae) {
    		this.lblMessage.setTextFill(Color.RED);
    		this.lblMessage.setText(dae.getMessage());
		}
	}
	
	private void setupComboBoxProduit() {
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
	
	@FXML private void ajouterLigne() {
		this.lignesObservableList.clear();
		
		try {
			if (this.textQuantite.getText() == null)
				throw new IllegalArgumentException("Veuillez choisir une quantité");
			if (this.comboProduit.getValue() == null)
				throw new IllegalArgumentException("Veuillez choisir un produit");
			
			this.daoLigne.ajouterLigne(facture, this.comboProduit.getValue(), Integer.parseInt(this.textQuantite.getText()));
			
			this.lblTotalHT.setText(Double.toString(this.facture.getTotalHT()));
			this.lblTotalTTC.setText(Double.toString(this.facture.getTotalTTC()));
			
			this.lignesObservableList.addAll(facture.getLignesFacture().entrySet());
			
			// On ajoute le produit et la quantité à la TableView
			this.tableLignes.setItems(this.lignesObservableList);
			
			this.reductionAppliquee = false;
			
			// Message de succès
			this.lblMessage.setTextFill(Color.GREEN);
			this.lblMessage.setText(this.comboProduit.getValue() + " ajouté " + Integer.parseInt(this.textQuantite.getText())  + " fois");
			
		// Si les saisies sont incorrectes
		} catch (IllegalArgumentException iae) {
			this.lblMessage.setTextFill(Color.RED);
			this.lblMessage.setText(iae.getMessage());
		}
	}
	
	@FXML private void modifierLigne() {
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = this.tableLignes.getSelectionModel().getSelectedIndex();
    	
    	try {   	
			// Si un item a été sélectionné
			if (selectedIndex >= 0) {
				if (this.textQuantite.getText() == null)
					throw new IllegalArgumentException("Veuillez choisir une quantité");
				
        		this.lignesObservableList.get(selectedIndex).getKey().setLibelle(this.comboProduit.getValue().getLibelle());
	    		this.lignesObservableList.get(selectedIndex).setValue(Integer.parseInt(this.textQuantite.getText()));
				
				this.daoLigne.modifierLigne(facture, this.comboProduit.getValue(), Integer.parseInt(this.textQuantite.getText()));
				
				this.lblTotalHT.setText(Double.toString(this.facture.getTotalHT()));
				this.lblTotalTTC.setText(Double.toString(this.facture.getTotalTTC()));
				
				// On ajoute le produit et la quantité à la TableView
				this.tableLignes.getItems().set(selectedIndex, this.lignesObservableList.get(selectedIndex));
				
				this.reductionAppliquee = false;
				
				// Message de succès
				this.lblMessage.setTextFill(Color.GREEN);
				this.lblMessage.setText(this.comboProduit.getValue() + " modifié, valeur actuelle : " + Integer.parseInt(this.textQuantite.getText())  + " fois");
			}
    	} catch (IllegalArgumentException iae) {
    		this.lblMessage.setTextFill(Color.RED);
			this.lblMessage.setText(iae.getMessage());
    	}
    }
	
	@FXML private void supprimerLigne() {		
    	// On récupère l'index sélectionné par l'utilisateur
    	int selectedIndex = this.tableLignes.getSelectionModel().getSelectedIndex();
    	  		
		// Si un item a été sélectionné
		if (selectedIndex >= 0) {
			try {
			this.daoLigne.supprimerLigne(facture, this.lignesObservableList.get(selectedIndex).getKey());
			
			this.lblTotalHT.setText(Double.toString(this.facture.getTotalHT()));
			this.lblTotalTTC.setText(Double.toString(this.facture.getTotalTTC()));
			
			// On met à jour la TableView
			this.tableLignes.getItems().remove(selectedIndex);
			
			// On actualise la table
			this.tableLignes.setItems(this.lignesObservableList);
			
			// Message de succès
			this.lblMessage.setTextFill(Color.GREEN);
			this.lblMessage.setText(this.comboProduit.getValue() + " supprimé ");
			
			// Si les saisies sont incorrectes
			} catch (IllegalArgumentException iae) {
				this.lblMessage.setTextFill(Color.RED);
				this.lblMessage.setText(iae.getMessage());
			}
		}
	}
	
	@FXML
	private void appliquerReductions() {
		
		if (this.reductionAppliquee == false) {
			try {
				daoFacture.rechercherReduction(this.facture);
				this.lblTotalHT.setText(Double.toString(this.facture.getTotalHT()));
				this.lblTotalTTC.setText(Double.toString(this.facture.getTotalTTC()));
				
				this.reductionAppliquee = true;
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@FXML private void ajouterFacture() {		
		// On récupère les saisies pour créer une facture
		try {
			if (this.comboClient.getValue() == null)
				throw new IllegalArgumentException ("Veuillez choisir un client");
			if (this.facture.getLignesFacture().size() == 0)
				throw new IllegalArgumentException ("Veuillez remplir des lignes de facture");
			
			this.facture.getClient().setIdClient(this.comboClient.getValue().getIdClient());
			
			LocalDate localDate = datePicker.getValue();
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			this.facture.setDateFacture(date);
			
			/*
			this.datePicker.setOnAction(
				event -> {
					LocalDate localDate = datePicker.getValue();
					Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
					this.facture.setDateFacture(date);
				}
			);
			if (this.facture.getDateFacture() == null)
				this.facture.setDateFacture(new Date());
			*/
			
			// Ouvre une fenêtre d'alerte pour confirmer
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setContentText(
				"Ajouter la facture du client " +
				comboClient.getValue() + 
				" pour un montant total de " +
				facture.getTotalTTC() + " € ?");
			
            // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
            alert.showAndWait().ifPresent(response -> {
            	if (response == ButtonType.OK) {
                	try {                		
                		// On insère la facture dans la base de données si les saisies sont correctes
            			int idFacture = daoFacture.create(this.facture);
            			facture.setIdFacture(idFacture);
            			
            			// Message de succès
            			this.lblMessage.setTextFill(Color.GREEN);
            			this.lblMessage.setText("Facture ajoutée");
            			
        	    	} catch (ObjectAlreadyExistsException oaee) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(oaee.getMessage());
        	    		
        	    	} catch (DataAccessException dae) {
        	    		this.lblMessage.setTextFill(Color.RED);
        	    		this.lblMessage.setText(dae.getMessage());
        			}
            	}
            });
		// Si les saisies sont incorrectes
		} catch (IllegalArgumentException iae) {
			this.lblMessage.setTextFill(Color.RED);
			this.lblMessage.setText(iae.getMessage());
		}
	}
}