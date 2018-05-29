package com.ecamping;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Camping.class)
public abstract class Camping_ {

	public static volatile ListAttribute<Camping, Booking> booking;
	public static volatile SingularAttribute<Camping, Address> address;
	public static volatile SingularAttribute<Camping, String> phone;
	public static volatile SingularAttribute<Camping, String> name;
	public static volatile ListAttribute<Camping, Rating> rating;
	public static volatile ListAttribute<Camping, Comment> comment;
	public static volatile SingularAttribute<Camping, Long> id;
	public static volatile SingularAttribute<Camping, User> user;
	public static volatile SingularAttribute<Camping, String> info;

}

