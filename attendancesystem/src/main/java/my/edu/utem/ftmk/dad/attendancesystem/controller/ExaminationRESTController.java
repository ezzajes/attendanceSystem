package my.edu.utem.ftmk.dad.attendancesystem.controller;

/**
 * ExaminationRestController
 * 
 * This class defines the REST API endpoints for managing examinations.
 * It handles incoming HTTP requests related to display, managing and examinations.
 * 
 * 
 * Available Endpoints:
 * - GET /examinations: Retrieves all examinations.
 * - GET /examinations/{id}: Retrieves an examination by ID.
 * - POST /examinations: Creates a new examination.
 * - PUT /examinations/{id}: Updates an existing examination.
 * - DELETE /examinations/{id}: Deletes an examination.
 * 
 * Request and Response Formats:
 * - GET /examinations: Returns a JSON array of examinations.
 * - GET /examinations/{id}: Returns a JSON object representing the examination.
 * - POST /examinations: Expects a JSON object representing a new examination.
 *                        Returns the created examination as a JSON object.
 * - PUT /examinations/{id}: Expects a JSON object representing an updated examination.
 *                           Returns the updated examination as a JSON object.
 * - DELETE /examinations/{id}: Deletes the examination with the specified ID.
 * - GET /examinations/{id}/results: Retrieves the examination results as a JSON object.
 * 
 *@author ezzajeslin
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

import my.edu.utem.ftmk.dad.attendancesystem.model.Examination;
import my.edu.utem.ftmk.dad.attendancesystem.repository.ExaminationRepository;

@RestController
@RequestMapping("/api/examinations")
public class ExaminationRESTController {

	@Autowired
	private ExaminationRepository examinationRepository;
	
	//Retrieves all examinations from the examination repository.
	@GetMapping
	public List<Examination> getExamination()
	{
		return examinationRepository.findAll();
	}
	
	//Retrieves an examination by its ID from the examination repository.
	@GetMapping("{examId}")
	public Examination getExamination(@PathVariable long examId)
	{
		Examination examination = examinationRepository.findById(examId).get();
		return examination;
	}
	
	//Inserts a new examination into the database.
	@PostMapping()
	public Examination insertExamination(@RequestBody Examination examination)
	{
		return examinationRepository.save(examination);
	}
	
	//Updates an examination in the examination repository.
	@PutMapping()
	public Examination updateExamination(@RequestBody Examination examination) 
	{
		return examinationRepository.save(examination);
	}
	
	//Deletes an examination by its ID from the examination repository.
	@DeleteMapping("{examId}")
	public ResponseEntity<HttpStatus> deleteExamination(@PathVariable long examId)
	{
		examinationRepository.deleteById(examId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
