package com.example.myemployeemanagementapplication.service;

import com.example.myemployeemanagementapplication.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;


public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(long id);
    void deleteEmployeeById(long id);
    // method for pagignation
    Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

}
