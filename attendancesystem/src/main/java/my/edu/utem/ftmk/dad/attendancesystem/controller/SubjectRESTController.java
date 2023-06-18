package my.edu.utem.ftmk.dad.attendancesystem.controller;

/**
 * SubjectRestController
 * 
 * This class defines the REST API endpoints for managing subjects.
 * It handles incoming HTTP requests related to creating, retrieving, updating, and deleting subjects.
 * 
 * 
 * Available Endpoints:
 * - GET /subjects: Retrieves all subjects.
 * - GET /subjects/{id}: Retrieves a subject by ID.
 * - POST /subjects: Creates a new subject.
 * - PUT /subjects/{id}: Updates an existing subject.
 * - DELETE /subjects/{id}: Deletes a subject.
 * 
 * Request and Response Formats:
 * - GET /subjects: Returns a JSON array of subjects.
 * - GET /subjects/{id}: Returns a JSON object representing the subject.
 * - POST /subjects: Expects a JSON object representing a new subject.
 *                   Returns the created subject as a JSON object.
 * - PUT /subjects/{id}: Expects a JSON object representing an updated subject.
 *                        Returns the updated subject as a JSON object.
 * - DELETE /subjects/{id}: Deletes the subject with the specified ID.
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
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.attendancesystem.model.Subject;
import my.edu.utem.ftmk.dad.attendancesystem.repository.SubjectRepository;

@RestController
@RequestMapping("/api/subjects")
public class SubjectRESTController {
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	//Retrieves a list of all subjects from the subject repository.
	@GetMapping
	public List<Subject> getSubject()
	{
		return subjectRepository.findAll();
	}
	
	//Retrieves a subject by its ID from the subject repository.
	@GetMapping("{subjectId}")
	public Subject getSubject(@PathVariable long subjectId)
	{
		Subject subject = subjectRepository.findById(subjectId).get();
		return subject;
	}
	
	//Inserts a new subject into the system.
	@PostMapping()
	public Subject insertSubject(@RequestBody Subject subject)
	{
		return subjectRepository.save(subject);
	}
	
	//Updates a subject by saving the provided subject object.
	@PutMapping()
	public Subject updateSubject(@RequestBody Subject subject) {
		return subjectRepository.save(subject);
	}
	
	//Deletes a subject with the specified ID from the subject repository.
	@DeleteMapping("{subjectId}")
	public ResponseEntity<HttpStatus> deleteSubject(@PathVariable long subjectId){
		subjectRepository.deleteById(subjectId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
