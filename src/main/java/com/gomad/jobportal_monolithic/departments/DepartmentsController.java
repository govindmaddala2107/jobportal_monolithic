package com.gomad.jobportal_monolithic.departments;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
public class DepartmentsController {
	
	private DepartmentsService departmentService;
	
	public DepartmentsController(DepartmentsService departmentService) {
		super();
		this.departmentService = departmentService;
	}

	@GetMapping()
	public ResponseEntity<List<Departments>> findAll(){
		return ResponseEntity.ok(departmentService.findAll());
	}
	
	@PostMapping("/create")
	public ResponseEntity<Boolean> createDepartment(@RequestBody Departments department){
		Boolean isCreated = departmentService.createDepartment(department);
		HttpStatus status = isCreated == true ? HttpStatus.CREATED: HttpStatus.FOUND;
		return new ResponseEntity<Boolean>(isCreated, status);
	}
	
	@DeleteMapping("/delete/{dept_id}")
	public ResponseEntity<Boolean> deleteDepartment(@PathVariable String dept_id){
		Boolean isDeleted = departmentService.deleteDepartment(dept_id);
		HttpStatus status = isDeleted == true ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Boolean>(isDeleted, status);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Boolean> updateDepartment(@RequestParam("dept_id") String dept_id, @RequestBody Departments department){
		Boolean isUpdated = departmentService.updateDepartment(dept_id, department);
		HttpStatus status = isUpdated == true ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Boolean>(isUpdated, status);
	}
}
