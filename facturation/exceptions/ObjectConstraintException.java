/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package exceptions;

public class ObjectConstraintException extends Exception {
	
	private static final long serialVersionUID = 4850898607805348793L;

	public ObjectConstraintException(String message) {
		super(message);
	}
	
	public ObjectConstraintException() {
		super("Impossible de supprimer un objet avec des dépendances");
	}
}