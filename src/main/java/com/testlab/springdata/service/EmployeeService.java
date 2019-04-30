package com.testlab.springdata.service;

import com.testlab.springdata.domain.employee.Employee;
import com.testlab.springdata.exception.EmployeeNotFoundException;
import com.testlab.springdata.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> employees() {
        log.info("A list of Employee");
        return employeeRepository.findAll();
    }

    public Employee createEmployee(final Employee employee) {
        log.info("An Employee has been created", employee);
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(final Long id) {
        log.info("Employee wiht the id {} has been fetch", id);
       return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee updateEmployee(final Long id, final Employee employee) {
        log.info("Employee wiht the id {} has been updated", id);
        return employeeRepository.findById(id)
                .map(emp -> {
                    emp.setName(employee.getName());
                    emp.setRole(employee.getRole());
                    return employeeRepository.save(emp);
                }).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public void deleteEmployee(final Long id) {
        log.info("Employee wiht the id {} has been deleted", id);
        employeeRepository.deleteById(id);
    }
}
