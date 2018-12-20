package com.thorium.sampleapps.myecom.api.service.impl;

import com.thorium.sampleapps.myecom.api.domain.Department;
import com.thorium.sampleapps.myecom.api.repository.DepartmentRepository;
import com.thorium.sampleapps.myecom.api.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Faust on 1/28/2018.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department d) {
        return departmentRepository.save(d);
    }

    @Override
    public Department findById(String departmentId) {
        return departmentRepository.findOne(departmentId);
    }

    @Override
    public void deleteById(String departmentId) {
        departmentRepository.delete(departmentId);
    }

    @Override
    public void updateDepartment(Department d) {
        departmentRepository.save(d);
    }

    @Override
    public boolean departmentExists(Department d) {
        return departmentRepository.exists(Example.of(d));
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public void deleteAll() {
        departmentRepository.deleteAll();
    }
}
