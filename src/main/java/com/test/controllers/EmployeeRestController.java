package com.test.controllers;

import com.test.dto.EmployeeDTO;
import com.test.service.EmployeeService;

import views.CsvView;
import views.ExcelView;
import views.PdfView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<EmployeeDTO> list() {
        return employeeService.getEmployees();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public boolean updateEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.updateEmployee(employee);
    }

    @RequestMapping(method = RequestMethod.POST)
    public boolean saveEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.saveEmployee(employee);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    public boolean deleteEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }
    
    @RequestMapping(value = "/downloadPdf", method = RequestMethod.GET)
    public ModelAndView downloadPdf() {
    	 Map<String, Object> model = new HashMap<String, Object>();
    	 model.put("employee", employeeService.getEmployees());
        return new ModelAndView(new PdfView(), model);
    }
    
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.GET)
    public ModelAndView downloadExcel() {
    	 Map<String, Object> model = new HashMap<String, Object>();
    	 model.put("employee", employeeService.getEmployees());
        return new ModelAndView(new ExcelView(), model);
    }
    
    @RequestMapping(value = "/downloadCsv", method = RequestMethod.GET)
    public ModelAndView downloadCsv() {
    	 Map<String, Object> model = new HashMap<String, Object>();
    	 model.put("employee", employeeService.getEmployees());
        return new ModelAndView(new CsvView(), model);
    }

}
