package in.tharun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.tharun.entities.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity,Integer>
{

}
