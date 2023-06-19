package my.edu.utem.ftmk.dad.attendancesystem.controller;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.attendancesystem.model.Attendance;
import my.edu.utem.ftmk.dad.attendancesystem.model.Examination;
import my.edu.utem.ftmk.dad.attendancesystem.model.RegistrationSubject;
import my.edu.utem.ftmk.dad.attendancesystem.model.Student;
import my.edu.utem.ftmk.dad.attendancesystem.repository.AttendanceRepository;
import my.edu.utem.ftmk.dad.attendancesystem.repository.ExaminationRepository;
import my.edu.utem.ftmk.dad.attendancesystem.repository.RegistrationSubjectRepository;
import my.edu.utem.ftmk.dad.attendancesystem.repository.StudentRepository;

/**
 * This class will generate attendance report after the examination
 * 
 * @author nursabrinaainadzulkifli
 *
 */
@Controller
public class AttendanceReportController {

	private String defaultURI = "http://localhost:8080/attendancesystem/api/attendances";

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private ExaminationRepository examinationRepository;

	@Autowired
	private RegistrationSubjectRepository registrationSubjectRepository;

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/attendancereport/{examId}")
	public String displayReport(@PathVariable("examId") long examId, Model model) {
		
		// Fetch the examination based on the examId
		Examination examination = examinationRepository.findById(examId).orElse(null);

		System.out.println("Subject" + examination.getSubject().getSubjectId());
		
		if (examination != null) {
			// Get the subject ID from the examination
			int subjectId = examination.getSubject().getSubjectId();

			// Calculate the total number of students supposed to attend based on subject ID
			int totalStudents = registrationSubjectRepository.countBySubjectSubjectId(subjectId);
			System.out.println("Total Attendance" + totalStudents);
			
			// Fetch the attendance records for the specified examId by joining Attendance and RegistrationSubject
			List<Attendance> attendanceList = attendanceRepository.findByExaminationExamId(examId);

			// Calculate the total attendance count
			int totalAttendance = attendanceList.size();
			System.out.println("Total Attendance" + totalAttendance);
			
			// Calculate the total absence count
			int totalAbsence = totalStudents - totalAttendance;
			System.out.println("Total Absence" + totalAbsence);

			// Create a list to store the student absences
			List<Student> absenceList = new ArrayList<>();

			// Fetch the registration subjects for the specified subjectId
			List<RegistrationSubject> registrationSubjects = registrationSubjectRepository.findBySubjectSubjectId(subjectId);

			// Find the student IDs in registrationSubjects
			List<Integer> registeredStudentIds = registrationSubjects.stream()
					.map(registrationSubject -> registrationSubject.getStudent().getStudentId())
					.collect(Collectors.toList());

			// Find the student IDs in attendanceList
			List<Integer> attendedStudentIds = attendanceList.stream()
					.map(attendance -> attendance.getStudent().getStudentId())
					.collect(Collectors.toList());

			// Find the student IDs that are absent
			List<Integer> absentStudentIds = registeredStudentIds.stream()
					.filter(studentId -> !attendedStudentIds.contains(studentId))
					.collect(Collectors.toList());

			// Fetch the students based on the absentStudentIds
			for (Integer studentId : absentStudentIds) {
				Optional<Student> optionalStudent = studentRepository.findByStudentId(studentId);
				if (optionalStudent.isPresent()) {
					absenceList.add(optionalStudent.get());
				}
			}

			// Add the data to the model for rendering in the view
			/* model.addAttribute("examination", examination); */
			model.addAttribute("totalStudents", totalStudents);
			model.addAttribute("totalAttendance", totalAttendance);
			model.addAttribute("totalAbsence", totalAbsence);
			model.addAttribute("attendanceList", attendanceList);
			model.addAttribute("absenceList", absenceList);
		}

		// Return the name of the view to render
		return "attendancereport";
	}

}


