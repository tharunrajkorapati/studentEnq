package in.tharun.utility;

import org.apache.commons.lang3.RandomStringUtils;

public class PwdUtils
{
	public static String password()
	{
	String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	String pwd = RandomStringUtils.random( 8, characters );
	return pwd;
	  
	}
}
