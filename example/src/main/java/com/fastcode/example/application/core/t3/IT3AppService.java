package com.fastcode.example.application.core.t3;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.core.t3.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;

import java.util.*;

public interface IT3AppService {
	
	//CRUD Operations
	CreateT3Output create(CreateT3Input t3);

    void delete(Integer id);

    UpdateT3Output update(Integer id, UpdateT3Input input);

    FindT3ByIdOutput findById(Integer id);
    List<FindT3ByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
    //Join Column Parsers
}

