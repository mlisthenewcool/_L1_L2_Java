/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package exceptions;

public class ObjectNotExistsException extends Exception {

	private static final long serialVersionUID = -8892694985034627447L;

	public ObjectNotExistsException(String message) {
		super(message);
	}
	
	public ObjectNotExistsException() {
		super("L'objet n'existe pas");
	}
}
