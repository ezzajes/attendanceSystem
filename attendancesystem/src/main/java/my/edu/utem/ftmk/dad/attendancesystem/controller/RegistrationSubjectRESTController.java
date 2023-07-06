package my.edu.utem.ftmk.dad.attendancesystem.controller;

/**
 * RegistrationSubjectRestController
 * 
 * This class defines the REST API endpoints for managing student registration for subjects.
 * It handles incoming HTTP requests related to enrolling students in subjects and retrieving
 * registration information.
 *
 * 
 * Available Endpoints:
 * - GET /registration/subjects: Retrieves all subjects available for registration.
 * - GET /registration/subjects/{id}: Retrieves a subject by ID.
 * - GET /registration/students/{id}/subjects: Retrieves the subjects enrolled by a student.
 * - POST /registration/students/{id}/subjects: Enrolls a student in subjects.
 * - DELETE /registration/students/{id}/subjects/{subjectId}: Unenrolls a student from a subject.
 * 
 * Request and Response Formats:
 * - GET /registration/subjects: Returns a JSON array of subjects available for registration.
 * - GET /registration/subjects/{id}: Returns a JSON object representing the subject.
 * - GET /registration/students/{id}/subjects: Returns a JSON array of subjects enrolled by the student.
 * - POST /registration/students/{id}/subjects: Expects a JSON array of subject IDs to enroll the student.
 *                                              Returns a success message as a JSON object.
 * - DELETE /registration/students/{id}/subjects/{subjectId}: Unenrolls the student from the subject.
 *                                                          Returns a success message as a JSON object.
 * 
 * @author ezzajeslin
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.attendancesystem.model.RegistrationSubject;
import my.edu.utem.ftmk.dad.attendancesystem.repository.RegistrationSubjectRepository;

@RestController
@RequestMapping("/api/registrationsubjects")
public class RegistrationSubjectRESTController {
	
	@Autowired
	private RegistrationSubjectRepository registrationSubjectRespository;
	
	//Retrieves a list of registration subjects from the database.
	@GetMapping
	public List<RegistrationSubject> getRegistrationSubject()
	{
		return registrationSubjectRespository.findAll();
	}
	
	//Retrieves a registration subject by its subjectId.
	@GetMapping("{subjectId}")
	public List<RegistrationSubject> getRegistrationSubject(@PathVariable long subjectId)
	{
		return registrationSubjectRespository.findBySubjectSubjectId(subjectId);
	}
	
	/*
	 * 
	 */
	@GetMapping("/count/{subjectId}")
	public int countRegisteredStudents(@PathVariable long subjectId) {
	    return registrationSubjectRespository.countBySubjectSubjectId(subjectId);
	}

	
	//Inserts a new registration subject into the system.
	@PostMapping()
	public RegistrationSubject insertRegistrationSubject(@RequestBody RegistrationSubject registrationSubject)
	{
		return registrationSubjectRespository.save(registrationSubject);
	}
	
	//Updates a registration subject in the database.
	@PutMapping()
	public RegistrationSubject updateRegistrationSubject(@RequestBody RegistrationSubject registrationSubject) 
	{
		return registrationSubjectRespository.save(registrationSubject);
	}
	

}
