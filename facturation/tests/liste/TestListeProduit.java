package tests.liste;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dao.liste.ListeProduitDao;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.Produit;

public class TestListeProduit {
	
	private ListeProduitDao listeProduit;
	private ArrayList <Produit> produits;
	
	@Before
	public void setUp() {
		this.listeProduit = ListeProduitDao.getInstance();
		this.produits = new ArrayList <Produit>();
		produits.add(new Produit (1, "produit1", 1, 1));
		produits.add(new Produit (2, "produit2", 1, 1));
		produits.add(new Produit (3, "produit3", 1, 1));
	}
	
	@Test
	public void instanceNotNull() {
		assertNotNull(this.listeProduit);
	}
	
	@Test
	public void createDoublon() {
		try {
			//this.listeProduit.create(produits.get(0));
			//fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			// 
		}
	}
	
	@Test
	public void createOK() {
		try {
			this.listeProduit.create(new Produit(4, "produit4", 1, 1));
		} catch (IllegalArgumentException iae) {
			fail("Exception non lancée !");
		} catch (ObjectAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void testGetByIdNotNull() {
		try {
			this.listeProduit.getById(1);
		} catch (IllegalArgumentException iae) {
			//fail("Exception non lancée !");
		} catch (ObjectNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Test
	public void testGetByIdChampsOK() {
        Produit p = produits.get(0);
        assertEquals(1, p.getIdProduit());
        assertEquals("produit1", p.getLibelle());
        assertEquals(1, p.getTypeProduit().getIdType());
        assertEquals(1, 0.001 , p.getPrix());
	}
	
	@Test
	public void getAllNotNull() {
		assertNotNull(this.listeProduit.getAll());
	}
	
	@Test
	public void deleteNotNull() {
		try {
			//this.listeProduit.delete(1);
			//fail("Exception non lancée !");
		} catch (IllegalArgumentException iae) {
			//
		}
	}	
}