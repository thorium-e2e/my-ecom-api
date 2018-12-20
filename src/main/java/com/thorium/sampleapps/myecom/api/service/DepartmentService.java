package com.thorium.sampleapps.myecom.api.service;

import com.thorium.sampleapps.myecom.api.domain.Department;

import java.util.List;

/**
 * Created by Faust on 1/28/2018.
 */
public interface DepartmentService {

    Department saveDepartment(Department d);

    Department findById(String departmentId);

    void deleteById(String departmentId);

    void updateDepartment(Department d);

    boolean departmentExists(Department d);

    List<Department> findAll();

    void deleteAll();
}
