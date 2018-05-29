package com.ecamping;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> estado;
	public static volatile SingularAttribute<Address, String> cidade;
	public static volatile SingularAttribute<Address, Camping> camping;
	public static volatile SingularAttribute<Address, String> numero;
	public static volatile SingularAttribute<Address, String> bairro;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, String> cep;
	public static volatile SingularAttribute<Address, String> rua;

}

