package my.edu.utem.ftmk.dad.attendancesystem.controller;

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
	
	@GetMapping
	public List<Examination> getExamination()
	{
		return examinationRepository.findAll();
	}
	
	@GetMapping("{examId}")
	public Examination getExamination(@PathVariable long examId)
	{
		Examination examination = examinationRepository.findById(examId).get();
		return examination;
	}
	
	@PostMapping()
	public Examination insertExamination(@RequestBody Examination examination)
	{
		return examinationRepository.save(examination);
	}
	
	@PutMapping()
	public Examination updateExamination(@RequestBody Examination examination) {
		return examinationRepository.save(examination);
	}
	
	@DeleteMapping("{examId}")
	public ResponseEntity<HttpStatus> deleteExamination(@PathVariable long examId)
	{
		examinationRepository.deleteById(examId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
