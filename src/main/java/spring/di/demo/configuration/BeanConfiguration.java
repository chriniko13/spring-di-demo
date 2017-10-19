package spring.di.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.di.demo.dao.EmployeeDao;
import spring.di.demo.dao.EmployeeDaoInMemoryImpl;
import spring.di.demo.service.EmployeeService;
import spring.di.demo.service.EmployeeServiceImpl;

@Configuration
public class BeanConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeServiceImpl(employeeDao());
    }

    @Bean
    public EmployeeDao employeeDao() {
        return new EmployeeDaoInMemoryImpl();
    }
}
