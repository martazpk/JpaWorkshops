package com.ailleron.workshop.service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ailleron.workshop.configuration.jpa.JpaConfig;
import com.ailleron.workshop.entity.jpa.Address;
import com.ailleron.workshop.entity.jpa.Person;
import com.ailleron.workshop.entity.jpa.Person_;

@Service
public class JpaService {

    private EntityManager entityManager;

    @Autowired
    public JpaService(JpaConfig jpaConfig) {
        this.entityManager = jpaConfig.getEntityManager();
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


}
