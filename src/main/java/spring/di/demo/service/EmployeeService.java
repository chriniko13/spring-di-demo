package spring.di.demo.service;

import spring.di.demo.domain.Employee;

import java.util.List;

public interface EmployeeService {

    void update(List<Employee> employees);

    List<Employee> findAll();

    List<Employee> find(List<Long> employeeIds);

    List<Employee> find(String firstname, String initials, String surname);

    List<Employee> find(boolean fired);

    Employee find(Long id);

    void insert(Employee employee);

    void update(Employee employee);

    void delete(Long id);
}
