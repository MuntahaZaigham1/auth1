package com.fastcode.example.application.core.t4.dto;

import java.time.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateT4Output {

    private Integer id;
    private List<Integer> score;

}

