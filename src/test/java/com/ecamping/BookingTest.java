/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.util.Calendar;
import java.util.Date;
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

    @Test
    public void createBooking() {
        User user = new User();
        user.setName("Fulano");
        user.setCpf("155.456.871-45");
        user.setEmail("fulanoemail@gmail.com");
        user.setPassword("senha");

        Address endereco = new Address();
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setBairro("Boa Viagem");
        endereco.setCep("51021-190");
        endereco.setNumero("585");
        endereco.setRua("Rua Imaginaria");

        Camping camping = new Camping();
        camping.setName("Camping 01");
        camping.setPhone("3465-4871");
        camping.setAddress(endereco);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);

        Booking reserva = new Booking();
        reserva.setBookingDate(c);
        reserva.setUser(user);
        reserva.setCamping(camping);

        em.persist(reserva);
        em.flush();
        em.clear();

        assertNotNull(reserva.getId());

    }

    @Test
    public void createBooking02() {
        User u1 = new User();
        u1.setName("Camus");
        u1.setCpf("684.005.154-45");
        u1.setEmail("camus@gmail.com");
        u1.setPassword("senha");

        Address endereco = new Address();
        endereco.setCidade("Cidade");
        endereco.setEstado("Estado");
        endereco.setBairro("Boa Viagem");
        endereco.setCep("51021-190");
        endereco.setNumero("585");
        endereco.setRua("Rua Grega");

        Camping camping = new Camping();
        camping.setName("Camping 01");
        camping.setPhone("3465-4871");
        camping.setAddress(endereco);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);

        Booking reserva = new Booking();
        reserva.setBookingDate(c);
        reserva.setUser(u1);
        reserva.setCamping(camping);

        em.persist(reserva);
        em.flush();
        em.clear();

        assertNotNull(reserva.getId());

    }

    @Test
    public void createBooking03() {
        User user = new User();
        user.setName("Albafica");
        user.setCpf("898.512.645-12");
        user.setEmail("albafica@gmail.com");
        user.setPassword("senha");

        Address endereco = new Address();
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setBairro("Boa Viagem");
        endereco.setCep("51021-190");
        endereco.setNumero("585");
        endereco.setRua("Rua Imaginaria");

        Camping camping = new Camping();
        camping.setName("Camping 02");
        camping.setPhone("3451-5120");
        camping.setAddress(endereco);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);

        Booking reserva = new Booking();
        reserva.setBookingDate(c);
        reserva.setUser(user);
        reserva.setCamping(camping);

        em.persist(reserva);
        em.flush();
        em.clear();

        assertNotNull(reserva.getId());

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
        assertEquals(0, reservas.size());
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
        assertEquals(1, reservas.size());
    }

    @Test
    public void NQCampingPorUser() {
        TypedQuery<Booking> q = em.createNamedQuery("Booking.PorUser", Booking.class);
        q.setParameter(1, "Fulano");
        List<Booking> b = q.getResultList();
        assertEquals(1, b.size());
        //fazer teste comparando as datas
    }

    @Test
    public void NQCampingReservaUnica() {
        Query q = em.createNamedQuery("Booking.PorUser", Booking.class);
        q.setParameter(1, "155.456.871-45");
        q.setParameter(2, "Camping 01");
        Booking b = (Booking) q.getSingleResult();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2018);
        c.set(Calendar.MONTH, Calendar.APRIL);
        c.set(Calendar.DAY_OF_MONTH, 10);

        assertNotNull(b);
        assertEquals(c, b.getBookingDate());
    }
    
    @Test
    public void NativeCampingOrdemData(){
        Query q = em.createNativeQuery("SELECT DT_BOOKINGDATE FROM tb_booking ORDER BY DT_BOOKINGDATE");
        List lista = q.getResultList();
        // fazer teste
        
    }

    
    //testes de update e delete
}
