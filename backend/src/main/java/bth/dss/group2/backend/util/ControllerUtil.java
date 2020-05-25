package bth.dss.group2.backend.util;

import java.security.Principal;

import org.springframework.security.core.userdetails.UserDetails;

public class ControllerUtil {
	public static String getLoginName(Principal principal) {
		return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.getName();
	}
}
