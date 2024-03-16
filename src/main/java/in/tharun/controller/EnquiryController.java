package in.tharun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tharun.binding.DashboardResponse;
import in.tharun.binding.EnquiryForm;
import in.tharun.binding.EnquirySearchCriteria;
import in.tharun.entities.StudentEnqEntity;
import in.tharun.services.EnquiryService;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
    private HttpSession session;
	@Autowired
	private EnquiryService service;
	@GetMapping("/logout")
	public String logout()
	{
		session.invalidate();
		return "index";
	}
	@GetMapping("/dashboard")
	public String dashboard(Model model) 
	{
		System.out.println("sadhboard is under maintance");
		Integer id=(Integer)session.getAttribute("userid");
		DashboardResponse response = service.get(id);
		model.addAttribute("response", response);
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnquiries( Model model)
	{
		//get courses drop down
		List<String> courses = service.getCourses();
		
		//get satus drop down
		List<String> status = service.getStatus();
		//creat binding oobj
		EnquiryForm form = new EnquiryForm();
		//set data in model obj
		model.addAttribute("courses",courses);
		model.addAttribute("status",status);
		model.addAttribute("form",form);
		
		
		return "enquiry";
	}
	@PostMapping("enquiry")
	public String addEnq(@ModelAttribute("form") EnquiryForm form,Model model)
	{
		System.out.println(form);
		 String saveEnq = service.saveEnq(form);
		
		 if(saveEnq.contains("save"))
		 {
			 model.addAttribute("success", "enquiry added"); 
		 }
		 if(saveEnq.contains("update"))
		 {
			 model.addAttribute("update", "enquiry update"); 
		 }
		
		
		
		return "enquiry";
		
	}
	
	public void init(Model model)
	{
		//get courses drop down
				List<String> courses = service.getCourses();
				
				//get satus drop down
				List<String> status = service.getStatus();
				//creat binding oobj
				EnquirySearchCriteria form=new EnquirySearchCriteria();
				//set data in model obj
				model.addAttribute("courses",courses);
				model.addAttribute("status",status);
				model.addAttribute("form",form);
	}
	
	@GetMapping("/enquiries")
	public String viewEnquiries(Model model)
	{
		init(model);
		List<StudentEnqEntity> entity = service.getEnq();
		model.addAttribute("entity", entity);
		return "enquiries";
	}
	@GetMapping("/filter")
	public String filter(@RequestParam String cname,@RequestParam String mode,@RequestParam String status,Model model)
	{
		//init(model);
		EnquirySearchCriteria c=new EnquirySearchCriteria();
		c.setCname(cname);
		c.setMode(mode);
		c.setStatus(status);
		//System.out.println(c);
		List<StudentEnqEntity> filterEqr = service.filterEqr(c);
		model.addAttribute("entity", filterEqr);
		return "filterTable";
	}
	
	
	public void eidi(Model model)
	{
		//get courses drop down
		List<String> courses = service.getCourses();
		
		//get satus drop down
		List<String> status = service.getStatus();
		//creat binding oobj
		EnquirySearchCriteria form=new EnquirySearchCriteria();
		//set data in model obj
		model.addAttribute("courses",courses);
		model.addAttribute("status",status);
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam Integer id,Model model,EnquiryForm form)
	{
		eidi(model);
		StudentEnqEntity enq = service.getEnqies(id);
		form.setId(id);
		form.setName(enq.getSTUDENT_NAME());
		form.setPhno(enq.getPHNO());
		form.setCoursess(enq.getCOURSE_NAME());
		form.setClassMode(enq.getCLASS_MODE());
		form.setStatuss(enq.getENQUIRY_STATUS());
		model.addAttribute("form", form);
		
		return "enquiry";
	}

}
