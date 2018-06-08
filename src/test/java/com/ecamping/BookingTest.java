/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
public class BookingTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public BookingTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);
        logger.setLevel(Level.SEVERE);
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
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }
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

    @Test
    public void createBooking() {
        Booking booking = null;
        Calendar calendar = new GregorianCalendar();

        try {
            //Pega um usuário existente no banco
            User user = em.find(User.class, 1L);
            //Pega um camping existente no banco
            Camping camping = em.find(Camping.class, 3L);

            calendar.set(2010, Calendar.FEBRUARY, 23); //Data no passado

            booking = new Booking();
            booking.setTent(""); //Forma inválida, deve ser INDIVIDUAL ou duplex
            booking.setUser(user);
            booking.setCamping(camping);
            booking.setBookingDate(calendar.getTime());

            em.persist(booking); //insere o novo booking no banco
            em.flush();
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println("Erro no cadatro de booking " + ex);
        }
    }
    
    @Test
    public void createBooking02() {
        Booking booking = null;
        Calendar calendar = new GregorianCalendar();

        try {
            //Pega um usuário existente no banco
            User user = em.find(User.class, 1L);
            //Pega um camping existente no banco
            Camping camping = em.find(Camping.class, 3L);

            calendar.set(2010, Calendar.FEBRUARY, 23); //Data no passado

            booking = new Booking();
            booking.setTent(""); //Forma inválida, deve ser INDIVIDUAL ou duplex
            booking.setUser(user);
            booking.setCamping(camping);
            booking.setBookingDate(calendar.getTime());

            em.persist(booking); //insere o novo booking no banco
            em.flush();
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println("Erro no cadatro de booking " + ex);
        }
    }
    
    @Test
    public void createBooking03() {
        Booking booking = null;
        Calendar calendar = new GregorianCalendar();

        try {
            //Pega um usuário existente no banco
            User user = em.find(User.class, 1L);
            //Pega um camping existente no banco
            Camping camping = em.find(Camping.class, 3L);

            calendar.set(2010, Calendar.FEBRUARY, 23); //Data no passado

            booking = new Booking();
            booking.setTent(""); //Forma inválida, deve ser INDIVIDUAL ou duplex
            booking.setUser(user);
            booking.setCamping(camping);
            booking.setBookingDate(calendar.getTime());

            em.persist(booking); //insere o novo booking no banco
            em.flush();
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println("Erro no cadatro de booking " + ex);
        }
    }


    private Date getData(Integer dia, Integer mes, Integer ano) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        return c.getTime();
    }

    @Test
    public void bookingPorData() {
        TypedQuery<Booking> query = em.createQuery(
                "SELECT b FROM Booking  b WHERE b.bookingDate BETWEEN ?1 AND ?2",
                Booking.class);
        query.setParameter(1, getData(15, Calendar.FEBRUARY, 2018));
        query.setParameter(2, getData(20, Calendar.DECEMBER, 2018));
        List<Booking> reservas = query.getResultList();

        if (logger.isLoggable(Level.INFO)) {
            for (Booking reserva : reservas) {
                logger.info(reserva.toString());
            }
        }
        assertEquals(6, reservas.size());
    }

    @Test
    public void bookingPorUsuario() {
        logger.info("Executando SELECT b FROM Booking b WHERE b.user.name LIKE ?1 ");
        TypedQuery<Booking> query = em.createQuery(
                "SELECT b FROM Booking b WHERE b.user.name LIKE ?1 ", Booking.class);
        query.setParameter(1, "Fulano");
        List<Booking> reservas = query.getResultList();

        if (logger.isLoggable(Level.INFO)) {
            for (Booking reserva : reservas) {
                logger.info(reserva.toString());
            }
        }
        assertEquals(1, reservas.size());
    }

    @Test
    public void bookingPorCamping() {
        TypedQuery<Booking> query = em.createQuery(
                "SELECT b FROM Booking b WHERE b.camping.name LIKE ?1 ", Booking.class);
        query.setParameter(1, "Camping 01");
        List<Booking> reservas = query.getResultList();
        if (logger.isLoggable(Level.INFO)) {
            for (Booking reserva : reservas) {
                logger.info(reserva.toString());
            }
        }
        assertEquals(2, reservas.size());
    }

    @Test
    public void NQCampingPorUser() {
        TypedQuery<Booking> q = em.createNamedQuery("Booking.PorUser", Booking.class);
        q.setParameter(1, "Fulano");
        List<Booking> b = q.getResultList();
        assertEquals(1, b.size());
        //fazer teste comparando as datas
    }
/*
    @Test
    public void NQCampingReservaUnica() {
        Query query = em.createNamedQuery("Booking.PorUser", Booking.class);
        query.setParameter(1, "155.456.871-45");
        query.setParameter(1, "Camping 01");
        List<Booking> booking = query.getResultList();

        Calendar calendar = new GregorianCalendar();
        calendar = (GregorianCalendar) GregorianCalendar.getInstance();
        calendar.set(GregorianCalendar.YEAR, 2018);
        calendar.set(GregorianCalendar.MONTH, Calendar.APRIL);
        calendar.set(GregorianCalendar.DAY_OF_MONTH, 10);
        //calendar.setBookingDate(calendar.getTime());

        assertNotNull(booking);

        assertEquals(calendar.getTime(), booking);
    }
*/
    @Test
    public void NativeCampingOrdemData() {
        Query q = em.createNativeQuery("SELECT DT_BOOKINGDATE FROM tb_booking ORDER BY DT_BOOKINGDATE");
        List lista = q.getResultList();
        // fazer teste

    }

    //testes de update e delete
}
