package com.fastcode.example.application.core.t3;

import org.mapstruct.Mapper;
import com.fastcode.example.application.core.t3.dto.*;
import com.fastcode.example.domain.core.t3.T3;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IT3Mapper {
   T3 createT3InputToT3(CreateT3Input t3Dto);
   CreateT3Output t3ToCreateT3Output(T3 entity);
   
    T3 updateT3InputToT3(UpdateT3Input t3Dto);
    
   	UpdateT3Output t3ToUpdateT3Output(T3 entity);
   	FindT3ByIdOutput t3ToFindT3ByIdOutput(T3 entity);

}

