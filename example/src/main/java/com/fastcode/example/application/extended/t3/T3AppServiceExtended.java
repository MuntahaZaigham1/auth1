package com.fastcode.example.application.extended.t3;

import org.springframework.stereotype.Service;
import com.fastcode.example.application.core.t3.T3AppService;

import com.fastcode.example.domain.extended.t3.IT3RepositoryExtended;
import com.fastcode.example.commons.logging.LoggingHelper;

@Service("t3AppServiceExtended")
public class T3AppServiceExtended extends T3AppService implements IT3AppServiceExtended {

	public T3AppServiceExtended(IT3RepositoryExtended t3RepositoryExtended,
				IT3MapperExtended mapper,LoggingHelper logHelper) {

		super(t3RepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

