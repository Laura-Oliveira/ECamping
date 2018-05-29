package com.ecamping;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, Camping> camping;
	public static volatile ListAttribute<User, Booking> booking;
	public static volatile SingularAttribute<User, String> cpf;
	public static volatile SingularAttribute<User, String> name;
	public static volatile ListAttribute<User, Rating> rating;
	public static volatile ListAttribute<User, Comment> comment;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> email;

}

