/*
 * @author MagicBanana
 */

package classesObjets;

public class Client {

	private String nom;
	private String prenom;
	
	/*
	 * Constructeur
	 */	
	public Client (String nom, String prenom) {
		this.setNom(nom);
		this.setPrenom(prenom);
	}
	
	/*
	 * Méthodes
	 */
	public void afficher() {
		System.out.println(
			"Nom : " +this.getNom() +
			"\nPrénom : " +this.getPrenom()
		);
	}
	
	/*
	 * Setters	
	 */
	public void setNom(String nom) {	
		//Génère une exception si le nom est vide
		if (nom == null || nom.trim().length() == 0) {
			throw new IllegalArgumentException("Nom vide");
		}
		
		//Génère une exception si le nom est inférieur à 2 caractères
		if (nom.trim().length() < 2) {
			throw new IllegalArgumentException("Le nom doit être composé d'au moins 2 caractères");
		}
		
		//Génère une exception si le nom contient des chiffres
		byte[] bytes = nom.trim().getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (!Character.isWhitespace((char) bytes[i])) {
				if (Character.isDigit((char) bytes[i])) {
					throw new IllegalArgumentException("Le nom contient des chiffres");
				}
			}
		}
		
		this.nom = nom.trim();
	}
	
	public void setPrenom(String prenom) {
		//Génère une exception si le prénom est vide
		if (prenom == null || prenom.trim().length() == 0) {
			throw new IllegalArgumentException("Prénom vide");
		}
		
		//Génère une exception si le prénom est inférieur à 2 caractères
		if (prenom.trim().length() < 2) {
			throw new IllegalArgumentException("Le prénom doit être composé d'au moins 2 caractères");
		}
		
		//Génère une exception si le prénom contient des chiffres
		byte[] bytes = prenom.trim().getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (!Character.isWhitespace((char) bytes[i])) {
				if (Character.isDigit((char) bytes[i])) {
					throw new IllegalArgumentException("Le prénom contient des chiffres");
				}
			}
		}
		
		this.prenom = prenom.trim();
	}
	
	/*
	 * Getters
	 */
	public String getNom() {
		return this.nom;
	}
	
	public String getPrenom() {
		return this.prenom;
	}
}
