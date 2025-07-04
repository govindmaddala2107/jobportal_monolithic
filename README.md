# Springboot project setup:
## Packages needed:
- Spring web
- Devtools
- Jpa
- Postgres
- Actuator
## Database setup:
- In **application.properties**, add the following lines to integrate postgres database into the project.
  ```
  spring.datasource.url=jdbc:postgresql://localhost:5432/<DATABASE_NAME>
  spring.datasource.username=postgres
  spring.datasource.password=2107
  spring.jpa.database=POSTGRESQL
  spring.jpa.show-sql=true
  spring.jpa.hibernate.ddl-auto=create-drop
  spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
  ```

## Creation of Service [Departments]
### Creation of Entity [Class]:
- Entity basically represent the table. Here it is **Departments** and it has **dept_id (string)** and **dept_name(string)**.
- It needs **@Entity** annotation.
  ```
  package com.gomad.jobportal_monolithic.departments;
  import jakarta.persistence.Entity;
  import jakarta.persistence.Id;

  @Entity
  public class Departments {
    @Id
    private String dept_no;
    private String dept_name;
  }
  ```
  - generate 
    - empty constructor & parameterised constructor.
    - getters & setters.
    - toString method.
  - If table name & class name are different, we have mention in Entity like **@Entity(name = "ACTUAL_TABLE_NAME")**
  - If column name and here varibale name are different, we have to give like
    - **@Column(name = "ACTUAL_COLUMN_NAME")** 
    - else no need of mentioning of **@Column** also.
  - If we want to generate the **Id** dynamically, below **@Id**, we have write like **@GeneratedValue(strategy = GenerationType.IDENTITY)**.

### Creation of Service [Interface]:
- It needs **no** annotation.
- Create a service interface with methods what we want to implement.
  ```
  package com.gomad.jobportal_monolithic.departments;

  import java.util.List;

  public interface DepartmentsService {
    List<Departments> findAll();
    boolean createDepartment(Departments department);
    boolean deleteDepartment(String dept_id);
    boolean updateDepartment(String dept_id, Departments department);
  }
  ```

### Creation of ServiceRepo[Interface]:
- It needs **no** annotation.
- It extending JpaRepository.
- It gives all the dtos of Jpa.
  ```
  package com.gomad.jobportal_monolithic.departments;

  import org.springframework.data.jpa.repository.JpaRepository;

  public interface DepartmentsRepo extends JpaRepository<Departments, String> {
  }
  ```
  - JpaRepository takes 2 parameters:
    - Entity Class and
    - Datatype of Id of Entity.

### Creation of Implementation of Service[Class]
- It needs **@Service** annotation.
- It implements Service interface.
  ```
  package com.gomad.jobportal_monolithic.departments.DepartmentImpl;

  import java.util.List;
  import java.util.Optional;

  import org.springframework.stereotype.Service;

  import com.gomad.jobportal_monolithic.departments.Departments;
  import com.gomad.jobportal_monolithic.departments.DepartmentsRepo;
  import com.gomad.jobportal_monolithic.departments.DepartmentsService;


  @Service
  public class DepartmentsImplimentation implements DepartmentsService {
    
    // Bean Creation
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
  ```
  - Here we create bean of DepartmentsRepo.
  - **findAll** returns List of Departments.
  - **findById** may returns department with corresponding id if exists else return null. It can be handled by 2 ways.
    - By Optional way:
      - Optional\<Department\> dept = departmentsRepo.findById(dept_id);
        - dept.isAbsent()  => returns **true** if dept not exists.
        - dept.isPresent() => returns **true** if dept does exists.
        - dept.get() => returns **department** details.
  - **save()**:
    - It is to save the entity.
    - Also to update and save the entity.
      - Once after we get the entity and set the data and use .save to save the entity.
  - **deleteById**
    - It is to delete the entity by id.

### Creation of Controller[Class]
- It needs **@RestController** & **@RequestMapping("/departments")** annotation.
  ```
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
  ```
  - Here we create bean of **DepartmentService** using constructor based.
  - @RestController annotation says it is a restful service.
  - @Requestmapping annotation helps in setting **command end point**.
  - @Getmapping = for GET menthod
  - @Postmapping = for POST menthod
  - @Putmapping = for PUT menthod
  - @Deletemapping = for DELETE menthod
  - @RequestBody annotation is used to access the **request body**.
  - @PathVariable annotation is used to access the path variable.
    - URL: /departments/get/123
    - @GetMapping("get/{id}")
    - Using **void methondName(@PathVaribale int id)**, we can access that id.
  - @RequestParam is used to access the **query params**
    - URL: /departments/get?id=123
    - @GetMapping("/get")
    - Using **void methodName(@RequestParam("id") int id)**, we can access that query param.
  - We can send response back as String, boolean and List and in other types but **ResponseEntity** can be used to send in standard format i.e with status code and other messages.
  - In method name, as return type keep like **ResponseEntity\<DataType\>**
  - In return, we can send it in 2 type:
    - ResponseEntity.ok(DataWhatWeWantToSend)
    - As Object: new ResponseEntity(DataWhatWeWantToSend, StatusCode)
      - StatusCode can be send using HttpStatus.
      - HttpStatus gives some standard things like "OK", "Created" and if we use those, corresponding status codes will be send.
## MISC:
- Data dump
  - pg_dump -U postgres -h localhost -d Employees -F c -f Employees.dump
  - createdb -U postgres -h localhost gomadcompany
  - pg_restore -U postgres -h localhost -d gomadcompany Employees.dump
