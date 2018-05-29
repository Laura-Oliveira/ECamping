package com.ecamping;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ extends com.ecamping.Feedback_ {

	public static volatile SingularAttribute<Comment, Camping> camping;
	public static volatile SingularAttribute<Comment, String> message;
	public static volatile SingularAttribute<Comment, User> user;

}

