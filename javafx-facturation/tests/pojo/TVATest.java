/*
 * @author MagicBanana
 */

package tests.pojo;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import pojo.TVA;

public class TVATest {
	
	private TVA tva;
	
	@Before
	public void setUp() throws Exception {
		this.tva= new TVA(1, "tva1", 5.5);
	}

	@Test
	public void testSetIdTvaOK() {
		try {
			this.tva.setIdTva(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdTvaNull() {
		try {
			this.tva.setIdTva(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetLibelleOK() {
		try {
			this.tva.setLibelle("typeProduit1");
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetLibelleVide() {
		try {
			this.tva.setLibelle("    ");
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetTauxOK() {
		try {
			this.tva.setTaux(5.5);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetTauxNull() {
		try {
			this.tva.setTaux(0);
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
}
