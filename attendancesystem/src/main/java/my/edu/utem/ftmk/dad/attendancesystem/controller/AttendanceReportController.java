package my.edu.utem.ftmk.dad.attendancesystem.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

@Controller
public class AttendanceReportController {

    private String attendanceURI = 
    		"http://localhost:8080/attendancesystem/api/attendances";
    private String examinationURI = 
    		"http://localhost:8080/attendancesystem/api/examinations";
    private String registrationSubjectURI = 
    		"http://localhost:8080/attendancesystem/api/registrationsubjects";
    private String studentURI = 
    		"http://localhost:8080/attendancesystem/api/students";

    @GetMapping("/attendancereport/{examId}")
    public String displayReport(@PathVariable("examId") long examId, 
    		Model model) {
        // Fetch the examination based on the examId
        Examination examination = fetchExamination(examId);
        if (examination != null) {
            // Get the subject ID from the examination
            long subjectId = examination.getSubject().getSubjectId();

            // Calculate the total number of students supposed to attend based 
            // on subject ID
            int totalStudents = countRegisteredStudents(subjectId);

            // Fetch the attendance records for the specified examId
            List<Attendance> attendanceList = fetchAttendanceList(examId);

            // Calculate the total attendance count
            int totalAttendance = attendanceList.size();

            // Calculate the total absence count
            int totalAbsence = totalStudents - totalAttendance;

            // Fetch the absent students
            List<Student> absenceList = 
            		fetchAbsentStudents(subjectId, attendanceList);

            // Add the data to the model for rendering in the view
            model.addAttribute("examination", examination);
            model.addAttribute("totalStudents", totalStudents);
            model.addAttribute("totalAttendance", totalAttendance);
            model.addAttribute("totalAbsence", totalAbsence);
            model.addAttribute("attendanceList", attendanceList);
            model.addAttribute("absenceList", absenceList);
        }

        // Return the name of the view to render
        return "attendancereport";
    }

    /**
     * This method retrieve examination by examination Id
     * @param examId
     * @return
     */
    private Examination fetchExamination(long examId) {
        String uri = examinationURI + "/" + examId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Examination> response = 
        		restTemplate.getForEntity(uri, Examination.class);
        return response.getBody();
    }

    /**
     * This method retrieve total students for a subject
     * @param subjectId
     * @return
     */
    private int countRegisteredStudents(long subjectId) {
        String uri = registrationSubjectURI + "/count/" + subjectId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Integer> response = 
        		restTemplate.getForEntity(uri, Integer.class);
        return response.getBody();
    }

    /**
     * This method retrieve the list of student attendances for an examination.
     * @param examId
     * @return
     */
    private List<Attendance> fetchAttendanceList(long examId) {
        String uri = attendanceURI + "/attendancesexam/" + examId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Attendance[]> response = 
        		restTemplate.getForEntity(uri, Attendance[].class);
        Attendance[] attendanceArray = response.getBody();
        List<Attendance> attendanceList = Arrays.asList(attendanceArray);
        
        // Filter the attendance list based on the specified examId
        attendanceList = attendanceList.stream()
                .filter(attendance -> attendance.getExamination().getExamId() 
                		== examId)
                .collect(Collectors.toList());
        
        return attendanceList;
    }

    /**
     * This method retrieve the list of absent students for an examination
     * @param subjectId
     * @param attendanceList
     * @return
     */
    private List<Student> fetchAbsentStudents(long subjectId, 
    		List<Attendance> attendanceList) {
        String uri = registrationSubjectURI + "?subjectId=" + subjectId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<RegistrationSubject[]> response = 
        		restTemplate.getForEntity(uri, RegistrationSubject[].class);
        RegistrationSubject[] registrationSubjects = response.getBody();
        List<Integer> attendedStudentIds = attendanceList.stream()
                .map(attendance -> attendance.getStudent().getStudentId())
                .collect(Collectors.toList());
        List<Student> absenceList = new ArrayList<>();
        for (RegistrationSubject registrationSubject : registrationSubjects) {
            int studentId = registrationSubject.getStudent().getStudentId();
            if (!attendedStudentIds.contains(studentId)) {
                Student student = fetchStudent(studentId);
                absenceList.add(student);
            }
        }
        return absenceList;
    }

    /**
     * This method will retrieve the student by the studentId
     * @param studentId
     * @return
     */
    private Student fetchStudent(long studentId) {
        String uri = studentURI + "/" + studentId;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Student> response = 
        		restTemplate.getForEntity(uri, Student.class);
        return response.getBody();
    }
}
