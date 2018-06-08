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

    /*  @Test
    public void createCamping01() {
        Address endereco = new Address();
        endereco.setCidade("Recife");
        endereco.setEstado("Pernambuco");
        endereco.setBairro("Boa Viagem");
        endereco.setCep("51.311-930");
        endereco.setNumero("585");
        endereco.setRua("Rua Imaginaria");

        Camping camping = new Camping();
        camping.setName("Camping 06");
        camping.setPhone("(81) 99509-3416");
        camping.setInfo("Informações.");
        camping.setAddress(endereco);

        em.persist(camping);

        em.flush();
        assertNotNull(camping.getId());
    }*/
    
    // TESTES NAMED QUERY    
    @Test
    public void NQCampingSemReservas() {
        //Busca campings que não tem reserva, ou seja, c.booking is EMPTY
        TypedQuery q = em.createNamedQuery("Camping.SemReservas", Camping.class);
        List<Camping> campings = q.getResultList();
        assertEquals(1, campings.size());

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
        //Busca pelos campings que no address a cidade seja Recife
        TypedQuery<Address> q = em.createNamedQuery("Endereco.PorCidade", Address.class);
        q.setParameter(1, "Recife");
        List<Address> a = q.getResultList();
        for (Address endereco : a) {
            assertEquals("Recife", endereco.getCidade()); //modificar p/ verificar a contagem
        }
    }

    @Test
    public void NQEnderecoPorID() {
        Query q = em.createNamedQuery("Endereco.PorId");
        q.setParameter(1, 1);
        Address a = (Address) q.getSingleResult();
        assertEquals("51.021-190", a.getCep());
    }

    @Test
    public void NQEnderecoPorEstado() {
        //Busca address que estejam registrados com o estado de Pernambuco
        TypedQuery<Address> q = em.createNamedQuery("Endereco.PorEstado", Address.class);
        q.setParameter(1, "Pernambuco");
        List<Address> a = q.getResultList();
        for (Address endereco : a) {
            assertEquals("Pernambuco", endereco.getEstado());//fazer por contagem
        }
    }

    //TESTES NATIVE QUERY
    @Test
    public void NativeQueryCampingComMaiorInfo() {
        //Seleciona o nome do camping que tem maior informação
        Query query = em.createNativeQuery("select txt_name from tb_camping "
                + "group by character_length(TXT_INFO), txt_name "
                + "order by MAX(char_length(TXT_INFO)) desc limit 1;");
        String camping = (String) query.getSingleResult();
        assertEquals("Camping 02", camping);
    }
    
    @Test
    public void NativeQueryCampingPorTelefone() {
        //Seleciona o telefone do Camping 02
        Query q = em.createNativeQuery("SELECT PHONE FROM tb_camping WHERE TXT_NAME LIKE ?");
        q.setParameter(1, "Camping 02");
        String telefone = (String) q.getSingleResult();
        assertEquals("(81) 99456-9035", telefone);
    }
    
    @Test
    public void NativeQueryRetornaNomeDeCamping(){
        Query q = em.createNativeQuery("SELECT TXT_NAME FROM tb_camping WHERE ID = 5");
        String resultado = (String) q.getSingleResult();
        assertEquals("Camping 05", resultado);
    }

    /*public void EstadosEmOrdem() {
        String estado0 = "Pará";
        String estado1 = "Paraná";
        String estado2 = "Paraíba";
        String estado3 = "Piauí";
        String estado4 = "Pernambuco";
        String estado5 = "Rio de Janeiro";
        String estado6 = "Rio Grande do Norte";
        String estado7 = "São Paulo";
        
        List<String> estados.add(estado0);
        Query q = em.createNativeQuery("SELECT TXT_CIDADE FROM tb_address ORDER BY TXT_ESTADO", Camping.class);
        List<Camping> resultado = q.getResultList();

        for
     {
            
        }
    }*/
    //TESTES JPQL
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
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Camping c WHERE c.address.estado = ?1", Long.class);
        query.setParameter(1, "São Paulo");
        long total = query.getSingleResult();
        assertEquals(total, (long) 1);
    }
    
    @Test
    public void JPQLretornaCampingsComNumeroDeEnderecoEntre0e100(){
        TypedQuery<Camping> q = em.createQuery("SELECT c FROM Camping c WHERE c.address.numero BETWEEN 100 and 300", Camping.class);
        List<Camping> resultado = q.getResultList();
        assertEquals(4, resultado.size());
    }
    
    @Test
    public void JPQLupdateCamping(){
        Query q = em.createQuery("UPDATE Camping c SET c.name = :novonome WHERE c.id LIKE 4");
        q.setParameter("novonome", "Camping da zoeira");
        
        q.executeUpdate();
        //terminar teste
        
        //uery query = em.createQuery("SELECT c FROM Camping c WHERE c.id LIKE 4");
        //Camping c = (Camping) q.getSingleResult();
        
        //assertEquals("Camping da zoeira", c.getName());
    }

    /* @Test
    public void allCampings() {
        //Seleciona todos os campings e os ordena pelo estado
        TypedQuery<Camping> query = em.createQuery(
                "SELECT c FROM Camping c ORDER BY c.address.estado", Camping.class);
        List<Camping> campings = query.getResultList();
        //assertEquals(3, campings.size());
    }*/
    //falta teste delete 
}
