package my.edu.utem.ftmk.dad.attendancesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.edu.utem.ftmk.dad.attendancesystem.model.Attendance;
import my.edu.utem.ftmk.dad.attendancesystem.model.RegistrationSubject;

/**
 * Repository interface for managing RegistrationSubject entities.
 * This interface extends the JpaRepository interface, providing standard CRUD operations.
 * 
 * @author ezzajeslin
 *
 */
@Repository
public interface RegistrationSubjectRepository extends JpaRepository<RegistrationSubject, Long> {
  
   // Retrieve list of student registration subject for the specified subjectId
	 List<RegistrationSubject> findBySubjectSubjectId(long subjectId);
  
   // Calculate total students for a subject 
	 int countBySubjectSubjectId(long subjectId);
}
