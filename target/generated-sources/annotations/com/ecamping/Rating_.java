package com.ecamping;

import com.ecamping.Camping;
import com.ecamping.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-06T22:43:49")
@StaticMetamodel(Rating.class)
public class Rating_ extends Feedback_ {

    public static volatile SingularAttribute<Rating, Camping> camping;
    public static volatile SingularAttribute<Rating, Integer> value;
    public static volatile SingularAttribute<Rating, User> user;

}