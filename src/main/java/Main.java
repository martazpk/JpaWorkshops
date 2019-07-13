import com.ailleron.workshop.JpaService;
import com.ailleron.workshop.configuration.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);

//        JpaService jpaService = context.getBean(JpaService.class);
////        jpaService.savePersonIntoDb();
//
//        jpaService.modifyPerson("Parker");

    }

}
