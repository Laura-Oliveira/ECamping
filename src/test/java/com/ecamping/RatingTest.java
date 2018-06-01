/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author laura
 */
public class RatingTest {
    
    public RatingTest() {
    }
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        //logger.setLevel(Level.INFO);
        logger.setLevel(Level.SEVERE);
        emf = Persistence.createEntityManagerFactory("ecampingPersistence");
        //DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        em.close();
    }
    
    private void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }

    private void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            et.rollback();
            fail(ex.getMessage());
        }
    }
    
    //TEST CLASSES
    @Test
    public void addRating(){
//        User user1 = new User();
//        user1.setName("Joselito Cunha");
//        user1.setCpf("683.075.154-45");
//        user1.setEmail("josecunha@gmail.com");
//        user1.setPassword("joselito$lito$");
//        
//        User user2 = new User();
//        user2.setName("Aura Magda");
//        user2.setCpf("888.125.104-85");
//        user2.setEmail("aura@gmail.com");
//        user2.setPassword("magdaLit@");
//        
//        User user3 = new User();
//        user2.setName("Joana Darc");
//        user2.setCpf("864.134.104-25");
//        user2.setEmail("jdarc@gmail.com");
//        user2.setPassword("guerr@%gue@@");
//        
//        Address endereco = new Address();
//        endereco.setCidade("Bonito");
//        endereco.setEstado("Pernambuco");
//        endereco.setBairro("Bonito");
//        endereco.setCep("54061-170");
//        endereco.setNumero("67");
//        endereco.setRua("Rua do Lago");
//        
//        Camping camping = new Camping();
//        camping.setInfo("O local possui área de trilhas e cachoeiras para os campistas desfrutarem.");
//        camping.setName("Camping do Luizão");
//        camping.setPhone("81 3333-3333");
//        camping.setAddress(endereco);
        User user = em.find(User.class, (long)1);
        Camping camping = em.find(Camping.class, (long)1);
        
        Rating rating1 = new Rating();
        rating1.setValue(2);
        rating1.setUser(user);
        rating1.setCamping(camping);
        rating1.setPublish(Calendar.getInstance().getTime());
        rating1.setUpdate(Calendar.getInstance().getTime());
        
        Rating rating2 = new Rating();
        rating2.setValue(2);
        rating2.setUser(user);
        rating2.setCamping(camping);
        rating2.setPublish(Calendar.getInstance().getTime());
        rating2.setUpdate(Calendar.getInstance().getTime());
        
        Rating rating3 = new Rating();
        rating3.setValue(4);
        rating3.setUser(user);
        rating3.setCamping(camping);
        rating3.setPublish(Calendar.getInstance().getTime());
        rating3.setUpdate(Calendar.getInstance().getTime());
        
        camping.addRating(rating1);
        camping.addRating(rating2);
        camping.addRating(rating3);
        
        em.flush();
        assertNotNull(camping.getRating());
        
//        Rating rating2 = new Rating();
//        rating2.setValue(2);
//        rating2.setUser(user2);
//        rating2.setCamping(camping);
//        
//        Rating rating3 = new Rating();
//        rating3.setValue(5);
//        rating3.setUser(user3);
//        rating3.setCamping(camping);
//        
//        em.merge(camping);
//        Camping campingNew = new Camping();
//        camping.addRating(rating1);
//        camping.addRating(rating2);
//        camping.addRating(rating3);

        
        
        
    }
    
}
