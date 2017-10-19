package spring.di.demo.dao;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import spring.di.demo.domain.Employee;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class EmployeeDaoInMemoryImplTest {

    private EmployeeDao employeeDao;

    @Before
    public void setUp() throws Exception {
        employeeDao = new EmployeeDaoInMemoryImpl();


        Method initializeMethod = EmployeeDaoInMemoryImpl.class.getDeclaredMethod("initialize");
        initializeMethod.setAccessible(true);
        initializeMethod.invoke(employeeDao);
    }

    @Test
    public void update() throws Exception {

        //given...
        List<Employee> employeesToUpdate = LongStream.rangeClosed(1L, 5L)
                .boxed()
                .map(idx -> Employee.builder()
                        .id(idx)
                        .firstname("Firstname [UPDATED]" + idx)
                        .initials("Initials [UPDATED]" + idx)
                        .surname("Surname [UPDATED]" + idx)
                        .fired(false)
                        .build())
                .collect(Collectors.toList());


        //when...
        employeeDao.update(employeesToUpdate);


        //then...
        List<Employee> employees = employeeDao.find(LongStream.rangeClosed(1, 5).boxed().collect(Collectors.toList()));
        for (Employee employee : employees) {

            assertThat(employee.getFirstname(), Matchers.containsString("[UPDATED]"));
            assertThat(employee.getInitials(), Matchers.containsString("[UPDATED]"));
            assertThat(employee.getSurname(), Matchers.containsString("[UPDATED]"));

        }

    }

    @Test
    public void find_list_of_long_ids() throws Exception {

        //given...
        List<Long> idsToSearchFor = Arrays.asList(1L, 2L);

        //when...
        List<Employee> result = employeeDao.find(idsToSearchFor);

        //then...
        assertThat(2, is(result.size()));

        assertThat("Firstname 1", equalTo(result.get(0).getFirstname()));
        assertThat("Firstname 2", equalTo(result.get(1).getFirstname()));

    }

    @Test
    public void find_based_on_firstname_initials_surname() throws Exception {

        //when...
        List<Employee> result = employeeDao.find("Firstname 1", "Initials 1", "Surname 1");


        //then...
        assertThat(1, is(result.size()));
        assertThat("Firstname 1", equalTo(result.get(0).getFirstname()));
    }

    @Test
    public void find_based_on_fired_attribute() throws Exception {

        //given...
        List<Employee> firedEmployees = employeeDao
                .findAll()
                .stream()
                .filter(Employee::getFired).collect(Collectors.toList());

        //when...
        List<Employee> result = employeeDao.find(true);


        //then...
        assertThat(firedEmployees.size(), is(result.size()));
        assertEquals(firedEmployees, result);
    }

    @Test
    public void find_based_on_id_attribute() throws Exception {

        //when...
        Employee employee = employeeDao.find(1L);


        //then...
        assertNotNull(employee);
        assertThat("Firstname 1", is(employee.getFirstname()));
    }

    @Test
    public void findAll() throws Exception {

        //given...
        Field endInclusive = EmployeeDaoInMemoryImpl.class.getDeclaredField("END_INCLUSIVE");
        endInclusive.setAccessible(true);
        int endEnclusive = (int) endInclusive.get(employeeDao);

        //when...
        List<Employee> result = employeeDao.findAll();


        //then...
        assertEquals(endEnclusive, result.size());
    }

    @Test
    public void insert() throws Exception {

        //given...
        Employee employee = Employee
                .builder()
                .firstname("Chri")
                .surname("Niko")
                .fired(false)
                .build();

        //when...
        employeeDao.insert(employee);

        //then...
        List<Employee> results = employeeDao.find("Chri", null, "Niko");

        assertThat(results.size(), is(1));
        assertEquals(employee, results.get(0));

    }

    @Test
    public void update_employee_record() throws Exception {

        //given...
        Employee employee = Employee
                .builder()
                .id(1L)
                .firstname("Chri")
                .surname("Niko")
                .fired(false)
                .build();

        //when...
        employeeDao.update(employee);


        //then...
        Employee result = employeeDao.find(1L);

        assertEquals(employee, result);

    }

    @Test
    public void delete() throws Exception {

        //when...
        employeeDao.delete(1L);


        //then...
        Employee employee = employeeDao.find(1L);
        assertNull(employee);
    }

}