package spring.di.demo.service;

import spring.di.demo.dao.EmployeeDao;
import spring.di.demo.domain.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public void update(List<Employee> employees) {
        employeeDao.update(employees);
    }

    @Override
    public List<Employee> find(List<Long> employeeIds) {
        return employeeDao.find(employeeIds);
    }

    @Override
    public List<Employee> find(String firstname, String initials, String surname) {
        return employeeDao.find(firstname, initials, surname);
    }

    @Override
    public List<Employee> find(boolean fired) {
        return employeeDao.find(fired);
    }

    @Override
    public Employee find(Long id) {
        return employeeDao.find(id);
    }

    @Override
    public void insert(Employee employee) {
        employeeDao.insert(employee);
    }

    @Override
    public void update(Employee employee) {
        employeeDao.update(employee);
    }

    @Override
    public void delete(Long id) {
        employeeDao.delete(id);
    }
}
