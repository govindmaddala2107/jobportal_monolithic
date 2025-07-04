package com.gomad.jobportal_monolithic.employee.mapper;

import com.gomad.jobportal_monolithic.employee.Employee;
import com.gomad.jobportal_monolithic.employee.dto.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmpMapper {
    public EmployeeDTO toDto(Employee emp) {
        return new EmployeeDTO(
                emp.getId(),
                emp.getFirstName(),
                emp.getLastName(),
                emp.getGender(),
                emp.getBirthDate(),
                emp.getHireDate()
        );
    }
}
