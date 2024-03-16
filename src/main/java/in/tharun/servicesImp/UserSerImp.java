package in.tharun.servicesImp;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tharun.binding.ForgotForm;
import in.tharun.binding.LoginForm;
import in.tharun.binding.SignUpForm;
import in.tharun.binding.UnlockForm;
import in.tharun.entities.UserDtlsEntity;
import in.tharun.repositories.UserDtlsRepo;
import in.tharun.services.UserService;
import in.tharun.utility.EmailUtils;
import in.tharun.utility.PwdUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.ServerEndpoint;

@Service
public class UserSerImp implements UserService {

	@Autowired
	private UserDtlsRepo repo;
	@Autowired
	private EmailUtils email;
	@Autowired
	private HttpSession session;
	
	public boolean singup(SignUpForm form) {
		
		UserDtlsEntity findByEmail = repo.findByEmail(form.getEmail());
		if(findByEmail !=null)
		{
			return false;
		}
		
		//TODO copy data from binding to entity
		UserDtlsEntity d=new UserDtlsEntity();
		BeanUtils.copyProperties(form, d);
		// TODO generate auto generated password
		
		String password = PwdUtils.password();
		d.setPWD(password);
		
		// TODO status is locked
		d.setACC_STATUS("LOCKED");
		
		// TODO insert record
		
		repo.save(d);
		// TODO eamil send 
		String to=d.getEmail();
		String subject = "unlock your account!!!!";
		StringBuffer body=new StringBuffer();
		body.append("<h1>use temparory password to unlock your account</h1>");
		body.append("temparory password:"+password);
		body.append("<br>");
		body.append("<a href=\"http://localhost:8181/unlock?email="+to+"\">click here to unlock your account</a>");
		email.mail(to, subject, body.toString());
		
		return true;
	}

	@Override
	public boolean unlock(UnlockForm unlk) {
		UserDtlsEntity entity = repo.findByEmail(unlk.getEmail());
		if(entity.getPWD().equals(unlk.getTempPwd()))
		{
			entity.setPWD(unlk.getNewPwd());
			entity.setACC_STATUS("unlocked");
			repo.save(entity);
			return true;
		}
		else
		{
			return true;
		}
		
	}
	@Override
	public String login(LoginForm form) {
		UserDtlsEntity entity = repo.findByEmailAndPWD(form.getEmail(),form.getPwd());
		if(entity==null)
		{
			return "invalid credentials";
		}
		if(entity.getACC_STATUS().equals("locked"))
		{
			return "please unlock your account first";
		}
		//todo : save user data in session 
		session.setAttribute("userid", entity.getUSER_ID());
		return "success";
	}
	@Override
	public boolean forgot(ForgotForm form) {
		UserDtlsEntity entity = repo.findByEmail(form.getEmail());
		if(entity==null)
		{
			
			return false;
		}
		else
		{
			String to=entity.getEmail();
			String subject="recover your password";
			StringBuffer b=new StringBuffer();
			b.append("<h1>use below password to login into your account</h1>");
			b.append("<br>");
			b.append("password: "+entity.getPWD());
			email.mail(to, subject, b.toString());
			return true;
		}
		
	}

}
