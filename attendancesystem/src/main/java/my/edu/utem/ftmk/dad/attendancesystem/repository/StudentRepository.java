package my.edu.utem.ftmk.dad.attendancesystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.edu.utem.ftmk.dad.attendancesystem.model.Student;

/**
 * Repository interface for managing Student entities.
 * This interface extends the JpaRepository interface, providing standard CRUD operations.
 * 
 * @author ezzajeslin
 *
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {	
	
	List<Student> findByStudentName(String studentName);

	List<Student> findByStudentNameContaining(String keyword);
	
	// Find student by Id for absence and attended students
	Optional<Student> findByStudentId(long studentId);


}
