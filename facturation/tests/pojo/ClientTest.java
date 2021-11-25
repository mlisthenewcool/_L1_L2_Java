/*
 * @author MagicBanana
 */

package tests.pojo;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import pojo.Client;

public class ClientTest {
	
	private Client c;
	
	@Before
	public void setUp() throws Exception {
		this.c = new Client(1, "nom", "prenom");
	}

	@Test
	public void testSetIdClientOK() {
		try {
			this.c.setIdClient(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdClientNull() {
		try {
			this.c.setIdClient(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetNomOK() {
		try {
			this.c.setNom("nom");
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetNomVide() {
		try {
			this.c.setNom("    ");
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetPrenomOK() {
		try {
			this.c.setPrenom("prenom");
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetPrenomNull() {
		try {
			this.c.setPrenom("    ");
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
}
