package my.edu.utem.ftmk.dad.attendancesystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * This class represents list of attendance records of examination for student in the system.
 * The attendance class contains information about attendance records such as date time, exam id and student id .
 * 
 * The class provides method to retrieve and update attendance.
 * 
 * The attendance data is used for various purposes, such as in keeping student examination records
 * 
 * @author ezzajeslin
 *
 */

@Entity
@Table(name="attendance")
public class Attendance {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="attendanceId")
	private int attendanceId;
	
	/*
	 * @Column (name="attendanceDateTime")
	 * 
	 * @Temporal(TemporalType.TIMESTAMP) private LocalDateTime attendanceDateTime;
	 */
	
	@Column(name="attendanceDateTime")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime attendanceDateTime;
	
	@Column (name="deviceType")
	private String deviceType;
	
	
	@ManyToOne
    @JoinColumn(name="ExamId")
    private  Examination examination;
	
	
	@ManyToOne
    @JoinColumn(name="StudentId")
    private  Student student;

	//getter and setters for the attendance variables.
	public int getAttendanceId() {
		return attendanceId;
	}


	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}


	public LocalDateTime getAttendanceDateTime() {
		return attendanceDateTime;
	}


	/*
	 * public void setAttendanceDateTime(String attendanceDateTimeString) {
	 * LocalDateTime attendanceDateTime =
	 * LocalDateTime.parse(attendanceDateTimeString,
	 * DateTimeFormatter.ISO_DATE_TIME); this.attendanceDateTime =
	 * attendanceDateTime; }
	 */
	
	@PrePersist
    public void prePersist() {
        attendanceDateTime = LocalDateTime.now();
    }

	public void setAttendanceDateTime(LocalDateTime attendanceDateTime) {
	    this.attendanceDateTime = attendanceDateTime;
	}
	
	public String getDeviceType() {
		return deviceType;
	}


	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}


	public Examination getExamination() {
		return examination;
	}


	public void setExamination(Examination examination) {
		this.examination = examination;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}
		

}
