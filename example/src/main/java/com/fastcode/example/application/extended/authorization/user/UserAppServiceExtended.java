package com.fastcode.example.application.extended.authorization.user;

import org.springframework.stereotype.Service;
import com.fastcode.example.application.core.authorization.user.UserAppService;

import com.fastcode.example.domain.extended.authorization.user.IUserRepositoryExtended;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceRepository;
import com.fastcode.example.commons.logging.LoggingHelper;

@Service("userAppServiceExtended")
public class UserAppServiceExtended extends UserAppService implements IUserAppServiceExtended {

	public UserAppServiceExtended(IUserRepositoryExtended userRepositoryExtended,
				IUserpreferenceRepository userpreferenceRepository,IUserMapperExtended mapper,LoggingHelper logHelper) {

		super(userRepositoryExtended,
		userpreferenceRepository,mapper,logHelper);

	}

 	//Add your custom code here
 
}

