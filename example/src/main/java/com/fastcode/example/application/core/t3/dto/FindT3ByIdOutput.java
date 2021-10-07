package com.fastcode.example.application.core.t3.dto;

import java.time.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindT3ByIdOutput {

  	private Integer id;
  	private List<Integer> score;
	private Long versiono;
 
}

