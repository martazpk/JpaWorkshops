package com.ailleron.workshop;

import com.ailleron.workshop.configuration.JpaConfig;
import com.ailleron.workshop.entity.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

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

    public Person getPersonUsingJpql(String surname) {
        TypedQuery<Person> query = entityManager.createQuery("select p from Person p where p.surname=:surname", Person.class);
        query.setParameter("surname", surname);
        return query.getSingleResult();
    }

    public void modifyPerson(String surname) {
        Person person = getPerson(surname);
        entityManager.getTransaction().begin();
        person.setSurname("Zmiana");
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

    public List<Student> prepareStudentData() {
        Computer computer1 = new Computer();
        computer1.setSerialNumber("SERIAL1");
        computer1.setDeviceName("ASDASD");
        computer1.setLocalization("Krakow");

        Computer computer2 = new Computer();
        computer2.setSerialNumber("SERIAL2");
        computer2.setDeviceName("ASDASD2");
        computer2.setLocalization("Warszawa");

        Author author = new Author();
        author.setName("Julian");
        author.setSurname("Tuwim");

        Author author2 = new Author();
        author2.setName("Jan");
        author2.setSurname("Brzechwa");

        Author author3 = new Author();
        author3.setName("Aleksander");
        author3.setSurname("Kwaśniewski");

        Book book1 = new Book();
        book1.setTitle("Wiersze");
        book1.setAuthor(author);

        Book book2 = new Book();
        book2.setTitle("Akademia Pana Kleksa");
        book2.setAuthor(author2);

        Book book3 = new Book();
        book3.setTitle("Zycie zaczyna sie po 50");
        book3.setAuthor(author3);

        Student student = new Student();
        student.setName("Pioter");
        student.setSurname("Nosacz");
        student.getBooks().add(book1);
        student.getComputers().add(computer1);
        student.getComputers().add(computer2);
        computer1.getStudents().add(student);
        computer2.getStudents().add(student);

        Student student2 = new Student();
        student2.setName("Jan");
        student2.setSurname("Nowak");
        student2.getBooks().add(book2);
        student2.getComputers().add(computer1);
        computer1.getStudents().add(student2);

        Student student3 = new Student();
        student3.setName("Mariusz");
        student3.setSurname("Chrusciel");
        student3.getBooks().add(book3);
        student3.getComputers().add(computer2);
        computer2.getStudents().add(student3);

        book1.setStudent(student);
        book2.setStudent(student2);
        book3.setStudent(student3);
        return Arrays.asList(student, student2, student3);
    }

    public void addStudentsIntoDb(List<Student> students) {
        entityManager.getTransaction().begin();
        students.forEach(student -> entityManager.persist(student));
        entityManager.getTransaction().commit();
    }

    public List<Book> getBooksTakeByStudentsFromLocationNativeQuery(String location) {
        Query nativeQuery = entityManager.createNativeQuery("SELECT DISTINCT b.* FROM book b " +
                "JOIN student s ON b.student_id=s.id " +
                "JOIN computer_student cs ON s.id=cs.student_id " +
                "JOIN computer c ON cs.computer_id=c.id " +
                "WHERE c.localization =?1", Book.class);
        nativeQuery.setParameter(1, location);
        return nativeQuery.getResultList();
    }

    public List<Book> getBooksTakeByStudentsFromLocationJpql(String location) {
        TypedQuery<Book> query = entityManager.createQuery("SELECT  b FROM Book b " +
                "JOIN b.student s " +
                "JOIN s.computers c " +
                "WHERE c.localization =?1 GROUP BY b", Book.class);
        query.setParameter(1, location);
        return query.getResultList();
    }

    public List<BookInfo> getBooksProjectionJpql(String location) {
        TypedQuery<BookInfo> query = entityManager.createQuery("SELECT new com.ailleron.workshop.entity.jpa.BookInfo(b.title,s.name,s.surname)  FROM Book b " +
                "JOIN b.student s " +
                "JOIN s.computers c " +
                "WHERE c.localization =?1 GROUP BY b.title,s.name,s.surname", BookInfo.class);
        query.setParameter(1, location);
        return query.getResultList();
    }

    public List getBooksInfoProjectionWithCriteriaApi(String location) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookInfo> query = cb.createQuery(BookInfo.class);
        Root<Book> bookRoot = query.from(Book.class);
        Join<Book, Student> student = bookRoot.join("student");
        Join<Student, Computer> computers = student.join("computers");

        query
                .select(cb.construct(
                        BookInfo.class,
                        bookRoot.get("title"),
                        student.get("name"),
                        student.get("surname")))
                .where(cb.like(computers.get("localization"), location))
                .groupBy(bookRoot.get("title"), student.get("name"), student.get("surname"));
        return entityManager.createQuery(query).getResultList();

    }
    }

