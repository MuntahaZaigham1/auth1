package com.fastcode.example.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.application.core.t4.IT4AppService;
import com.fastcode.example.application.core.t4.dto.*;
import java.util.*;
import java.time.*;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/t4")
@RequiredArgsConstructor
public class T4Controller {

	@Qualifier("t4AppService")
	@NonNull protected final IT4AppService _t4AppService;
	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('T4ENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateT4Output> create( @RequestBody @Valid CreateT4Input t4) {
		CreateT4Output output=_t4AppService.create(t4);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	// ------------ Delete t4 ------------
	@PreAuthorize("hasAnyAuthority('T4ENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindT4ByIdOutput output = _t4AppService.findById(Integer.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("There does not exist a t4 with a id=%s", id)));

    	_t4AppService.delete(Integer.valueOf(id));
    }


	// ------------ Update t4 ------------
    @PreAuthorize("hasAnyAuthority('T4ENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateT4Output> update(@PathVariable String id,  @RequestBody @Valid UpdateT4Input t4) {

	    FindT4ByIdOutput currentT4 = _t4AppService.findById(Integer.valueOf(id));
		Optional.ofNullable(currentT4).orElseThrow(() -> new EntityNotFoundException(String.format("Unable to update. T4 with id=%s not found.", id)));


		t4.setVersiono(currentT4.getVersiono());
	    UpdateT4Output output = _t4AppService.update(Integer.valueOf(id),t4);
		return new ResponseEntity(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('T4ENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindT4ByIdOutput> findById(@PathVariable String id) {

    	FindT4ByIdOutput output = _t4AppService.findById(Integer.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

		return new ResponseEntity(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('T4ENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return ResponseEntity.ok(_t4AppService.find(searchCriteria,Pageable));
	}
	

}


