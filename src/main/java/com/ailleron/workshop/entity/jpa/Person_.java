package com.ailleron.workshop.entity.jpa;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Person.class)
public class Person_ {
    public static SingularAttribute<Person,String>
    surname;
}
