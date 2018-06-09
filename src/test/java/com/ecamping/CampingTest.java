/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

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
public class CampingTest {

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    public CampingTest() {
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
    public void CreateCamping() {
     
        Address endereco = em.find(Address.class, 5L);

        Camping camping = new Camping();
        camping.setName("Camping 08");
        camping.setInfo("informacao não tão pequena");
        camping.setPhone("(11) 98542-1923");
        camping.setAddress(endereco);

        try {
            em.persist(camping);
            em.flush();
            assertNotNull(camping); //algo de errado não está certo
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
    public void NQCampingSemReservas() {

        TypedQuery q = em.createNamedQuery("Camping.SemReservas", Camping.class);
        List<Camping> campings = q.getResultList();
        assertEquals(2, campings.size());

    }

    @Test
    public void NQCampingPorNome() {
        Query q = em.createNamedQuery("Camping.PorNome", Camping.class);
        q.setParameter(1, "Camping 01");
        Camping camping = (Camping) q.getSingleResult();

        assertEquals("Camping 01", camping.getName());
    }

    @Test
    public void NQEnderecoPorCidade() {

        TypedQuery<Address> q = em.createNamedQuery("Endereco.PorCidade", Address.class);
        q.setParameter(1, "Recife");
        List<Address> a = q.getResultList();
        for (Address endereco : a) {
            assertEquals("Recife", endereco.getCidade()); 
        }
    }

    @Test
    public void NQEnderecoPorID() {
        Query q = em.createNamedQuery("Endereco.PorId");
        q.setParameter(1, 1);
        Address a = (Address) q.getSingleResult();
        assertEquals("51021-190", a.getCep());
    }

    @Test
    public void NQEnderecoPorEstado() {

        TypedQuery<Address> q = em.createNamedQuery("Endereco.PorEstado", Address.class);
        q.setParameter(1, "Pernambuco");
        List<Address> a = q.getResultList();
        for (Address endereco : a) {
            assertEquals("Pernambuco", endereco.getEstado());
        }
    }

    @Test
    public void NativeQueryCampingComMaiorInfo() {
        
        Query query = em.createNativeQuery("select txt_name from tb_camping "
                + "group by character_length(TXT_INFO), txt_name "
                + "order by MAX(char_length(TXT_INFO)) desc limit 1;");
        String camping = (String) query.getSingleResult();
        assertEquals("Camping 02", camping);
    }

    @Test
    public void NativeQueryCampingPorTelefone() {

        Query q = em.createNativeQuery("SELECT PHONE FROM tb_camping WHERE TXT_NAME LIKE ?");
        q.setParameter(1, "Camping 02");
        String telefone = (String) q.getSingleResult();
        assertEquals("(81) 99456-9035", telefone);
    }

    @Test
    public void NativeQueryRetornaNomeDeCamping() {
        Query q = em.createNativeQuery("SELECT TXT_NAME FROM tb_camping WHERE ID = 5");
        String resultado = (String) q.getSingleResult();
        assertEquals("Camping 05", resultado);
    }


    @Test
    public void JPQLretornaAddressQueIniciamComR() {
        TypedQuery<Address> query = em.createQuery(
                "SELECT a FROM Address a WHERE a.cidade LIKE :cidade ORDER BY a.id DESC", Address.class);
        query.setParameter("cidade", "r%");
        List<Address> cidades = query.getResultList();
        assertEquals(2, cidades.size());
    }

    @Test
    public void JPQLretornaQuantidadeDeCampingsNumEstado() {
        Query query = em.createQuery("SELECT COUNT(c) FROM Camping c WHERE c.address.estado = ?1", Long.class);
        query.setParameter(1, "São Paulo");
        long total = (long) query.getSingleResult();
        assertEquals(total, (long) 1);
    }

    @Test
    public void JPQLretornaCampingsComNumeroDeEnderecoEntre0e100() {
        TypedQuery<Camping> q = em.createQuery("SELECT c FROM Camping c WHERE c.address.numero BETWEEN 100 and 300", Camping.class);
        List<Camping> resultado = q.getResultList();
        assertEquals(4, resultado.size());
    }

    @Test
    public void JQLupdateCamping() {
        Query q = em.createQuery("UPDATE Camping c SET c.name = :novonome WHERE c.id = :id");
        q.setParameter("novonome", "Camping dahora");
        q.setParameter("id", 4L);

        q.executeUpdate();
        em.flush();

        Query query = em.createQuery("SELECT c.name FROM Camping c WHERE c.id LIKE :id", String.class);
        query.setParameter("id", 4L);
        String c = (String) query.getSingleResult();
        assertEquals("Camping dahora", c);

    }

    @Test
    public void JPQLDeleteCamping() {
        Query q = em.createQuery("DELETE FROM Camping c WHERE c.id LIKE 6");

        q.executeUpdate();

        assertNull(em.find(Camping.class, (long) 6));
    }

}