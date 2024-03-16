package in.tharun.services;

import in.tharun.binding.ForgotForm;
import in.tharun.binding.LoginForm;
import in.tharun.binding.SignUpForm;
import in.tharun.binding.UnlockForm;

public interface UserService 
{
     public boolean singup(SignUpForm form);
     public boolean unlock(UnlockForm unlk);
     public String login(LoginForm form);
     public boolean forgot(ForgotForm form);
}
