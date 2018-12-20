package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.Employee;

import java.util.List;

/**
 * Created by Faust on 1/28/2018.
 */
public interface EmployeeService {
    Employee saveEmployee(Employee e);

    Employee findById(String employeeId);

    void deleteById(String employeeId);

    void updateEmployee(Employee e);

    boolean employeeExists(Employee e);

    List<Employee> findAll();

    void deleteAll();
}
