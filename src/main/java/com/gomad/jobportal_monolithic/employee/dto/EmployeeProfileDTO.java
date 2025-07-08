package com.gomad.jobportal_monolithic.employee.dto;

import java.time.LocalDate;

import com.gomad.jobportal_monolithic.employee.Gender;

import lombok.Data;

@Data
public class EmployeeProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthDate;
    private LocalDate hireDate;
    private String password;
}
