package com.ecamping;

import com.ecamping.Booking;
import com.ecamping.Comment;
import com.ecamping.Rating;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-09T11:20:18")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, Booking> booking;
    public static volatile SingularAttribute<User, String> cpf;
    public static volatile SingularAttribute<User, String> name;
    public static volatile ListAttribute<User, Rating> rating;
    public static volatile ListAttribute<User, Comment> comment;
    public static volatile SingularAttribute<User, Long> id;
    public static volatile SingularAttribute<User, String> email;

}