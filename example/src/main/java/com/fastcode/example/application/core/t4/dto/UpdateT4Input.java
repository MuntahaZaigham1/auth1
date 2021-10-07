package com.fastcode.example.application.core.t4.dto;

import java.time.*;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateT4Input {

  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	private List<Integer> score;
  	
  	private Long versiono;
  
}

