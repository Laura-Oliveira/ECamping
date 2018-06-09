package com.ecamping;

import com.ecamping.Camping;
import com.ecamping.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-09T11:20:18")
@StaticMetamodel(Booking.class)
public class Booking_ { 

    public static volatile SingularAttribute<Booking, String> tent;
    public static volatile SingularAttribute<Booking, Camping> camping;
    public static volatile SingularAttribute<Booking, Date> bookingDate;
    public static volatile SingularAttribute<Booking, Long> id;
    public static volatile SingularAttribute<Booking, User> user;

}