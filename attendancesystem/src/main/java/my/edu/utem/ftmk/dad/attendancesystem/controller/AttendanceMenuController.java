package my.edu.utem.ftmk.dad.attendancesystem.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import my.edu.utem.ftmk.dad.attendancesystem.model.Attendance;
import my.edu.utem.ftmk.dad.attendancesystem.model.Examination;
import my.edu.utem.ftmk.dad.attendancesystem.model.Student;
import my.edu.utem.ftmk.dad.attendancesystem.repository.AttendanceRepository;

/**
 * AttendanceMenuController
 * 
 * This class handles the user interface and actions related to the attendance menu.
 * It allows users to view and manage examination attendance records for student.
 * 
 * 
 * Available actions:
 * - View attendance records
 * - Insert new attendance record for student
 * - Update or edit existing attendance records.
 * - Generate attendance reports for a specified time period.
 * 
 * Usage:
 * - Instantiate AttendanceMenuController to display the attendance menu.
 * - Call methods to perform desired actions and handle user input.
 * 
 *@author ezzajeslin
 */

@Controller
public class AttendanceMenuController {

	private String defaultURI = "http://localhost:8080/attendancesystem/api/attendances";
	
	//Retrieves a list of attendances and attaches it to the model.	 
	@GetMapping("/attendance/list")
	public String getAttendances(Model model) 
	{		
		//The URI for GET examination
		String uri = "http://localhost:8080/attendancesystem/api/attendances";		
		
		//Get a list of examinations from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Attendance[]> response = restTemplate.getForEntity(uri, Attendance[].class);
			
		//Parse JSON data to array of object
		Attendance attendances[] = response.getBody();
			
		//Parse an array to a list object
		List<Attendance> attendanceList = Arrays.asList(attendances);
		
		//Attach list to model as attribute
		model.addAttribute("attendances", attendanceList);
		
		return "attendances";
	}
	
	//Inserts an attendance record into the attendance system API.
	@RequestMapping("/attendance/save")
    public String insertAttendance(@ModelAttribute Attendance attendance, @RequestParam int examId, @RequestParam int studentId) 
	{
		RestTemplate restTemplate = new RestTemplate();
	
	 	// Set the examId value to the attendance object
		 Examination examination = new Examination();
		 examination.setExamId(examId);
		 
		 Student student = new Student();
		 student.setStudentId(studentId);

	    // Set the Examination object to the attendance
	    attendance.setExamination(examination);
	    attendance.setStudent(student);

        // Create a request body
        HttpEntity<Attendance> request = new HttpEntity<>(attendance);

        // Send a POST request to save the attendance
        String attendanceResponse = restTemplate.postForObject(defaultURI, request, String.class);

        System.out.println(attendanceResponse);

        // Redirect to the examination list page
        return "redirect:/examination/list";
    }
}
	   

