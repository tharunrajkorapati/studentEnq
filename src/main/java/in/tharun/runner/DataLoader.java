package in.tharun.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.tharun.entities.CourseEntity;
import in.tharun.entities.EnqStatusEntity;
import in.tharun.repositories.CourseRepo;
import in.tharun.repositories.EnqStatusRepo;

@Component
public class DataLoader implements ApplicationRunner
{
	@Autowired
    private CourseRepo crepo;
	@Autowired
    private EnqStatusRepo srepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		crepo.deleteAll();
		CourseEntity e1=new CourseEntity();
		e1.setName("java");
		CourseEntity e2=new CourseEntity();
		e2.setName("python");
		CourseEntity e3=new CourseEntity();
		e3.setName("devops");
		
		crepo.saveAll(Arrays.asList(e1,e2,e3));
		
		srepo.deleteAll();
		
		EnqStatusEntity s1=new EnqStatusEntity();
		s1.setStatus("new");
		EnqStatusEntity s2=new EnqStatusEntity();
		s2.setStatus("enrolled");
		EnqStatusEntity s3=new EnqStatusEntity();
		s3.setStatus("lost");
		srepo.saveAll(Arrays.asList(s1,s2,s3));
	}

}
