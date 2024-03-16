package in.tharun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import in.tharun.binding.LoginForm;
import in.tharun.binding.SignUpForm;
import in.tharun.binding.UnlockForm;
import in.tharun.binding.ForgotForm;
import in.tharun.services.UserService;

@Controller
public class UserController
{
	@Autowired
	private UserService service;
	
	@GetMapping("/registor")
    public String registorPage(Model model)
    {
		model.addAttribute("user",new SignUpForm());
    	return "registor";
    }
	
	@PostMapping("/registor")
	public String handleSingup(@ModelAttribute("user") SignUpForm from,Model model)
	{
		boolean singup = service.singup(from);
		if (singup) {
			model.addAttribute("success","account created ,pls check your email");
		} else {
              model.addAttribute("error", "enter unic email id");
		}
		
		return "registor";
	}
	
	@GetMapping("/unlock")
    public String unlockPage(@RequestParam String email,Model model)
    {
		UnlockForm ul=new UnlockForm();
		ul.setEmail(email);
		model.addAttribute("unlock",ul);
    	return "unlock";
    }
	@PostMapping("/unlock")
	public String unlock(@ModelAttribute("unlock") UnlockForm form,Model model)
	{
		  System.out.println(form);
		  if(form.getNewPwd().equals(form.getConfPwd()))
		  {
			  boolean status = service.unlock(form);
			  if (status) {
				model.addAttribute("s","your account is successfuly unlocked ");
			} else {
                 model.addAttribute("e","your password is incorrect,check your email");
			}
		  }
		  else
		  {
			  model.addAttribute("error", "password must be same");
		  }
	      return "unlock";	
	}
	
	@GetMapping("/login")
    public String loginPage(LoginForm form , Model model)
    {
		model.addAttribute("loginform",new LoginForm());
    	return "login";
    }
	@PostMapping("/login")
	public String login(@ModelAttribute("loginform") LoginForm form,Model model)
	{
		String string = service.login(form);
		if(string.contains("invalid"))
		{
			model.addAttribute("error","invalid credentials");
		}
		if(string.contains("please unlock your account first"))
		{
			model.addAttribute("unlock", "please unlock your account first");
		}
		if(string.contains("success"))
		{
			return "redirect:/dashboard";
		}
		return "login";
	}
	@GetMapping("/forgot")
    public String forgot(ForgotForm form,Model model)
    {
		model.addAttribute("forgot", new ForgotForm());
    	return "forgot";
    }
	@PostMapping("/forgot")
	public String forgotpwd(@ModelAttribute("forgot") ForgotForm form,Model model)
	{
	    boolean forgot = service.forgot(form);	
	    if(forgot)
	    {
	    	model.addAttribute("success","check your eamil");
	    }
	    else
	    {
	    	model.addAttribute("error","your mail id is invalid");
	    }
		return "forgot";
	}
	
}
