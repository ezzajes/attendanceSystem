package my.edu.utem.ftmk.dad.attendancesystem.controller;

/**
 * AttendanceRestController
 * 
 * This class defines the REST API endpoints for managing attendance records.
 * It handles incoming HTTP requests related to attendance tracking and provides
 * appropriate responses.
 * 
 * 
 * Available Endpoints:
 * - GET /attendance: Retrieves all attendance records.
 * - GET /attendance/{id}: Retrieves attendance record by ID.
 * - POST /attendance: Creates a new attendance record.
 * - PUT /attendance/{id}: Updates an existing attendance record.
 * - DELETE /attendance/{id}: Deletes an attendance record.
 * 
 * Request and Response Formats:
 * - GET /attendance: Returns a JSON array of attendance records.
 * - GET /attendance/{id}: Returns a JSON object representing the attendance record.
 * - POST /attendance: Expects a JSON object representing a new attendance record.
 *                       Returns the created attendance record as a JSON object.
 * - PUT /attendance/{id}: Expects a JSON object representing an updated attendance record.
 *                          Returns the updated attendance record as a JSON object.
 * - DELETE /attendance/{id}: Deletes the attendance record with the specified ID.
 *                            Returns a success message as a JSON object.
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

import my.edu.utem.ftmk.dad.attendancesystem.model.Attendance;
import my.edu.utem.ftmk.dad.attendancesystem.repository.AttendanceRepository;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceRESTController {

	@Autowired
	private AttendanceRepository attendanceRepository;
	
	//Retrieves a list of all attendances from the attendance repository.
	@GetMapping
	public List<Attendance> getAttendance()
	{
		return attendanceRepository.findAll();
	}
	
	//Retrieves an attendance record by its ID from the attendance repository.
	@GetMapping("{attendanceId}")
	public Attendance getAttendance(@PathVariable long attendanceId)
	{
		Attendance attendance = attendanceRepository.findById(attendanceId).get();
		return attendance;
	}
	
	//Inserts a new attendance record into the attendance repository.
	@PostMapping()
	public Attendance insertAttendance(@RequestBody Attendance attendance)
	{
		return attendanceRepository.save(attendance);
	}
	
	/*
	  @PutMapping() public Attendance updateAttendance(@RequestBody Attendance
	  attendance) { return attendanceRepository.save(attendance); }
	  
	  @DeleteMapping("{attendanceId}") public ResponseEntity<HttpStatus>
	  deleteAttendance(@PathVariable long attendanceId) {
	  attendanceRepository.deleteById(attendanceId); return new
	  ResponseEntity<>(HttpStatus.OK); }
	 */
	
}
