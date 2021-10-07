package com.fastcode.example.application.core.t4;

import org.mapstruct.Mapper;
import com.fastcode.example.application.core.t4.dto.*;
import com.fastcode.example.domain.core.t4.T4;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IT4Mapper {
   T4 createT4InputToT4(CreateT4Input t4Dto);
   CreateT4Output t4ToCreateT4Output(T4 entity);
   
    T4 updateT4InputToT4(UpdateT4Input t4Dto);
    
   	UpdateT4Output t4ToUpdateT4Output(T4 entity);
   	FindT4ByIdOutput t4ToFindT4ByIdOutput(T4 entity);

}

