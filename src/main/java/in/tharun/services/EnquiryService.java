package in.tharun.services;

import java.util.List;

import in.tharun.binding.DashboardResponse;
import in.tharun.binding.EnquiryForm;
import in.tharun.binding.EnquirySearchCriteria;
import in.tharun.entities.StudentEnqEntity;

public interface EnquiryService
{
     public DashboardResponse get(Integer id);
     
     public List<String> getCourses();
     public List<String> getStatus();
     
     public String saveEnq(EnquiryForm form);
     
     public List<StudentEnqEntity> getEnq();
     public List<StudentEnqEntity> filterEqr(EnquirySearchCriteria fil);
     public StudentEnqEntity getEnqies(Integer id);
     
     
}
