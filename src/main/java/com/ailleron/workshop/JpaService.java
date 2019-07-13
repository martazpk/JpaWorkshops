package com.ailleron.workshop;

import com.ailleron.workshop.configuration.JpaConfig;
import com.ailleron.workshop.entity.jpa.Address;
import com.ailleron.workshop.entity.jpa.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Service
public class JpaService {

    private EntityManager entityManager;

    @Autowired
    public JpaService(JpaConfig jpaConfig) {
        this.entityManager = jpaConfig.getEntityManager();
    }

    public void savePersonIntoDb() {
        entityManager.getTransaction().begin();
        Person person = new Person();
        person.setBillingAddress(new Address("wrząsowice", "Biała", "22-222"));
        person.setName("Peter");
        person.setSurname("Parker");
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    public Person getPerson(String surname) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> query = cb.createQuery(Person.class);

        Root<Person> from = query.from(Person.class);
        query.select(from).where(cb.equal(from.get("surname"), surname));
        return entityManager.createQuery(query).getSingleResult();
    }

    public Person getPersonUsingNative(String surname) {
        Query query = entityManager.createNativeQuery("select * from Person p where p.surname=?1", Person.class);
        query.setParameter(1, surname);
        return (Person) query.getSingleResult();
    }

    public Person getPersonUsingJpql(String surname){
        TypedQuery<Person> query = entityManager.createQuery("select p from Person p where p.surname=:surname", Person.class);
        query.setParameter("surname",surname);
        return query.getSingleResult();
    }

    public void modifyPerson(String surname){
        Person person = getPerson(surname);
        entityManager.getTransaction().begin();
        person.setSurname("Zmiana");
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

}

