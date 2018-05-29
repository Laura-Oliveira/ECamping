/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Ramon
 */
@Entity
@Table(name="tb_rating")
@DiscriminatorValue(value = "R")
@PrimaryKeyJoinColumn(name="ID_FEEDBACK", referencedColumnName = "ID")
//@NamedQueries(
//        {
//            @NamedQuery(
//                    name = "Rating.ValueLessThanThree",
//                    query = "SELECT a FROM Rating a WHERE a.nome LIKE :nome ORDER BY a.id"
//            ),
//                @NamedQuery(
//                    name = "Rating.PorTipoDeExercicio",
//                    query = "SELECT DISTINCT a FROM Rating a JOIN a.exercicios xs WHERE xs.tipo = :ex"
//            )
//        }
//)
public class Rating extends Feedback implements Serializable{
    
    @Max(5)
    @NotNull
    @Column(name="INT_VALUE", nullable = false)
    private int value;
    
    @NotNull(message = "O usuario precisa ser definido!")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="ID_USER", referencedColumnName = "ID", nullable = false)
    private User user;
    
    @NotNull(message = "O camping precisa ser definido!")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name="ID_CAMPING", referencedColumnName = "ID", nullable = false)
    private Camping camping;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Camping getCamping() {
        return camping;
    }

    public void setCamping(Camping camping) {
        this.camping = camping;
    }
    
    
    
}
