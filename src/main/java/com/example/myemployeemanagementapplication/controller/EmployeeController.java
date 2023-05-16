package com.example.myemployeemanagementapplication.controller;

import com.example.myemployeemanagementapplication.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.myemployeemanagementapplication.service.EmployeeService;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    public EmployeeService employeeService;
    // display list of employee


    @GetMapping("/")
    public String viewHomePage(Model model){
        return findPaginated(1,  "firstName", "asc", model);
    }
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model){
        // create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee ){
        // save employee to database
        employeeService.saveEmployee(employee);
        return "redirect:/";

    }
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable (value = "id") long id, Model model){

        // get employee from the service
        Employee employee = employeeService.getEmployeeById(id);
        // set employee as a model attribute  to  pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id, Model model){

        // call delete employee method
        this.employeeService.deleteEmployeeById(id);

        return "redirect:/";
    }

    //page/1?sortField-name&sortDir=asc
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam ("sortField") String sortField,
                                @RequestParam ("sortDir") String sortDir,
                                Model model
    ){
        int pageSize = 5;
        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> listEmployee = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc" : "asc");

        model.addAttribute("listEmployees", listEmployee);
        return "index";

    }
}
