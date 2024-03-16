package in.tharun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.tharun.entities.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer>
{

}
