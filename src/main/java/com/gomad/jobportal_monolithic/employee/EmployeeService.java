package com.gomad.jobportal_monolithic.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> findAll();
    Employee createEmployee(Employee employee);
    boolean deleteEmployee(Long id);
    Employee updateEmployee(Long id, Employee employee);
    Optional<Employee> findById(Long id);
    Page<Employee> findAll(Pageable pageable);
    Long totalEmployees();
}
