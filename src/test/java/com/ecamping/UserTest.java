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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author isabella
 */
public class UserTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public UserTest() {
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

    //CREATES
   /*
    @Test
    public void createUser() {
        User u1 = new User();
        u1.setName("Joao da Silva Oliveira");
        u1.setCpf("684.005.154-45");
        u1.setEmail("joao_oliveira@gmail.com");
        u1.setPassword("Senh@_");
        em.persist(u1);
        em.flush();
        assertNotNull(u1.getId());
    }
*/
    //TESTES NATIVE QUERY
   /*
    @Test
    public void UsuarioPorId() {
        Query query = em.createNativeQuery("SELECT TXT_NAME FROM tb_user WHERE ID LIKE 3", User.class);
        String user = (String) query.getSingleResult();
        assertNotNull(user);
        em.clear();
    }
    */
/*
    @Test
    public void UsuarioPorNome() {
        Query query = em.createNativeQuery("SELECT TXT_NAME FROM tb_user WHERE TXT_NAME LIKE ?", User.class);
        query.setParameter(1, "Makenshi");
        String usuario = query.getSingleResult().toString();
        em.clear();
        assertNotNull(usuario, query);
    }
*/
    @Test
    public void NNQUsuarioPorEmail() {
        Query q = em.createNamedQuery("User.PorEmail", User.class);
        q.setParameter(1, "frida_kahlo@hotmail.com");
        String found = q.getSingleResult().toString();
        em.clear();
        assertEquals("Frida Kahlo", found);

    }

    @Test
    public void NNQNomesOrdenados() {
        Query q = em.createNamedQuery("User.OrdemNome", User.class);
        List<String> nomes = q.getResultList();
        em.clear();

        assertNotNull(nomes); //melhorar teste
    }

    //TESTES JPQL NORMAL
    @Test
    public void UsuarioPorCPF() {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.cpf LIKE :cpf", User.class);
        query.setParameter("cpf", "909.987.710-23");
        User usuario = (User) query.getSingleResult();
        assertNotNull(usuario);
        em.clear();
    }

    @Test
    public void UsuariosComecandoPelaLetraA() {
        TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.name LIKE :nome ORDER BY u.id", User.class);
        q.setParameter("nome", "a%");
        List<User> usuarios = q.getResultList();
        for (User usuario : usuarios) {
            assertEquals("A", usuario.getName().substring(0, 1).toUpperCase());
        }
        em.clear();
    }

    @Test
    public void SelecionarTodosUsuarios() {
        Query query = em.createQuery("SELECT u FROM User u");
        List<User> usuarios = query.getResultList();
        em.clear();
        Assert.assertEquals(10, usuarios.size());

    }

    //TESTES NAMED QUERY    
    @Test
    public void NQUsuarioPorNome() {
        TypedQuery<User> q = em.createNamedQuery("User.PorNome", User.class);
        q.setParameter(1, "Camus");
        List<User> usuarios = q.getResultList();
        em.clear();
        assertNotNull(usuarios); //melhorar teste

    }
/*
    @Test
    public void updateUser() {
        Long id = 1L;
        Query query = em.createNativeQuery("UPDATE User AS u SET u.name = ?1 WHERE u.id = ?2", User.class);
        query.setParameter(1, "Germino");
        query.setParameter(2, id);
        query.executeUpdate();
        em.flush();
        em.clear();
        Query q = em.createQuery("SELECT u FROM User u WHERE u.id LIKE ?1", User.class);
        q.setParameter(1, id);
        User usuario = (User) q.getSingleResult();
        em.clear();
        assertEquals("Germino", usuario.getName());
    }
    @Test
    public void deleteUser() {
        Long id = 5L;
        Query query = em.createQuery("DELETE FROM User AS u WHERE u.id = ?5", User.class);
        query.setParameter(5, id);
        query.executeUpdate();
        em.flush();
        assertNull(em.find(User.class, id));
        em.clear();
    }
*/
    //SqlResultMapping Test
    public void DadosUsuario() {
        Query q = em.createNativeQuery("SELECT ID, TXT_NAME,TXT_CPF,TXT_EMAIL WHERE TXT_EMAIL LIKE ?");
        q.setParameter(1, "aldebaran@gmail.com");
        List lista = q.getResultList();
        Object[] user = null;
        for (int i = 0; i < lista.size(); i++) {
            user = (Object[]) lista.get(i);
        }
        assertEquals(user[1], "Aldebaran");
    }

}
