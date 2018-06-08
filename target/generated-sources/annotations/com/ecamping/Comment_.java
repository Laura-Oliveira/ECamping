package com.ecamping;

import com.ecamping.Camping;
import com.ecamping.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-08T19:22:05")
@StaticMetamodel(Comment.class)
public class Comment_ extends Feedback_ {

    public static volatile SingularAttribute<Comment, Camping> camping;
    public static volatile SingularAttribute<Comment, String> message;
    public static volatile SingularAttribute<Comment, User> user;

}