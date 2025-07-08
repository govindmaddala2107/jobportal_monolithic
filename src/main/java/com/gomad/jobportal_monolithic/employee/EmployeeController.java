package com.gomad.jobportal_monolithic.employee;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.gomad.jobportal_monolithic.employee.dto.EmployeeListDTO;
import com.gomad.jobportal_monolithic.employee.dto.EmployeeProfileDTO;
import com.gomad.jobportal_monolithic.employee.mapper.EmployeeMapper;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeMapper empMapper;

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> findAll(){
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteEmployee(@RequestParam(value = "id") Long id){
        return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestParam(value = "id") Long id, @RequestBody Employee employee){
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Optional<Employee>> findById(@RequestParam(value = "id") Long id){
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Employee>> findAllPaginated(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        Page<Employee> employeePage = employeeService.findAll(pageable);
        return ResponseEntity.ok(employeePage);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> totalEmployees(){
        return ResponseEntity.ok(employeeService.totalEmployees());
    }

    @GetMapping("/list")
    public ResponseEntity<Page<EmployeeListDTO>> findEmployeesForList(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<Employee> page = employeeService.findAll(pageable);
        return ResponseEntity.ok(page.map(empMapper::toListDTO));
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<EmployeeProfileDTO> getProfile(@PathVariable Long id) {
        Employee emp = employeeService.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        return ResponseEntity.ok(empMapper.toProfileDto(emp));
    }
}
