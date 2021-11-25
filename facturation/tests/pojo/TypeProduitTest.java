/*
 * @author MagicBanana
 */

package tests.pojo;

import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import pojo.TypeProduit;

public class TypeProduitTest {
	
	private TypeProduit type;
	
	@Before
	public void setUp() throws Exception {
		this.type = new TypeProduit(1, "typeProduit1", 1);
	}

	@Test
	public void testSetIdTypeProduitOK() {
		try {
			this.type.setIdType(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdTypeNull() {
		try {
			this.type.setIdType(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetTypeOK() {
		try {
			this.type.setLibelle("typeProduit1");
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetTypeVide() {
		try {
			this.type.setLibelle("    ");
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
	
	@Test
	public void testSetIdTvaOK() {
		try {
			this.type.getTVA().setIdTva(1);
		} catch (IllegalArgumentException iae) {
			fail("Exception lancée par erreur !");
		}
	}
	@Test
	public void testSetIdTvaNull() {
		try {
			this.type.getTVA().setIdTva(0);
			fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//rien à faire
		}
	}
}
