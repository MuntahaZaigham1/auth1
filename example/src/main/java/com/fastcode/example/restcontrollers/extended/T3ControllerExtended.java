package com.fastcode.example.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.fastcode.example.restcontrollers.core.T3Controller;
import com.fastcode.example.application.extended.t3.IT3AppServiceExtended;
import org.springframework.core.env.Environment;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/t3/extended")
public class T3ControllerExtended extends T3Controller {

		public T3ControllerExtended(IT3AppServiceExtended t3AppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		t3AppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

