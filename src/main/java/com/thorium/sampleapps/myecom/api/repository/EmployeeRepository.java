package com.thorium.sampleapps.myecom.api.repository;

import com.thorium.sampleapps.myecom.api.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Faust on 1/28/2018.
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
