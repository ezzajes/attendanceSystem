package my.edu.utem.ftmk.dad.attendancesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.edu.utem.ftmk.dad.attendancesystem.model.Attendance;

/**
 * Repository interface for managing Attendance entities.
 * This interface extends the JpaRepository interface, providing standard CRUD operations.
 * 
 * @author ezzajeslin
 *
 */

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

}
