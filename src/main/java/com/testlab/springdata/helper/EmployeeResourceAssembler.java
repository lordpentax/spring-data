package com.testlab.springdata.helper;

import com.testlab.springdata.controller.EmployeeController;
import com.testlab.springdata.domain.employee.Employee;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {
    @Override
    public Resource<Employee> toResource(Employee employee) {
        return new Resource<>(employee,
                linkTo(methodOn(EmployeeController.class).getOneEmployee(employee.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
    }
}
