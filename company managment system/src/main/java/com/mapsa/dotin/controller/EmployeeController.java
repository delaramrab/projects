package com.mapsa.dotin.controller;

import com.mapsa.dotin.dto.LeaveDto;
import com.mapsa.dotin.model.email.Email;
import com.mapsa.dotin.model.leave.Leave;
import com.mapsa.dotin.model.leave.LeaveRequestCheck;
import com.mapsa.dotin.model.leave.LeaveType;
import com.mapsa.dotin.model.person.Employee;
import com.mapsa.dotin.model.person.EmployeeRole;
import com.mapsa.dotin.model.person.Person;
import com.mapsa.dotin.service.EmailService;
import com.mapsa.dotin.service.EmployeeService;
import com.mapsa.dotin.service.LeaveService;
import com.mapsa.dotin.service.ManagerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
@AllArgsConstructor
@Controller
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private LeaveService leaveService;
    private ManagerService managerService;
    private EmailService emailService;

    @GetMapping("/signup")
    public String getSignUp(Model model){
        model.addAttribute("employee",new Employee());
        model.addAttribute("employeeRoles", EmployeeRole.values());
        model.addAttribute("managers", managerService.getAll());
        return "employee_sign_up";
    }

    @PostMapping("signup")
    public String postSignUp(@Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employee",employee);
            model.addAttribute("employeeRoles", EmployeeRole.values());
            model.addAttribute("managers", managerService.getAll());
            return "employee_sign_up";
        }
        try {
            employeeService.save(employee);
            model.addAttribute("message", "Hi " + employee.getFirstName());
            return "redirect:/employee/employee_profile/" + employee.getUsername();
        }catch (Exception e){
            model.addAttribute("message", "There was an error signing up. Please try again.");
            model.addAttribute("employee",employee);
            model.addAttribute("employeeRoles", EmployeeRole.values());
            model.addAttribute("managers", managerService.getAll());
            return "employee_sign_up";
        }
    }

    @GetMapping("/signin")
    public String getSignIn(Model model){
        model.addAttribute("employee",new Person());
        return "employee_sign_in";
    }

    @PostMapping("signin")
    public String postSignIn(@Valid Person user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employee",user);
            return "employee_sign_in";
        }
        if(employeeService.findByUsername(user.getUsername()) == null){
            model.addAttribute("employee",user);
            model.addAttribute("message", "Wrong username and password. Please enter again.");
            return "employee_sign_in";
        }
        model.addAttribute("employee",user);
        return "redirect:/employee/employee_profile/" + user.getUsername();
    }

    @GetMapping("employee_profile/{username}")
    public String getProfile(@PathVariable String username, Model model){
        Employee employee = employeeService.findByUsername(username);
        model.addAttribute("employee",employee);
        model.addAttribute("message", "Hi " + employee.getFirstName());
        return "employee_profile";
    }

    @GetMapping("leave_requests/{username}")
    public String getLeaveRequestsList(@PathVariable String username, Model model){
        model.addAttribute("leaves",leaveService.findAllByEmployeeUsername(username));
        model.addAttribute(username);
        return "employee_leave_requests";
    }

    @GetMapping("leave_request/{username}")
    public String getLeaveRequest(@PathVariable String username, Model model){
        model.addAttribute("leaveDto",new LeaveDto());
        model.addAttribute("leaveTypes", LeaveType.values());
        model.addAttribute(username);
        return "employee_leave_request";
    }

    @PostMapping("leave_request/{username}")
    public String postLeaveRequest(@PathVariable String username,@Valid LeaveDto leaveDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "employee_leave_request";
        }
        try{
            leaveDto.setStartDate(leaveDto.getStart());
            leaveDto.setEndDate(leaveDto.getEnd());
            Employee employee = employeeService.findByUsername(username);
            List<Leave> allEmployeeLeaves = leaveService.findAllByEmployeeUsername(username);
            LeaveRequestCheck leaveRequestCheck = leaveService.checkValidity(leaveDto,allEmployeeLeaves,model);
            if(leaveRequestCheck == LeaveRequestCheck.WRONG_DATE) {
                model.addAttribute("title", "Incorrect Dates");
                model.addAttribute("message", "End Date Must Be After Start Date!");
                model.addAttribute("leaveDto", leaveDto);
                model.addAttribute(username);
                return "employee_leave_request_error";
            }else if (leaveRequestCheck == LeaveRequestCheck.OVERLAP) {
                model.addAttribute("title", "Dates Overlap");
                model.addAttribute("message", "Leave Dates Overlap With Your Other Leave Requests!");
                model.addAttribute("leaveDto", leaveDto);
                model.addAttribute(username);
                return "employee_leave_request_error";
            } else if (leaveRequestCheck == LeaveRequestCheck.LIMIT_EXCEED) {
                model.addAttribute("title", "Too Many Leave Requests!");
                model.addAttribute("message", "You Have 10 Leave Requests. You Can't Add More.");
                model.addAttribute("leaveDto", leaveDto);
                model.addAttribute(username);
                return "employee_leave_request_error";
            }
            leaveDto.setEmployee(employee);
            leaveService.save(leaveDto);
            model.addAttribute("title", "Leave Request Submitted");
            model.addAttribute("message", "Your Leave Request Has Been Submitted.");
            model.addAttribute(username);
        }catch (Exception e){
            model.addAttribute("title", "Leave Request Not Submitted");
            model.addAttribute("message", "There Was A Problem Submitting Your Request.");
            return "employee_leave_request_error";
        }
        return "employee_leave_request_result";
    }

    @GetMapping("/delete_leave_request/{leave_id}")
    public String deleteRequest(@PathVariable Long leave_id, Model model){
        String employeeUsername = leaveService.findById(leave_id).get().getEmployee().getUsername();
        leaveService.deleteById(leave_id);
        model.addAttribute("message", "Leave Request Has Been Deleted.");
        model.addAttribute("title","Leave Request Deleted");
        model.addAttribute("username",employeeUsername);
        return "employee_leave_request_result";
    }

    @GetMapping("send_email/{employee_username}")
    public String getSendEmail(@PathVariable String employee_username, Model model){
        Employee employee = employeeService.findByUsername(employee_username);
        String from = employee.getEmailAddress();
        model.addAttribute("from", from);
        model.addAttribute(new Email());
        model.addAttribute("username", employee_username);
        model.addAttribute("role", "employee");
        return "send_email";
    }

    @PostMapping("send_email/{employee_username}")
    public String postSendEmail(@PathVariable String employee_username, @Valid Email email, BindingResult bindingResult,
                                @RequestParam(value = "attachedFile") MultipartFile attachedFile, Model model){
        if(bindingResult.hasErrors()){
            getSendEmail(employee_username, model);
        }
        Employee employee = employeeService.findByUsername(employee_username);
        String from = employee.getEmailAddress();
        email.setFrom(from);
        try {
            emailService.sendEmail(email, attachedFile);
            model.addAttribute("title", "Email Sent");
            model.addAttribute("message", "Your Email Sent Successfully.");
            model.addAttribute("username", employee_username);
            model.addAttribute("role", "employee");
            return "send_email_result";
        }catch (Exception e){
            model.addAttribute("title", "Email Not Sent");
            model.addAttribute("message", "There Was An Error Sending Your Email.");
            model.addAttribute("username", employee_username);
            model.addAttribute("role", "employee");
            e.printStackTrace();
            return "send_email_result";
        }

    }


}
