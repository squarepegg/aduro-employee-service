package com.aduro.configuration;

import com.aduro.data.rest.model.Employee;
import com.aduro.data.rest.repository.EmployeeRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Profile("dev")
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "dev-configuration")
public class DevelopmentConfiguration {

    private final EmployeeRepository employeeRepository;

    private List<Employee> employees = new ArrayList<>();

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public DevelopmentConfiguration(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void addEmployees() {
        for (Employee employee: employees) {
            employeeRepository.save(employee);
        }
        employeeRepository.findAll().forEach(employee -> System.out.println(employee.getName()));
    }
}
