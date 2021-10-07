package com.fastcode.example.application.extended.authorization.userrole;

import org.springframework.stereotype.Service;
import com.fastcode.example.application.core.authorization.userrole.UserroleAppService;

import com.fastcode.example.domain.extended.authorization.userrole.IUserroleRepositoryExtended;
import com.fastcode.example.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.fastcode.example.domain.extended.authorization.user.IUserRepositoryExtended;
import com.fastcode.example.commons.logging.LoggingHelper;

@Service("userroleAppServiceExtended")
public class UserroleAppServiceExtended extends UserroleAppService implements IUserroleAppServiceExtended {

	public UserroleAppServiceExtended(IUserroleRepositoryExtended userroleRepositoryExtended,
				IRoleRepositoryExtended roleRepositoryExtended,IUserRepositoryExtended userRepositoryExtended,IUserroleMapperExtended mapper,LoggingHelper logHelper) {

		super(userroleRepositoryExtended,
		roleRepositoryExtended,userRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

