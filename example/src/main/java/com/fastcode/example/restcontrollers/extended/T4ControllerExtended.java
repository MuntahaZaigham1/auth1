package com.fastcode.example.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.example.restcontrollers.core.T4Controller;
import com.fastcode.example.application.extended.t4.IT4AppServiceExtended;
import org.springframework.core.env.Environment;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/t4/extended")
public class T4ControllerExtended extends T4Controller {

		public T4ControllerExtended(IT4AppServiceExtended t4AppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		t4AppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

