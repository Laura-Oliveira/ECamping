/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

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
        logger.setLevel(Level.INFO);
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

    @Test
    public void createCamping01() {
        User u1 = new User();
        u1.setName("Camus");
        u1.setCpf("684.005.154-45");
        u1.setEmail("camus@gmail.com");
        u1.setPassword("senha223$H");

        Address endereco = new Address();
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setBairro("Boa Viagem");
        endereco.setCep("51.021-190");
        endereco.setNumero("585");
        endereco.setRua("Rua Imaginaria");

        Camping camping = new Camping();
        camping.setName("Camping 01");
        camping.setPhone("(81)3465-4871");
        camping.setInfo("Informações.");
        camping.setUser(u1);
        camping.setAddress(endereco);

        em.persist(camping);

        em.flush();
        assertNotNull(camping.getId());
    }
/*
    @Test
    public void createCamping02() {
        User u1 = new User();
        u1.setName("Albafica");
        u1.setCpf("754.355.102-78");
        u1.setEmail("albafica@gmail.com");
        u1.setPassword("senha");

        Camping camping = new Camping();
        camping.setName("Camping 02");
        camping.setPhone("3465-4871");
        camping.setInfo("Nullo.");
        camping.setUser(u1);

        Address endereco = new Address();
        endereco.setCidade("dasdad");
        endereco.setEstado("Pernambwqeuco");
        endereco.setBairro("Boa fasfd");
        endereco.setCep("45130-190");
        endereco.setNumero("610");
        endereco.setRua("Rua Imaginaria");
        camping.setAddress(endereco);

        em.persist(camping);
        em.flush();
        em.clear();

        assertNotNull(camping.getId());
    }

    @Test
    public void createCamping03() {
        User u1 = new User();
        u1.setName("Aldebaran");
        u1.setCpf("841.040.542-45");
        u1.setEmail("aldebaran@gmail.com");
        u1.setPassword("senha");

        Camping camping = new Camping();
        camping.setName("Camping 03");
        camping.setPhone("3491-0154");
        camping.setInfo("Sem muitas informações aqui.");
        camping.setUser(u1);

        Address endereco = new Address();
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setBairro("Bairro diferenciado");
        endereco.setCep("78952-150");
        endereco.setNumero("012");
        endereco.setRua("Rua Grega");
        camping.setAddress(endereco);

        em.persist(camping);
        em.flush();
        em.clear();

        assertNotNull(camping.getId());
    }

    @Test
    public void CampingComMaiorInfo() {
        Query query = em.createNativeQuery("SELECT TXT_NAME, max(char_length(TXT_INFO)) from tb_camping");
        String camping = (String) query.getSingleResult();
        em.clear();
        assertEquals("Camping 03", camping);
    }

    @Test
    public void CampingPorUser() {
        TypedQuery<Camping> query = em.createQuery(
                "SELECT c FROM Camping c WHERE c.user.cpf LIKE ?1", Camping.class);
        query.setParameter(1, "841.040.542-45");
        List<Camping> campings = query.getResultList();

        assertEquals(1, campings.size()); //melhorar teste
    }

    @Test
    public void allCampings() {
        TypedQuery<Camping> query = em.createQuery(
                "SELECT c FROM Camping c ORDER BY c.address.estado", Camping.class);
        List<Camping> campings = query.getResultList();
        assertEquals(3, campings.size());
    }


    // TESTES NAMED QUERY    
    @Test
    public void NQCampingSemReservas() {
        TypedQuery q = em.createNamedQuery("Camping.SemReservas", Camping.class);
        List<Camping> campings = q.getResultList();
        for (Camping camping : campings) {
            assertNull(camping.getBooking());
        }

    }
    
    @Test
    public void NQCampingPorNome(){
        Query q = em.createNamedQuery("Camping.PorNome", Camping.class);
        q.setParameter(1, "Camping 03");
        Camping camping = (Camping) q.getSingleResult();
        assertEquals("Camping 03", camping.getName());
    }
    
    @Test
    public void CampingPorCidade(){
        TypedQuery<Camping> q = em.createQuery("SELECT c FROM Camping c WHERE c.address.cidade LIKE ?1", Camping.class);
        q.setParameter(1, "Recife");
        List<Camping> campos = q.getResultList();
        for(Camping c : campos){
            assertEquals("Recife", c.getAddress().getCidade());
        }
    }

    //TESTES NATIVE QUERY
    public void NativeQueryCampingPorTelefone() {
        Query q = em.createNativeQuery("SELECT PHONE FROM tb_camping WHERE TXT_NOME LIKE ?");
        q.setParameter(1, "Camping 02");
        String telefone = (String) q.getSingleResult();
        assertEquals("3465-4871", telefone);
    }

    public void CampingEmOrdem() {
        Query q = em.createNativeQuery("SELECT TXT_NAME FROM tb_user ORDER BY TXT_NAME");
        List<String> nomes = q.getResultList();
        em.clear();
        
        String lista[] = null;
        lista[0] = "Camping 01";
        lista[1] = "Camping";
        lista[3] = "Camping do Caracol Alado";
        for(int i = 0; i < nomes.size(); i++){
            assertEquals(lista[i], nomes.get(i));
        }
    }
    
    @Test
    public void NQEnderecoPorID(){
        Query q = em.createNamedQuery("Endereco.PorId");
        q.setParameter(1, 1);
        Address a = (Address) q.getSingleResult();
        assertNotNull(a);//fazer teste descente
    }
    
    @Test
    public void NQEnderecoPorCidade(){
        TypedQuery<Address> q = em.createNamedQuery("Endereco.PorCidade", Address.class);
        q.setParameter(1, "Recife");
        List<Address> a = q.getResultList();
        for(Address endereco : a){
            assertEquals("Recife", endereco.getCidade());
        }
    }
    
    @Test
    public void NQEnderecoPorEstado(){
        TypedQuery<Address> q = em.createNamedQuery("Endereco.PorEstado", Address.class);
        q.setParameter(1, "Pernambuco");
        List<Address> a = q.getResultList();
        for(Address endereco : a){
            assertEquals("Pernambuco", endereco.getEstado());
        }
    }
    
      /*  @Test
    public void updateCamping() {
        Query findCamping = em.createQuery("SELECT c FROM Camping c where c.name like :nome", Camping.class);
        findCamping.setParameter("nome", "Camping 03");

        Camping camping = (Camping) findCamping.getSingleResult();
        camping.setName("Camping do Caracol Alado");
        em.merge(camping);
        em.flush();
        em.clear();
        //fazer teste (não usar id como parametro na query pls)
    }
*/
    
    //falta teste delete 
   
}
