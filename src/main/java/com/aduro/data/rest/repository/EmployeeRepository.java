package com.aduro.data.rest.repository;

import com.aduro.data.rest.model.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Integer> {
}
