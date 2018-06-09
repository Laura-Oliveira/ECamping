package com.ecamping;

import com.ecamping.Camping;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-09T11:20:18")
@StaticMetamodel(Address.class)
public class Address_ { 

    public static volatile SingularAttribute<Address, String> estado;
    public static volatile SingularAttribute<Address, String> cidade;
    public static volatile SingularAttribute<Address, Camping> camping;
    public static volatile SingularAttribute<Address, String> numero;
    public static volatile SingularAttribute<Address, String> bairro;
    public static volatile SingularAttribute<Address, Long> id;
    public static volatile SingularAttribute<Address, String> cep;
    public static volatile SingularAttribute<Address, String> rua;

}