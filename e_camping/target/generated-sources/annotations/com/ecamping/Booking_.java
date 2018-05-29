package com.ecamping;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Booking.class)
public abstract class Booking_ {

	public static volatile SingularAttribute<Booking, String> tent;
	public static volatile SingularAttribute<Booking, Camping> camping;
	public static volatile SingularAttribute<Booking, Calendar> bookingDate;
	public static volatile SingularAttribute<Booking, Long> id;
	public static volatile SingularAttribute<Booking, User> user;

}

