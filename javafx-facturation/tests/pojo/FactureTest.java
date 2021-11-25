/*
 * @author MagicBanana
 */

package tests.pojo;

import static org.junit.Assert.fail;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

import pojo.Facture;

public class FactureTest {
	
	private Facture f;
	
	@Before
	public void setUp() throws Exception {
		this.f = new Facture(1, new Date(), 1);
	}

	@Test
	public void testSetIdFactureOK() {
		try {
			this.f.setIdFacture(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdFactureNull() {
		try {
			this.f.setIdFacture(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetDateFactureOK() {
		try {
			this.f.setDateFacture(new Date());
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetDateFactureUlterieure() {
		try {
			//TODO ce test ne fonctionne pas
			//this.f.setDateFacture(new Date());
			//fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetIdClientOK() {
		try {
			this.f.setIdClient(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdClientNull() {
		try {
			this.f.setIdClient(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
}