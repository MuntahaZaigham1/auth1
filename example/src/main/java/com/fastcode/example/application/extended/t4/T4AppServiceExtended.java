package com.fastcode.example.application.extended.t4;

import org.springframework.stereotype.Service;
import com.fastcode.example.application.core.t4.T4AppService;

import com.fastcode.example.domain.extended.t4.IT4RepositoryExtended;
import com.fastcode.example.commons.logging.LoggingHelper;

@Service("t4AppServiceExtended")
public class T4AppServiceExtended extends T4AppService implements IT4AppServiceExtended {

	public T4AppServiceExtended(IT4RepositoryExtended t4RepositoryExtended,
				IT4MapperExtended mapper,LoggingHelper logHelper) {

		super(t4RepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

