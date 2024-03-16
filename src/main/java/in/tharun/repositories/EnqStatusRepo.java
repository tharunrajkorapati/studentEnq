package in.tharun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.tharun.entities.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer>
{

}
