package com.fastcode.example.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.example.restcontrollers.core.UserController;
import com.fastcode.example.application.extended.authorization.user.IUserAppServiceExtended;
import com.fastcode.example.application.extended.authorization.userpermission.IUserpermissionAppServiceExtended;
import com.fastcode.example.application.extended.authorization.userrole.IUserroleAppServiceExtended;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fastcode.example.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/user/extended")
public class UserControllerExtended extends UserController {

		public UserControllerExtended(IUserAppServiceExtended userAppServiceExtended, IUserpermissionAppServiceExtended userpermissionAppServiceExtended, IUserroleAppServiceExtended userroleAppServiceExtended,
	    PasswordEncoder pEncoder,JWTAppService jwtAppService, LoggingHelper helper, Environment env) {
		super(
		userAppServiceExtended,
    	userpermissionAppServiceExtended,
    	userroleAppServiceExtended,
	    pEncoder,
	    jwtAppService,
		helper, env);
	}

	//Add your custom code here

}

