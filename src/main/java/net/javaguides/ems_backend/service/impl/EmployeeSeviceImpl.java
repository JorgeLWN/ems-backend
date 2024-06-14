package net.javaguides.ems_backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems_backend.EmployeeMapper.EmployeeMapper;
import net.javaguides.ems_backend.dto.EmployeeDto;
import net.javaguides.ems_backend.entity.Employee;
import net.javaguides.ems_backend.exception.ResourceNotFoundException;
import net.javaguides.ems_backend.repository.EmployeeRepository;
import net.javaguides.ems_backend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeSeviceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
       Employee employee =  employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Empleado no se puede obeneter por Id:" + employeeId));

       return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployess() {
      List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee) -> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployee) {
     Employee employee =  employeeRepository.findById(employeeId).orElseThrow(
               ()-> new ResourceNotFoundException("Empleado no se puede obeneter por Id: " + employeeId)
       );
     employee.setFirstname(updateEmployee.getFirstname());
     employee.setLastname(updateEmployee.getLastname());
     employee.setEmail(updateEmployee.getEmail());

     Employee updateEmployeeObj = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(updateEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee =  employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("Empleado no se puede obeneter por Id: " + employeeId)
        );

        employeeRepository.deleteById(employeeId);
    }
}
