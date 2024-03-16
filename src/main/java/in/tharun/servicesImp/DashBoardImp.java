package in.tharun.servicesImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tharun.binding.DashboardResponse;
import in.tharun.binding.EnquiryForm;
import in.tharun.binding.EnquirySearchCriteria;
import in.tharun.entities.CourseEntity;
import in.tharun.entities.EnqStatusEntity;
import in.tharun.entities.StudentEnqEntity;
import in.tharun.entities.UserDtlsEntity;
import in.tharun.repositories.CourseRepo;
import in.tharun.repositories.EnqStatusRepo;
import in.tharun.repositories.StudentEnqRepo;
import in.tharun.repositories.UserDtlsRepo;
import in.tharun.services.EnquiryService;
import jakarta.persistence.Id;
import jakarta.servlet.http.HttpSession;

@Service
public class DashBoardImp implements EnquiryService {
    @Autowired
	private UserDtlsRepo repo;
    @Autowired
    private CourseRepo crepo;
    @Autowired
    private EnqStatusRepo srepo;
    @Autowired
     private StudentEnqRepo r;
    @Autowired
    private HttpSession session;
    
	public DashboardResponse get(Integer id) {
		
		DashboardResponse d=new DashboardResponse();
		Optional<UserDtlsEntity> findById = repo.findById(id);
		if(findById.isPresent())
		{
			UserDtlsEntity entity = findById.get();
			List<StudentEnqEntity> list = entity.getEnquiry();
			int total = list.size();
			
			int enrolled = list.stream().filter(e -> e.getENQUIRY_STATUS().equals("enrolled")).collect(Collectors.toList()).size();
			int lost = list.stream().filter(e -> e.getENQUIRY_STATUS().equals("lost")).collect(Collectors.toList()).size();
			d.setTotal(total);
			d.setEnroll(enrolled);
			d.setLost(lost);
		}
		
		return d;
	}
	@Override
	public List<String> getCourses() {
		List<CourseEntity> all = crepo.findAll();
		List<String> courses =new ArrayList<>();
		for (CourseEntity entity :all) {
			courses.add(entity.getName());
		}
		return courses;
	}
	@Override
	public List<String> getStatus() {
		List<EnqStatusEntity> all = srepo.findAll();
		List<String> status=new ArrayList<>();
		for (EnqStatusEntity entity : all) {
			status.add(entity.getStatus());
		}
		return status;
	}
	@Override
	public String saveEnq(EnquiryForm form) {
         StudentEnqEntity entity=new StudentEnqEntity();
         if(form.getId()==null)
         {
         entity.setSTUDENT_NAME(form.getName()); 
         entity.setPHNO(form.getPhno()); 
         entity.setCLASS_MODE(form.getClassMode());
         entity.setCOURSE_NAME( form.getCoursess());
         entity.setENQUIRY_STATUS(form.getStatuss());
       //  BeanUtils.copyProperties(form, entity);
         Integer id= (Integer)session.getAttribute("userid");
         UserDtlsEntity userDtlsEntity = repo.findById(id).get();
         entity.setUser_id(userDtlsEntity);
         r.save(entity);
        
		return "save";
         }
         else
         {
        	 Optional<StudentEnqEntity> findById = r.findById(form.getId());
         	if(findById.isPresent())
         	{
         		StudentEnqEntity e = findById.get();
         		e.setSTUDENT_NAME(form.getName());
         		e.setCOURSE_NAME(form.getCoursess());
         		e.setPHNO(form.getPhno());
         		e.setCLASS_MODE(form.getClassMode());
         		e.setENQUIRY_STATUS(form.getStatuss());
         		r.save(e);
         	}
        	 return "update";
         }
	}
	@Override
	public List<StudentEnqEntity> getEnq() {
		Integer i= (Integer)session.getAttribute("userid");
		Optional<UserDtlsEntity> findById = repo.findById(i);
		if(findById.isPresent())
		{
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiry = userDtlsEntity.getEnquiry();
			return enquiry;
		}
		return null;
		
	}
	@Override
	public List<StudentEnqEntity> filterEqr(EnquirySearchCriteria fil) {
		Integer i= (Integer)session.getAttribute("userid");
		Optional<UserDtlsEntity> findById = repo.findById(i);
		if(findById.isPresent())
		{
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiry = userDtlsEntity.getEnquiry();
			
			  if(null!=fil.getCname() & !"".equals(fil.getCname()))
			  {
				  enquiry = enquiry.stream().filter(e -> e.getCOURSE_NAME().equals(fil.getCname())).collect(Collectors.toList());
			  }
			  if(null!=fil.getMode() & !"".equals(fil.getMode()))
			  {
				  enquiry = enquiry.stream().filter(e -> e.getCLASS_MODE().equals(fil.getMode())).collect(Collectors.toList());
			  }
			  if(null!=fil.getStatus() & !"".equals(fil.getStatus()))
			  {
				  enquiry = enquiry.stream().filter(e -> e.getENQUIRY_STATUS().equals(fil.getStatus())).collect(Collectors.toList());
			  }
			
			return enquiry;
		}
		return null;
	}
     public StudentEnqEntity getEnqies(Integer id) {
    	 Integer i= (Integer)session.getAttribute("userid");
 		Optional<UserDtlsEntity> findById = repo.findById(i);
 		if(findById.isPresent())
 		{
 			UserDtlsEntity userDtlsEntity = findById.get();
 			
 			List<StudentEnqEntity> enquiry = userDtlsEntity.getEnquiry();
 			
 			for (StudentEnqEntity studentEnqEntity : enquiry) {
			          if(studentEnqEntity.getENQUIRY_ID()==id)
			          {
			        	   return studentEnqEntity;
			          }
			}
 			//return enquiry;
 		}
    	return null;
    }
   
}
