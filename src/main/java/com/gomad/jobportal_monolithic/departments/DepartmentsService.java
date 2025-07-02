package com.gomad.jobportal_monolithic.departments;

import java.util.List;

public interface DepartmentsService {
	List<Departments> findAll();
	boolean createDepartment(Departments department);
	boolean deleteDepartment(String dept_id);
	boolean updateDepartment(String dept_id, Departments department);
}
