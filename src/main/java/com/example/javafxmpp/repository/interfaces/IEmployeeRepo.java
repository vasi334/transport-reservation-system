package com.example.javafxmpp.repository.interfaces;

import com.example.javafxmpp.model.Employee;

public interface IEmployeeRepo extends Repository<Employee, String>{

    Employee findEmployee(String email, String password);
}
