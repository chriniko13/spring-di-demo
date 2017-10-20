package spring.di.demo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.di.demo.configuration.BeanConfiguration;
import spring.di.demo.domain.Employee;
import spring.di.demo.service.EmployeeService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Demonstrator {

    enum ContextConfig {
        XML_BASED_CONFIG,
        JAVA_BASED_CONFIGURATION
    }

    private static ApplicationContext getContext(ContextConfig contextConfig) {

        switch (contextConfig) {

            case XML_BASED_CONFIG: //DONE
                return new ClassPathXmlApplicationContext("config-beans.xml");

            case JAVA_BASED_CONFIGURATION: //DONE
                return new AnnotationConfigApplicationContext(BeanConfiguration.class);

            default:
                throw new IllegalStateException();
        }

    }

    public static void main(String[] args) {

        final ApplicationContext context = getContext(ContextConfig.XML_BASED_CONFIG);
        firstExample(context);
    }

    private static void firstExample(ApplicationContext context) {
        EmployeeService employeeService = context.getBean(EmployeeService.class);

        //findAll demonstration...
        List<Employee> employees = employeeService.findAll();
        System.out.println("\n--- findAll ---");
        employees.forEach(System.out::println);


        //find(List<Long>) demonstration...
        List<Employee> employees2 = employeeService.find(Arrays.asList(1L, 2L, 3L, 154L));
        System.out.println("\n--- find(List<Long>) ---");
        employees2.forEach(System.out::println);


        //find(Long) demonstration...
        Employee employee = employeeService.find(1L);
        System.out.println("\n--- find(Long) ---");
        System.out.println(employee);


        //find(Long) demonstration...
        Employee employee2 = employeeService.find(123L);
        System.out.println("\n--- find(Long) ---");
        System.out.println(employee2);


        //find(Boolean) demonstration...
        List<Employee> employees3 = employeeService.find(false);
        System.out.println("\n--- find(Boolean) ---");
        employees3.forEach(System.out::println);


        //find(Boolean) demonstration...
        List<Employee> employees4 = employeeService.find(true);
        System.out.println("\n--- find(Boolean) ---");
        employees4.forEach(System.out::println);


        //find(String,String,String) demonstration...
        List<Employee> employees5 = employeeService.find("Firstname 1", "Initials 1", "Surname 1");
        System.out.println("\n--- find(String,String,String) ---");
        employees5.forEach(System.out::println);


        //find(String,String,String) demonstration...
        List<Employee> employees6 = employeeService.find("Firstname 123", "Initials 1", "Surname 1");
        System.out.println("\n--- find(String,String,String) ---");
        employees6.forEach(System.out::println);


        //insert - update- delete demonstration...
        employeeService.insert(Employee.builder().firstname("Niko").surname("Chri").fired(true).build());
        System.out.println("\n--- insert(Employee) - update(Employee) - delete(Long) ---");

        List<Employee> employees7 = employeeService.find("Niko", null, "Chri");
        employees7.forEach(System.out::println);

        Employee chrinikoEmployee = employees7.get(0);
        chrinikoEmployee.setFired(false);

        employeeService.update(chrinikoEmployee);

        List<Employee> employees8 = employeeService.find("Niko", null, "Chri");
        employees8.forEach(System.out::println);

        employeeService.delete(chrinikoEmployee.getId());
        List<Employee> employees9 = employeeService.find("Niko", null, "Chri");
        System.out.println(Optional.ofNullable(employees9).filter(l -> !l.isEmpty()).map(l -> l.get(0)).orElse(null));


        //update(List<Employee) demonstration...
        System.out.println("\n--- update(List<Employee>) ---");
        List<Employee> employees10 = employeeService.findAll();
        employees10.forEach(emp -> {
            emp.setFirstname("[BACKUP]" + emp.getFirstname());
        });

        employeeService.update(employees10);
        List<Employee> employees11 = employeeService.findAll();
        employees11.forEach(System.out::println);
    }

}
