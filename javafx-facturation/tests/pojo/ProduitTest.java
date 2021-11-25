/*
 * @author MagicBanana
 */

package tests.pojo;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import pojo.Produit;

public class ProduitTest {
	
	private Produit p;
	
	@Before
	public void setUp() throws Exception {
		this.p = new Produit(1, "produit1", 1, 1);
	}

	@Test
	public void testSetIdProduitOK() {
		try {
			this.p.setIdProduit(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdProduitNull() {
		try {
			this.p.setIdProduit(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetLibelleOK() {
		try {
			this.p.setLibelle("produit1");
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetLibelleVide() {
		try {
			this.p.setLibelle("    ");
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetIdTypeOK() {
		try {
			this.p.setIdType(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdTypeNull() {
		try {
			this.p.setIdType(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetPrixOK() {
		try {
			this.p.setPrix(10);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetPrixNull() {
		try {
			this.p.setPrix(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
}
