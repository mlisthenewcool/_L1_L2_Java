package tests.mysql;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.mysql.MySqlProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
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
		} catch (DataAccessException | ObjectAlreadyExistsException e) {
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
	        assertEquals(1, p.getTypeProduit().getIdType());
	        assertEquals(1, 0.001 , p.getPrix());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAllNotNull() {
		try {
			assertNotNull(this.mySqlProduit.getAll());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteNotNull() {
		try {
			this.mySqlProduit.delete(this.mySqlProduit.create(new Produit(1, "produit", 1, 1)));
			fail("Exception non lancée !");
		} catch (DataAccessException | ObjectConstraintException oce) {
			//
		} catch (ObjectAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void create() {
		try {
			this.mySqlProduit.create(new Produit(1, "produit", 1, 1));
		} catch (DataAccessException | ObjectAlreadyExistsException e) {
			//
		}
	}
}