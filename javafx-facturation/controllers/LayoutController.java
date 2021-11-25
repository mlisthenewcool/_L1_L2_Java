package controllers;

import app.Bootloader;
import javafx.fxml.FXML;

public class LayoutController implements ControlledScreen {
	
	private ScreensController myController;
	
    public void initialize() {
    	
    }
    
    @FXML private void goToClientView() {
    	myController.setScreen(Bootloader.CLIENT_ID);
    }

	@Override
	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
}
