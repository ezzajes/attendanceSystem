package my.edu.utem.ftmk.dad.attendancesystem.controller;

/**
 * StudentRestController
 * 
 * This class defines the REST API endpoints for managing student data.
 * It handles incoming HTTP requests related to creating, retrieving, updating, and deleting students.
 * 
 * 
 * Available Endpoints:
 * - GET /students: Retrieves all students.
 * - GET /students/{id}: Retrieves a student by ID.
 * - POST /students: Creates a new student.
 * - PUT /students/{id}: Updates an existing student.
 * - DELETE /students/{id}: Deletes a student.
 * 
 * Request and Response Formats:
 * - GET /students: Returns a JSON array of students.
 * - GET /students/{id}: Returns a JSON object representing the student.
 * - POST /students: Expects a JSON object representing a new student.
 *                   Returns the created student as a JSON object.
 * - PUT /students/{id}: Expects a JSON object representing an updated student.
 *                        Returns the updated student as a JSON object.
 * - DELETE /students/{id}: Deletes the student with the specified ID.
 * 
 * @author ezzajeslin
 *  
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.attendancesystem.model.Student;
import my.edu.utem.ftmk.dad.attendancesystem.repository.StudentRepository;

@Service
@RestController
@RequestMapping("/api/students")
public class StudentRESTController {
	
	@Autowired
	private StudentRepository studentRepository;
	
	//Retrieves a list of all students from the student repository.
	@GetMapping
	public List<Student> getStudent()
	{
		return studentRepository.findAll();
	}
	
	//Searches for students whose names contain the specified keyword.
	 public List<Student> searchStudentsByName(String keyword) 
	 {
	    return studentRepository.findByStudentNameContaining(keyword);
	 }
	
	 //Retrieves a specific student by their ID from the student repository.
	@GetMapping("{studentId}")
	public Student getStudent(@PathVariable long studentId)
	{
		Student student = studentRepository.findById(studentId).get();
		return student;
	}
	
	//Inserts a new student into the system by saving it using the student repository.
	@PostMapping()
	public Student insertStudent(@RequestBody Student student)
	{
		return studentRepository.save(student);
	}
	
	//Updates a student record with the provided information.
	@PutMapping()
	public Student updateStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}
	

	

}
