package com.gomad.jobportal_monolithic.employee.mapper;

import org.springframework.stereotype.Component;

import com.gomad.jobportal_monolithic.employee.Employee;
import com.gomad.jobportal_monolithic.employee.dto.EmployeeListDTO;
import com.gomad.jobportal_monolithic.employee.dto.EmployeeProfileDTO;

@Component
public class EmployeeMapper {

    public EmployeeListDTO toListDTO(Employee emp){
        EmployeeListDTO dto = new EmployeeListDTO();
        dto.setId(emp.getId());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        return dto;
    }

    public EmployeeProfileDTO toProfileDto(Employee emp) {
        EmployeeProfileDTO dto = new EmployeeProfileDTO();
        dto.setId(emp.getId());
        dto.setFirstName(emp.getFirstName());
        dto.setLastName(emp.getLastName());
        dto.setGender(emp.getGender());
        dto.setPassword(emp.getPassword()); // use only if securely stored
        dto.setHireDate(emp.getHireDate());
        dto.setBirthDate(emp.getBirthDate());
        return dto;
    }

    
    
}
