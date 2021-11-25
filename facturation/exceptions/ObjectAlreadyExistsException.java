/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package exceptions;

public class ObjectAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -5971844323824768728L;

	public ObjectAlreadyExistsException(String message) {
		super(message);
	}
}