package com.example.javafxmpp.service;

import com.example.javafxmpp.model.Employee;
import com.example.javafxmpp.repository.EmployeeRepo;
import com.example.javafxmpp.repository.interfaces.IEmployeeRepo;

public class EmployeeService {

    private final IEmployeeRepo employeeRepo;

    public EmployeeService(IEmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public void saveEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    public void deleteEmployeeById(String id) {
        employeeRepo.delete(id);
    }

    public void deleteAllEmployees() {
        employeeRepo.deleteAll();
    }

    public boolean existsEmployeeById(String id) {
        return employeeRepo.existsById(id);
    }

    public Employee findEmployeeById(String id) {
        return employeeRepo.findById(id);
    }

    public Employee findEmployee(String email, String password){
        return employeeRepo.findEmployee(email, password);
    }

    public long countEmployees() {
        return employeeRepo.count();
    }
}
