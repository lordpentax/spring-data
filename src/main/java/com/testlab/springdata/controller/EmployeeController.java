package com.testlab.springdata.controller;

import com.testlab.springdata.domain.employee.Employee;
import com.testlab.springdata.helper.EmployeeResourceAssembler;
import com.testlab.springdata.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeResourceAssembler employeeResourceAssembler;

    @Autowired
    public EmployeeController(final EmployeeService employeeService, EmployeeResourceAssembler employeeResourceAssembler) {
        this.employeeService = employeeService;
        this.employeeResourceAssembler = employeeResourceAssembler;
    }


    @GetMapping("/employees")
    public Resource getAllEmployees() {
        return new Resource(employeeService.employees().stream()
                .map(employee -> new Resource<>(employeeResourceAssembler.toResource(employee))));
    }

    @PostMapping("/employees")
    public Resource<Employee> createEmployee(@Valid @RequestBody final Employee employee) {

        return new Resource<>(employeeService.createEmployee(employee),
                linkTo(methodOn(EmployeeController.class).getOneEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }

    @GetMapping("/employees/{id}/employee")
    public Resource<Employee> getOneEmployee(@PathVariable final Long id) {


        return new Resource(employeeResourceAssembler.toResource(employeeService.getEmployee(id)));
    }

    @PutMapping("/employees/{id}/employee")
    public Resource<Employee> updateEmployee(@PathVariable final Long id, @RequestBody Employee employee) {

        return new Resource<>(employeeService.updateEmployee(id, employee),
                linkTo(methodOn(EmployeeController.class).updateEmployee(id, employee)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }

    @DeleteMapping("/employees/{id}/employee")
    public Resource<org.springframework.hateoas.Link> deleteEmployee(@PathVariable final Long id) {
        employeeService.deleteEmployee(id);
        return new Resource<>(linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }
}
