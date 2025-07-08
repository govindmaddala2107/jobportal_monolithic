package com.gomad.jobportal_monolithic.employee.employeeImpl;

import com.gomad.jobportal_monolithic.employee.Employee;
import com.gomad.jobportal_monolithic.employee.EmployeeRepo;
import com.gomad.jobportal_monolithic.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor   // generates the constructor for final fields
@Transactional              // all public methods join the same transaction
public class EmployeeImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public boolean deleteEmployee(Long id) {
        if(!employeeRepo.existsById(id)) {
            return false;
        }
        employeeRepo.deleteById(id);
        return true;
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        return employeeRepo.findById(id).map(dbEmp -> {
            dbEmp.setBirthDate(employee.getBirthDate());
            dbEmp.setGender(employee.getGender());
            dbEmp.setFirstName(employee.getFirstName());
            dbEmp.setLastName(employee.getLastName());
            dbEmp.setHireDate(employee.getHireDate());
            dbEmp.setPassword(employee.getPassword());
            return employeeRepo.save(dbEmp);
        }).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    @Override
    public Long totalEmployees(){
        return employeeRepo.count();
    }
}
