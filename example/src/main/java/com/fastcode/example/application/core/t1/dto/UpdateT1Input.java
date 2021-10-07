package com.fastcode.example.application.core.t1.dto;

import java.time.*;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateT1Input {

  	private char[] ca;
  	
  	private byte[] file;
  	public void setFile(MultipartFile mpfile) {
    	try {
			file = mpfile.getBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  	
  	private List<Double> flpa;
  	
  	private List<Double> fpa;
  	
  	@NotNull(message = "id Should not be null")
  	private Long id;
  	
 	@Length(max = 255, message = "inet must be less than 255 characters")
  	private String inet;
  	
 	@Length(max = 255, message = "jb must be less than 255 characters")
  	private String jb;
  	
 	@Length(max = 255, message = "jbf must be less than 255 characters")
  	private String jbf;
  	
  	private String str;
  	
  	private Long versiono;
  
}

