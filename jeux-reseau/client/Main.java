package client;

import vueSwing.VuePrincipaleClient;
import controleurClient.ControleurVuePrincipaleClient;

public class Main {

	public static void main(String[] args) {
		
		ControleurVuePrincipaleClient ctrlVP = new ControleurVuePrincipaleClient();
		ctrlVP.setVue(new VuePrincipaleClient(ctrlVP));
		
	}

}
