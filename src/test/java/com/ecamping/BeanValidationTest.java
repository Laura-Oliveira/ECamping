/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author ramon
 */
public class BeanValidationTest {
    
    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public BeanValidationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        //logger.setLevel(Level.INFO);
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
            //logger.log(Level.SEVERE, ex.getMessage(), ex);
            if (et.isActive()) {
                et.rollback();
            }
//            fail(ex.getMessage());    
        }
    }

    
    @Test
    public void newBooking(){
        
        Booking booking = null;
        Calendar calendar = new GregorianCalendar();
                
        try{
            
            User user = em.find(User.class, 1L);
            Camping camping = em.find(Camping.class, 3L);
            
            calendar.set(2010, Calendar.FEBRUARY, 23);
            
            booking = new Booking();
            booking.setTent(""); 
            booking.setUser(user);
            booking.setCamping(camping);
            booking.setBookingDate(calendar.getTime());
            
            em.persist(booking); 
            em.flush();
            assertTrue(false); 
            
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            
            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}\n\n", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(2, constraintViolations.size());
            
        }
    }
    
    @Test
    public void newUser(){
        
        User user = null;
        Calendar calendar = new GregorianCalendar();
                
        try{
            
            user = new User();
            user.setName(" ");
            user.setCpf("111.111.11-11"); 
            user.setEmail("AlexandreMagno.as.com");
            user.setPassword("ssdf@sf55D");
                    
            em.persist(user); 
            em.flush();
            assertTrue(false); 
            
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            
            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}\n\n", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(3, constraintViolations.size());
            
        }
    }
    
    @Test
    public void sizeOfCamping(){
        
        Camping camping = null;
        Calendar calendar = new GregorianCalendar();
                
        try{
            Address address = new Address();
            address.setRua("Vogar");
            address.setBairro("Moria");
            address.setCidade("Lothriem");
            address.setNumero("12");
            address.setEstado("Middle-Earth");
            address.setCep("222-323");
            
            camping = new Camping();
            camping.setName("sdf");
            camping.setPhone("8134353523252254235");
            camping.setInfo(" ");
            camping.setAddress(address);
            
            em.persist(camping); 
            em.flush();
            assertTrue(false); 
            
        } catch (ConstraintViolationException ex) {

            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            if (logger.isLoggable(Level.INFO)) {
                for (ConstraintViolation violation : constraintViolations) {
                    Logger.getGlobal().log(Level.INFO, "{0}.{1}: {2}\n\n", new Object[]{violation.getRootBeanClass(), violation.getPropertyPath(), violation.getMessage()});
                }
            }

            assertEquals(3, constraintViolations.size());
            
        }
        
    }
    
}
