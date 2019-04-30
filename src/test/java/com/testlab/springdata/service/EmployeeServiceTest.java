package com.testlab.springdata.service;

import com.testlab.springdata.domain.employee.Employee;
import com.testlab.springdata.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.testlab.springdata.domain.employee.Employee.EmployeeBuilder.anEmployee;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreatAUser() {
        //Given
        createEmployee();
        //WHEN
        when(employeeRepository.save(any(Employee.class))).thenReturn(new Employee());

        //THEN
        assertThat(employeeService.createEmployee(createEmployee())).isNotNull();
        verify(employeeRepository).save(isA(Employee.class));

    }

    private static Employee createEmployee() {

        return anEmployee()
                .withId(35L)
                .withName("Cristian Fernandez")
                .withRole("Admin")
                .build();
    }
}