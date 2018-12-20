package com.thorium.sampleapps.myecom.api.controller;

import com.thorium.sampleapps.myecom.api.domain.Department;
import com.thorium.sampleapps.myecom.api.domain.Employee;
import com.thorium.sampleapps.myecom.api.service.DepartmentService;
import com.thorium.sampleapps.myecom.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Faust on 1/29/2018.
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.findAll();
        if (departments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            departments.forEach(d -> d.add(linkTo(methodOn(DepartmentController.class).getAllDepartments()).withRel("departments")));
            departments.forEach(d -> d.add(linkTo(methodOn(DepartmentController.class).getDepartmentById(d.getDepartmentId())).withSelfRel()));
            departments.forEach(d -> d.getEmployees().forEach(e -> {
                e.add(linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
                e.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(e.getEmployeeId())).withSelfRel());
            }));
            return new ResponseEntity<>(departments, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<Department> getDepartmentById(@PathVariable("id") String departmentId) {
        Department byDepartmentId = departmentService.findById(departmentId);
        if (byDepartmentId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            byDepartmentId.add(linkTo(methodOn(DepartmentController.class).getAllDepartments()).withRel("departments"));
            byDepartmentId.add(linkTo(methodOn(DepartmentController.class).getDepartmentById(byDepartmentId.getDepartmentId())).withSelfRel());
            byDepartmentId.getEmployees().forEach(e -> {
                e.add(linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
                e.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(e.getEmployeeId())).withSelfRel());
            });
            return new ResponseEntity<>(byDepartmentId, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public HttpEntity<?> saveDepartment(@RequestBody Department d) {
        if (departmentService.departmentExists(d)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Department department = departmentService.saveDepartment(d);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/departments/department/{id}")
                    .buildAndExpand(department.getDepartmentId()).toUri();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);
            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public HttpEntity<?> updateDepartment(@PathVariable("id") String id, @RequestBody Department d) {
        Department byDepartmentId = departmentService.findById(id);
        if (byDepartmentId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            byDepartmentId.setName(d.getName());
            byDepartmentId.setDescription(d.getDescription());
            List<Employee> employees = new ArrayList<>();
            byDepartmentId.getEmployees().forEach(employee -> employees.add(employeeService.findById(employee.getEmployeeId())));
            byDepartmentId.setEmployees(employees);
            departmentService.updateDepartment(byDepartmentId);
            byDepartmentId.add(linkTo(methodOn(DepartmentController.class).getAllDepartments()).withRel("departments"));
            byDepartmentId.add(linkTo(methodOn(DepartmentController.class).getDepartmentById(byDepartmentId.getDepartmentId())).withSelfRel());
            byDepartmentId.getEmployees().forEach(e -> {
                e.add(linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees"));
                e.add(linkTo(methodOn(EmployeeController.class).getEmployeeById(e.getEmployeeId())).withSelfRel());
            });
            return new ResponseEntity<>(departmentService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") String departmentId) {
        departmentService.deleteById(departmentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        departmentService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
