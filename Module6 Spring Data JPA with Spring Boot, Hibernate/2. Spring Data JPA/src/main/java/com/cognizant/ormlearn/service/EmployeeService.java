package com.cognizant.ormlearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;

import antlr.collections.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public Employee get(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }
    @Transactional(readOnly = true)
    public List getAllPermanentEmployees() {
        return (List) employeeRepository.getAllPermanentEmployees();
    }

    @Transactional(readOnly = true)
    public Double getAverageSalary(int deptId) {
        Double avgSalary = employeeRepository.getAverageSalary(deptId);
        return (avgSalary != null) ? avgSalary : 0.0;
    }

    @Transactional(readOnly = true)
    public List getAllEmployeesNative() {
        return (List) employeeRepository.getAllEmployeesNative();
    }
}