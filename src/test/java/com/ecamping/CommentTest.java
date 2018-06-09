/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ramon
 */
public class CommentTest {
    
    public CommentTest() {
    }
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        emf = Persistence.createEntityManagerFactory("ecampingPersistence");
        DbUnitUtil.inserirDados();
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
            if (et.isActive()) {
                et.rollback();
            }  
        }
    }
    
    
    @Test
    public void addRating(){
       
        User user = em.find(User.class, 1L);
        Camping camping = new Camping();
        camping.setName("Camping 12");
        camping.setInfo("informacaonçao muito relevante mas relevante");
        camping.setPhone("(21) 99322-1973");

        Rating rating1 = new Rating();
        rating1.setValue(2);
        rating1.setUser(user);
        rating1.setCamping(camping);
        rating1.setPublish(Calendar.getInstance().getTime());
        rating1.setUpdate(Calendar.getInstance().getTime());
        try{

            em.persist(rating1);
            em.flush();
            assertNotNull(rating1);
            
        } catch(ConstraintViolationException ex){
        
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            
            
            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}\n\n", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }
            assertEquals(1, constraintViolations.size());
        }
    }
    
    @Test
    public void updateComment(){
        

        TypedQuery<Comment> q = em.createQuery("UPDATE Comment c SET c.message = :message WHERE c.message = :text", Comment.class);
        q.setParameter("message", "Nossa o camping é muito da hora!");
        q.setParameter("text", "Mensagem de Joana Darc sobre camping 1");
        q.executeUpdate();
        
        assertEquals("Nossa o camping é muito da hora!", q.getParameterValue("message"));
        
    }
    
    @Test
    public void ratingList(){
        
        Query comments = em.createNamedQuery("Comment.AllComment", Comment.class);
        List<Comment> list = comments.getResultList();
        assertEquals(8, list.size());
    }
    
    @Test
    public void commentBetweenTwoDates(){ 
        TypedQuery<Rating> q = em.createQuery("SELECT c FROM Comment c WHERE c.publish BETWEEN '2015-03-19' AND '2017-03-02'", Rating.class);
        List<Rating> resultado = q.getResultList();
        assertEquals(4, resultado.size());
    }
    

    
}
