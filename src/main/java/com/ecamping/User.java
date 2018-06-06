/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author Ramon
 */
@Entity
@Table(name = "tb_user")
@Access(AccessType.FIELD)
@NamedNativeQueries(
        {
            @NamedNativeQuery(
                    name = "User.PorEmail",
                    query = "SELECT TXT_NAME FROM tb_user WHERE TXT_EMAIL LIKE ?1"
            ),
            @NamedNativeQuery(
                    name = "User.OrdemNome",
                    query = "SELECT TXT_NAME FROM tb_user ORDER BY TXT_NAME"
            ),            
        }
)

@NamedQueries(
        {
            @NamedQuery(
                    name = "User.PorNome",
                    query = "SELECT u FROM User u WHERE u.name LIKE ?1 ORDER BY u.id"
                    
            )            
        }
)

@SqlResultSetMapping(
        name = "User.QuantidadeUsers",
        entities = {
            @EntityResult(entityClass = User.class)},
        columns = {
            @ColumnResult(name = "id", type = Long.class)
            ,
                    @ColumnResult(name = "cpf", type = String.class)
            ,
                    @ColumnResult(name = "name", type = String.class)
            ,
                    @ColumnResult(name = "email", type = String.class)
            }
)
public class User implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @CPF(message="{invalid.cpf}")
    @Max(value=14)
    @Column(name = "TXT_CPF", nullable = false, length = 14, unique = true)
    private String cpf;
    @NotNull
    @Size(min = 5, max=200)
    @Column(name = "TXT_NAME", nullable = false, length = 255)
    private String name;
    
    
    @NotNull
    @Email(message="{invalid.email}")
    @Column(name = "TXT_EMAIL", nullable = false, length = 70)
    private String email;
    
    @NotBlank
    @Pattern(regexp = "((?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", message="{invalid.password})")
    @Column(name = "TXT_PASSWORD", nullable = false, length = 20)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            targetEntity = Booking.class, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Booking> booking;
    
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, targetEntity = Rating.class,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Rating> rating;
    
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, targetEntity = Comment.class,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
    
    public void addRating(Rating rating) {
        this.rating.add(rating);
        rating.setUser(this);
    }
    
    public void addComment(Comment comment) {
        this.comment.add(comment);
        comment.setUser(this);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }

}
