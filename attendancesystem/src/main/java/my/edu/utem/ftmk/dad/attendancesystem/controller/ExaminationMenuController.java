package my.edu.utem.ftmk.dad.attendancesystem.controller;

/**
 * ExaminationMenuController
 * 
 * This class handles the user interface and actions related to the examination menu.
 * It provides functionality for managing and conducting examinations.
 * 
 * 
 * Usage:
 * - Instantiate ExaminationMenuController to display the examination menu.
 * - Call methods to perform desired actions and handle user input.
 * 
 *@author ezzajeslin
 */

import java.util.Arrays;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import my.edu.utem.ftmk.dad.attendancesystem.model.Examination;
import my.edu.utem.ftmk.dad.attendancesystem.model.Student;
import my.edu.utem.ftmk.dad.attendancesystem.repository.StudentRepository;

@Controller
public class ExaminationMenuController {
	
	private String defaultURI = "http://localhost:8080/attendancesystem/api/examinations";
	
	//Retrieves a list of examinations from the examination system API and attaches it to the model.
	@GetMapping("/examination/list")
	public String getExaminations(Model model) 
	{
		//The URI for GET examination
		String uri = "http://localhost:8080/attendancesystem/api/examinations";
		
		//Get a list of examinations from the web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Examination[]> response = restTemplate.getForEntity(uri, Examination[].class);
			
		//Parse JSON data to array of object
		Examination examinations[] = response.getBody();
			
		//Parse an array to a list object
		List<Examination> examinationList = Arrays.asList(examinations);
		
		//Attach list to model as attribute
		model.addAttribute("examinations", examinationList);
		
		return "examinations";
	}
	
	//Retrieves an examination with the given examId and attaches it to the model.
	@GetMapping("/examination/{examId}")
	public String getExamination (@PathVariable Integer examId, Model model) 
	{
		String pageTitle = "New Examination";
		Examination examination = new Examination();
			
		//Give a new title to the page
		pageTitle = "Search Name";
			
		//Attach value to pass to front end
		model.addAttribute("examination", examination);
		model.addAttribute("pageTitle", pageTitle);
		
		return "searchName";
	}
	
	@Autowired
	private StudentRESTController studentService;
		
	//Searches for students by keyword and attaches the search results, exam ID, and current date and time to the model.
	@GetMapping("/examination/search")
	public String searchExamination(@RequestParam String keyword, @RequestParam Long examId, Model model) 
	{
	    List<Student> students = new ArrayList<>();
	    try {
		    LocalDateTime currentDateTime = LocalDateTime.now();
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		    String formattedDateTime = currentDateTime.format(formatter);
		    students = studentService.searchStudentsByName(keyword);
		    model.addAttribute("students", students);
		    model.addAttribute("examId", examId);
		    model.addAttribute("currentDateTime", formattedDateTime);
		    
		 // Log the values for verification
		    System.out.println("Keyword: " + keyword);
	        System.out.println("ExamId: " + examId);
	        System.out.println("Current DateTime: " + formattedDateTime);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        // You can add custom error handling logic here, such as redirecting to an error page
	        return "errorPage";
	    }
	    return "searchName";
	}
	
	//Updates or adds an examination by sending a HTTP request to the examination system API.
	@RequestMapping("/examination/save")
	public String updateExamination (@ModelAttribute Examination examination) 
	{		
		//Create a new RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		//Create a request body
		HttpEntity<Examination> request = new HttpEntity<Examination>(examination);
		
		String examinationResponse = "";
		
		if(examination.getExamId() > 0) {
			//This block update a new order type and
			
			//send request as PUT
			restTemplate.put(defaultURI, request, Examination.class);
		}else {
			//This block add a new order type
			
			//send request as POST
			examinationResponse = restTemplate.postForObject(defaultURI, request, String.class);
		}
		System.out.println(examinationResponse);
		
		//Redirect request to display a list of order type
		return "redirect:/examination/list";
		
	}
	
}

/*
 * @GetMapping("/examination/search") public String
 * searchExamination(@RequestParam String studentName, Model model) {
 * List<Student> students = studentRepository.findByStudentName(studentName);
 * model.addAttribute("students", students); return "searchName"; }
 */

