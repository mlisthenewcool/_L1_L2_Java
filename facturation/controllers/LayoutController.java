/**
 * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Contrôleur pour le conteneur principal
 * Gère le menu de navigation
 * * * * * * * * * * * * * * * * * * * * *
 */

package controllers;

import java.io.IOException;

import app.ViewNavigator;
import dao.factory.DaoFactory;
import dao.factory.ListeDaoFactory;
import dao.factory.MySqlDaoFactory;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class LayoutController {
	@FXML private StackPane conteneur;
	
	@FXML
	public void initialize() {
		//DaoFactory.setCurrentDao(new ListeDaoFactory());
		DaoFactory.setCurrentDao(new MySqlDaoFactory());
	}
	
	@FXML
	private void setDaoMySql() {
		DaoFactory.setCurrentDao(new MySqlDaoFactory());
		// REFRESH POUR LE DAO
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_FACTURATION);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Vous avez choisi le Dao MySql");
	}
	
	@FXML
	private void setDaoListeMemoire() {
		DaoFactory.setCurrentDao(new ListeDaoFactory());
		// REFRESH POUR LE DAO
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_FACTURATION);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Vous avez choisi le Dao liste mémoire");
	}
	
	public void setView(Node vue) {
		conteneur.getChildren().setAll(vue);
	}
	
	@FXML
	public void goToFacturationView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_FACTURATION);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToClientView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_CLIENT);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToProduitView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_PRODUIT);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToTypeView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_TYPE);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToTVAView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_TVA);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToFactureView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_FACTURE);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToReductionSurProduitView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_REDUCTION_PRODUIT);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToReductionSurTypeProduitView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_REDUCTION_TYPE_PRODUIT);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@FXML
	public void goToReductionSurTotalView() {
		try {
			ViewNavigator.loadView(ViewNavigator.PATH_REDUCTION_TOTAL);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}