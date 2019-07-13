import com.ailleron.workshop.JpaService;
import com.ailleron.workshop.configuration.SpringConfig;
import com.ailleron.workshop.entity.jpa.Book;
import com.ailleron.workshop.entity.jpa.BookInfo;
import com.ailleron.workshop.entity.jpa.Student;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);

        JpaService jpaService = context.getBean(JpaService.class);
        List<Student> students = jpaService.prepareStudentData();
        jpaService.addStudentsIntoDb(students);
        List<BookInfo> result = jpaService.getBooksInfoProjectionWithCriteriaApi("Krakow");
        System.out.println(result);



    }

}
