package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.Employee;
import com.thorium.sampleapps.myecom.api.repository.EmployeeRepository;
import com.thorium.sampleapps.myecom.api.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Faust on 1/28/2018.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee e) {
        return employeeRepository.save(e);
    }

    @Override
    public Employee findById(String employeeId) {
        return employeeRepository.findOne(employeeId);
    }

    @Override
    public void deleteById(String employeeId) {
        employeeRepository.delete(employeeId);
    }

    @Override
    public void updateEmployee(Employee e) {
        employeeRepository.save(e);
    }

    @Override
    public boolean employeeExists(Employee e) {
        return employeeRepository.exists(Example.of(e));
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
