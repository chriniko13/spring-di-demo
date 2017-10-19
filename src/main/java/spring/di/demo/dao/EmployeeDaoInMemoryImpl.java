package spring.di.demo.dao;

import spring.di.demo.domain.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmployeeDaoInMemoryImpl implements EmployeeDao {

    private static final AtomicLong ID_GENERATOR_SEQUENCER = new AtomicLong();

    private static final Map<Long, Employee> EMPLOYEES_DB = new ConcurrentHashMap<>();

    private static final int END_INCLUSIVE = 10;

    static {

        final Random random = new Random();

        // populate the db with random entries...
        IntStream.rangeClosed(1, END_INCLUSIVE).forEach(idx -> {

            Employee employee = Employee.builder()
                    .id(ID_GENERATOR_SEQUENCER.incrementAndGet())
                    .firstname("Firstname " + idx)
                    .initials("Initials " + idx)
                    .surname("Surname " + idx)
                    .fired(random.nextInt(2) == 0 ? false : true)
                    .build();

            EMPLOYEES_DB.put(employee.getId(), employee);
        });

    }

    public void update(List<Employee> employees) {
        employees.forEach(this::update);
    }

    public List<Employee> find(List<Long> employeeIds) {
        return employeeIds
                .stream()
                .map(empId -> {
                    return EMPLOYEES_DB.entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().equals(empId))
                            .map(Map.Entry::getValue)
                            .collect(Collectors.toList());

                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Employee> find(String firstname, String initials, String surname) {

        final String fullname = produceFullname(Employee.builder().firstname(firstname).initials(initials).surname(surname).build());

        return EMPLOYEES_DB.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(record -> {
                    final String recordFullname = produceFullname(record);
                    return recordFullname.equals(fullname);
                })
                .collect(Collectors.toList());

    }

    public List<Employee> find(boolean fired) {
        return EMPLOYEES_DB.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(employee -> employee.getFired().equals(fired))
                .collect(Collectors.toList());
    }

    @Override
    public List<Employee> findAll() {
        return EMPLOYEES_DB.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public Employee find(Long id) {
        return EMPLOYEES_DB.get(id);
    }

    public void insert(Employee employee) {
        employee.setId(ID_GENERATOR_SEQUENCER.incrementAndGet());
        EMPLOYEES_DB.put(employee.getId(), employee);
    }

    public void update(Employee employee) {
        EMPLOYEES_DB.put(employee.getId(), employee);
    }

    public void delete(Long id) {
        EMPLOYEES_DB.remove(id);
    }

    private String produceFullname(Employee employee) {
        return nullToNone(employee.getFirstname()) + " " + nullToNone(employee.getInitials()) + " " + nullToNone(employee.getSurname());
    }

    private String nullToNone(String firstname) {
        return Optional.ofNullable(firstname).orElse("");
    }

}
