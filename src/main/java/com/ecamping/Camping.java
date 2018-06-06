/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Laura
 */
@Entity
@Table(name="tb_camping")
@Access(AccessType.FIELD)
@NamedQueries(
        {
            @NamedQuery(
                    name ="Camping.PorNome",
                    query = "SELECT c FROM Camping c WHERE c.name LIKE ?1"
                    
            ),
            @NamedQuery(
                    name = "Camping.SemReservas",
                    query = "SELECT c FROM Camping c WHERE c.booking IS EMPTY"
                    
            )
        }
)
public class Camping implements Serializable 
{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Size (min = 5, max=200)
    @Column(name= "TXT_NAME", nullable = false, length = 200)
    private String name;
    
    @NotNull
    @Size (min = 5)
    @Column(name= "TXT_INFO", nullable = false)
    private String info;
    
    @NotBlank
    @Pattern (regexp = " ^(\\([0-9]{2}\\))\\s([9]{1})?([0-9]{4})-([0-9]{4})$", message="{invalid.phone}")
    @Max(value=17)
    @Column(name="PHONE", nullable = false, length = 17)
    private String phone;
    
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, optional = false)
    @JoinColumn(name = "ID_ADDRESS", referencedColumnName = "ID")
    private Address address; 
    
    
    @OneToMany(mappedBy="camping", cascade = CascadeType.ALL, targetEntity = Booking.class,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Booking> booking;
    
    @OneToMany(mappedBy="camping", cascade = CascadeType.ALL, targetEntity = Rating.class,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Rating> rating;
    
    @OneToMany(mappedBy="camping", cascade = CascadeType.ALL, targetEntity = Comment.class,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comment;

    public Camping() {
    }
    
    
    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }
    
    public void addBooking(Booking booking) {
        this.booking.add(booking);
        booking.setCamping(this);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        rating.setCamping(this);
    }
    
    public void addComment(Comment comment) {
        this.comment.add(comment);
        comment.setCamping(this);
    }

    
    
}
