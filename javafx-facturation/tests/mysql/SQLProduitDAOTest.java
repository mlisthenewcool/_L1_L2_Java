package tests.mysql;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import dao.mysql.MySqlProduitDao;
import pojo.Produit;

public class SQLProduitDAOTest {
	
	private MySqlProduitDao mySqlProduit;
	
	@Before
	public void setUp() {
		this.mySqlProduit = new MySqlProduitDao();
	}
	
	@Test
	public void instanceNotNull() {
		assertNotNull(this.mySqlProduit);
	}
	
	@Test
    public void testGetByIdNotNull() {
		//Ce test fail si on ne change pas le nom du produit... C EST NORMAL
		try {
			assertNotNull(this.mySqlProduit.getById(this.mySqlProduit.create(new Produit(1, "produit", 1, 1))));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Test
	public void testGetByIdChampsOK() {
        /*
         * Pour pousser le test plus loin et vérifier l'exactitude des champs
         */
		//si la bdd est vide, décommenter la ligne suivante
		//this.mySqlProduit.create(new Produit(1, "produit1000", 1, 1));
		
		try {
			Produit p = this.mySqlProduit.getById(1);
	        assertEquals(1, p.getIdProduit());
	        assertEquals("produit1", p.getLibelle());
	        assertEquals(1, p.getIdType());
	        assertEquals(1, 0.001 , p.getPrix());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllNotNull() {
		try {
			assertNotNull(this.mySqlProduit.getAll());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteNotNull() {
		try {
			this.mySqlProduit.delete(this.mySqlProduit.create(new Produit(1, "produit", 1, 1)));
			fail("Exception non lancée !");
		} catch (IllegalArgumentException | SQLException iae) {
			//
		}
	}
	
	@Test
	public void create() {
		try {
			this.mySqlProduit.create(new Produit(1, "produit", 1, 1));
		} catch (IllegalArgumentException | SQLException iae) {
			//
		}
	}
}
