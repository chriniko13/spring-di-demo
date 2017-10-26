package spring.di.demo.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.di.demo.dao.EmployeeDaoInMemoryImpl;
import spring.di.demo.domain.Employee;
import spring.di.demo.service.EmployeeService;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config-beans.xml"})
public class FullServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeDaoInMemoryImpl employeeDaoInMemory;

    @Test
    public void findAll_Test() throws Exception {

        //given...
        Method initialize = employeeDaoInMemory.getClass().getDeclaredMethod("initialize");
        initialize.setAccessible(true);
        initialize.invoke(employeeDaoInMemory);

        //when...
        List<Employee> all = employeeService.findAll();

        //then...
        Assert.assertEquals(10, all.size());
        int idx = 1;
        for (Employee employee : all) {
            Assert.assertEquals("Firstname " + (idx++), employee.getFirstname());
        }

    }

    @Test
    public void find_list_long_Test() throws Exception {

        //given...
        List<Long> idsToSearchFor = Arrays.asList(1L, 2L);

        //when...
        List<Employee> result = employeeService.find(idsToSearchFor);

        //then...
        Assert.assertEquals(result.size(), 2);
        int idx = 1;
        for (Employee employee : result) {
            Assert.assertEquals("Firstname " + (idx++), employee.getFirstname());
        }

    }

    @Test
    public void find_long_Test() throws Exception {

        //when...
        Employee employee = employeeService.find(1L);

        //then...
        Assert.assertEquals(employee.getFirstname(), "Firstname 1");
    }

    @Test
    public void find_boolean_Test() throws Exception {

        //given...
        List<Employee> firedEmployees = employeeService.findAll().stream().filter(Employee::getFired).collect(Collectors.toList());

        //when...
        List<Employee> result = employeeService.find(true);

        //then...
        Assert.assertEquals(result, firedEmployees);

    }

    @Test
    public void find_string_string_string_Test() throws Exception {

        //when...
        List<Employee> result = employeeService.find("Firstname 1", "Initials 1", "Surname 1");

        //then...
        Assert.assertEquals(result.get(0).getFirstname(), "Firstname 1");

    }

    @Test
    public void insert_Test() throws Exception {

        //given...
        Employee employee = Employee.builder().firstname("chri").initials(null).surname("niko").fired(false).build();

        //when...
        employeeService.insert(employee);

        //then...
        List<Employee> result = employeeService.find("chri", null, "niko");
        Assert.assertEquals(result.get(0), employee);

    }

    @Test
    public void update_Test() throws Exception {

        //given...
        Employee employee = Employee.builder().id(1L).firstname("Firstname 1").initials("Initials 1").surname("Surname 1").fired(true).build();

        //when...
        employeeService.update(employee);

        //then...
        Employee result = employeeService.find(1L);
        Assert.assertEquals(result, employee);

    }

    @Test
    public void delete_Test() throws Exception {

        //when...
        employeeService.delete(1L);
        Employee employee = employeeService.find(1L);

        //then...
        Assert.assertNull(employee);

    }

    @Test
    public void update_list_employees_Test() throws Exception {

        //given...
        List<Employee> employees = LongStream
                .rangeClosed(1, 2)
                .boxed()
                .map(idx -> Employee.builder()
                        .id(idx)
                        .firstname("Firstname " + idx)
                        .initials("Initials " + idx)
                        .surname("Surname " + idx)
                        .fired(true)
                        .build())
                .collect(Collectors.toList());


        //when...
        employeeService.update(employees);


        //then...
        List<Employee> result = employeeService.find(Arrays.asList(1L, 2L));

        Assert.assertEquals(result, employees);
    }
}
