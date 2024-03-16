package in.tharun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.tharun.entities.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> 
{
    public UserDtlsEntity findByEmail(String email);
    public UserDtlsEntity findByEmailAndPWD(String email,String pwd);
}
