package spring.di.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import spring.di.demo.dao.EmployeeDao;
import spring.di.demo.domain.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;

public class EmployeeServiceImplTest {

    private EmployeeDao employeeDao;
    private EmployeeService employeeService;

    @Before
    public void init() {
        employeeDao = Mockito.mock(EmployeeDao.class);
        employeeService = new EmployeeServiceImpl(employeeDao);
    }

    @Test
    public void findAll() throws Exception {

        //given...
        Employee employee = Employee.builder()
                .id(1L)
                .firstname("firstname")
                .initials("initials")
                .surname("surname")
                .fired(false)
                .build();

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        Mockito.when(employeeDao.findAll()).thenReturn(employees);


        //when...
        List<Employee> result = employeeService.findAll();


        //then...
        Mockito.verify(employeeDao, times(1)).findAll();
        assertThat(employees, is(result));

    }

    @Test
    public void update_Employee() throws Exception {

        //given...
        Employee employee = Employee.builder()
                .id(1L)
                .firstname("firstname")
                .initials("initials")
                .surname("surname")
                .fired(false)
                .build();

        //when...
        employeeService.update(employee);

        //then...
        Mockito.verify(employeeDao, times(1)).update(employee);

    }

    @Test
    public void find_by_list_of_longs() throws Exception {

        //given...
        List<Employee> employees = IntStream
                .rangeClosed(1, 10)
                .boxed()
                .map(idx -> {
                    return Employee.builder()
                            .id(1L)
                            .firstname("firstname " + idx)
                            .initials("initials " + idx)
                            .surname("surname " + idx)
                            .fired(false)
                            .build();

                })
                .collect(Collectors.toList());

        List<Long> idsToSearchFor = Arrays.asList(1L, 2L, 3L);

        Mockito.when(employeeDao.find(idsToSearchFor)).thenReturn(employees);

        //when...
        List<Employee> result = employeeService.find(idsToSearchFor);


        //then...
        Mockito.verify(employeeDao, times(1)).find(idsToSearchFor);
        assertThat(employees, is(result));

    }

    @Test
    public void find_by_firstname_initials_surname() throws Exception {

        //given...
        List<Employee> employees = IntStream
                .rangeClosed(1, 1)
                .boxed()
                .map(idx -> {
                    return Employee.builder()
                            .id(1L)
                            .firstname("firstname " + idx)
                            .initials("initials " + idx)
                            .surname("surname " + idx)
                            .fired(false)
                            .build();

                })
                .collect(Collectors.toList());

        String firstname = "firstname";
        String initials = "initials";
        String surname = "initials";

        Mockito.when(employeeDao.find(firstname, initials, surname)).thenReturn(employees);

        //when...
        List<Employee> result = employeeService.find(firstname, initials, surname);

        //then...
        Mockito.verify(employeeDao, times(1)).find(firstname, initials, surname);
        assertThat(employees, is(result));
    }

    @Test
    public void find_by_fired_attribute() throws Exception {
        //given...
        boolean fired = true;

        List<Employee> employees = IntStream
                .rangeClosed(1, 10)
                .boxed()
                .map(idx -> {
                    return Employee.builder()
                            .id(1L)
                            .firstname("firstname " + idx)
                            .initials("initials " + idx)
                            .surname("surname " + idx)
                            .fired(fired)
                            .build();

                })
                .collect(Collectors.toList());


        Mockito.when(employeeDao.find(fired)).thenReturn(employees);

        //when...
        List<Employee> result = employeeService.find(fired);

        //then...
        Mockito.verify(employeeDao, times(1)).find(fired);
        assertThat(employees, is(result));
    }

    @Test
    public void find_by_id_attribute() throws Exception {
        //given...
        Long id = 123L;

        Employee employee = Employee.builder()
                .id(id)
                .firstname("firstname")
                .initials("initials")
                .surname("surname")
                .fired(true)
                .build();


        Mockito.when(employeeDao.find(id)).thenReturn(employee);

        //when...
        Employee result = employeeService.find(id);

        //then...
        Mockito.verify(employeeDao, times(1)).find(id);
        assertThat(employee, is(result));
    }

    @Test
    public void insert_employee() throws Exception {
        //given...
        Long id = 123L;

        Employee employee = Employee.builder()
                .id(id)
                .firstname("firstname")
                .initials("initials")
                .surname("surname")
                .fired(true)
                .build();

        //when...
        employeeService.insert(employee);

        //then...
        Mockito.verify(employeeDao, times(1)).insert(employee);

    }

    @Test
    public void update_list_of_employees() throws Exception {

        //given...
        List<Employee> employees = IntStream
                .rangeClosed(1, 10)
                .boxed()
                .map(idx -> {
                    return Employee.builder()
                            .id(1L)
                            .firstname("firstname " + idx)
                            .initials("initials " + idx)
                            .surname("surname " + idx)
                            .fired(false)
                            .build();

                })
                .collect(Collectors.toList());


        //when...
        employeeService.update(employees);

        //then...
        Mockito.verify(employeeDao, times(1)).update(employees);
    }

    @Test
    public void delete_employee() throws Exception {
        //given...
        Long id = 123L;

        //when...
        employeeService.delete(id);

        //then...
        Mockito.verify(employeeDao, times(1)).delete(id);

    }

}