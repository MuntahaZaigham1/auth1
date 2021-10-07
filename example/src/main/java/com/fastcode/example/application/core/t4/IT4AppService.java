package com.fastcode.example.application.core.t4;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.application.core.t4.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;

import java.util.*;

public interface IT4AppService {
	
	//CRUD Operations
	CreateT4Output create(CreateT4Input t4);

    void delete(Integer id);

    UpdateT4Output update(Integer id, UpdateT4Input input);

    FindT4ByIdOutput findById(Integer id);
    List<FindT4ByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
    //Join Column Parsers
}

