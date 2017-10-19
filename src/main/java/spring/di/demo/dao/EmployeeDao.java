package spring.di.demo.dao;

import spring.di.demo.domain.Employee;

import java.util.List;

public interface EmployeeDao extends GenericDao<Long, Employee> {

    void update(List<Employee> employees);

    List<Employee> find(List<Long> employeeIds);

    List<Employee> find(String firstname, String initials, String surname);

    List<Employee> find(boolean fired);

}
