package com.ecamping;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Rating.class)
public abstract class Rating_ extends com.ecamping.Feedback_ {

	public static volatile SingularAttribute<Rating, Camping> camping;
	public static volatile SingularAttribute<Rating, Integer> value;
	public static volatile SingularAttribute<Rating, User> user;

}

