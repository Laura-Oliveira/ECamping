/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Isabella
 */
@Entity
@Table(name="tb_address")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name = "Endereco.PorId",
                    query = "SELECT a FROM Address a WHERE a.id LIKE ?1"
                     
            ),
            @NamedQuery(
                    name = "Endereco.PorCidade",
                    query = "SELECT a FROM Address a WHERE a.cidade LIKE ?1"                    
            ),
            @NamedQuery(
                    name = "Endereco.PorEstado",
                    query = "SELECT a FROM Address a WHERE a.estado LIKE ?1"                    
            )
        }
)
public class Address implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Pattern(regexp = "[0-90]{2}.[0-9]{3}-[0-9]{3}", message = "{invalid.cep}")
    @Column(name= "TXT_CEP", nullable = false, length = 10)
    private String cep;
    
    @NotNull
    @Column(name= "TXT_ESTADO", nullable = false, length = 200)
    private String estado;
    
    @NotNull    
    @Column(name= "TXT_CIDADE", nullable = false, length = 200)
    private String cidade;
    
    @NotNull
    @Column(name= "TXT_RUA", nullable = false, length = 200)
    private String rua;
    
    @NotNull
    @Column(name= "TXT_BAIRRO", nullable = false, length = 200)
    private String bairro;
    
    @NotBlank
    @NotNull    
    @Column(name= "TXT_NUMERO", nullable = false, length = 200)
    private String numero;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


    
    
    
    
}