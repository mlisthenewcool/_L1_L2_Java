/**
 * * * * * * * * * * * * * * * * * * * * * * * *
 * @author jewelsea
 * @see https://gist.github.com/jewelsea/6460130
 * 
 * Framework très léger pour charger différentes
 * vues avec leurs propres contrôleurs au sein
 * d'un contrôleur unique.
 * 
 * Ici on l'utilise pour réutiliser le menu de
 * navigation.
 * * * * * * * * * * * * * * * * * * * * * * * *
 */

package app;

import java.io.IOException;

import controllers.LayoutController;
import javafx.fxml.FXMLLoader;

public class ViewNavigator {
    public static final String PATH_STYLE = "/style/main.css";
    public static final String PATH_LAYOUT = "/views/Layout.fxml";
    public static final String PATH_FACTURATION = "/views/Facturation.fxml";
    public static final String PATH_CLIENT = "/views/Client.fxml";
    public static final String PATH_PRODUIT = "/views/Produit.fxml";
    public static final String PATH_TYPE = "/views/TypeProduit.fxml";
    public static final String PATH_TVA = "/views/TVA.fxml";
    public static final String PATH_FACTURE = "/views/Facture.fxml";
    public static final String PATH_REDUCTION_PRODUIT = "/views/reduction/ReductionSurProduit.fxml";
    public static final String PATH_REDUCTION_TYPE_PRODUIT = "/views/reduction/ReductionSurTypeProduit.fxml";
    public static final String PATH_REDUCTION_TOTAL = "/views/reduction/ReductionSurTotal.fxml";
    
    private static LayoutController layoutController;
    
    public static void setLayoutController(LayoutController layoutController) {
    	ViewNavigator.layoutController = layoutController;
    }
    
    /**
     * * * * * * * * * * * * * * * * * * * * * *
     * @param fxml : Le fichier fxml à charger
     * @throws IOException
     * 
     * Charge la vue au sein du layoutController
     * * * * * * * * * * * * * * * * * * * * * *
     */
    public static void loadView(String fxml) throws IOException {
    	layoutController.setView(
    		FXMLLoader.load(
    			ViewNavigator.class.getResource(fxml)));
    	
    	System.out.println("Page " + fxml + " chargée");
    }
}