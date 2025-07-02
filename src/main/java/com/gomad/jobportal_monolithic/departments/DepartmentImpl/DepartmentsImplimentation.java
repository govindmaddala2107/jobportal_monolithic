package com.gomad.jobportal_monolithic.departments.DepartmentImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomad.jobportal_monolithic.departments.Departments;
import com.gomad.jobportal_monolithic.departments.DepartmentsRepo;
import com.gomad.jobportal_monolithic.departments.DepartmentsService;


@Service
public class DepartmentsImplimentation implements DepartmentsService {
	
	private DepartmentsRepo departmentRepo;
	
	public DepartmentsImplimentation(DepartmentsRepo departmentRepo) {
		super();
		this.departmentRepo = departmentRepo;
	}
	

	@Override
	public List<Departments> findAll() {
		return departmentRepo.findAll();
	}

	@Override
	public boolean createDepartment(Departments department) {
		Departments dept = departmentRepo.findById(department.getDept_no()).orElse(null);
		if(dept != null) {
			return false;
		}
		departmentRepo.save(department);
		return true;
	}

	@Override
	public boolean deleteDepartment(String dept_id) {
		Departments dept = departmentRepo.findById(dept_id).orElse(null);
		if(dept == null) {
			return false;
		}
		departmentRepo.deleteById(dept_id);
		return true;
	}

	@Override
	public boolean updateDepartment(String dept_id, Departments department) {
		Optional<Departments> dept = departmentRepo.findById(dept_id);
		if(dept.isEmpty()) return false;
		Departments updatedDept = dept.get();
		updatedDept.setDept_name(department.getDept_name());	
		departmentRepo.save(updatedDept);
		return true;
	}

}
