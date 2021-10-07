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
import com.fastcode.example.application.core.t3.IT3AppService;
import com.fastcode.example.application.core.t3.dto.*;
import java.util.*;
import java.time.*;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/t3")
@RequiredArgsConstructor
public class T3Controller {

	@Qualifier("t3AppService")
	@NonNull protected final IT3AppService _t3AppService;
	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('T3ENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateT3Output> create( @RequestBody @Valid CreateT3Input t3) {
		CreateT3Output output=_t3AppService.create(t3);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	// ------------ Delete t3 ------------
	@PreAuthorize("hasAnyAuthority('T3ENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindT3ByIdOutput output = _t3AppService.findById(Integer.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("There does not exist a t3 with a id=%s", id)));

    	_t3AppService.delete(Integer.valueOf(id));
    }


	// ------------ Update t3 ------------
    @PreAuthorize("hasAnyAuthority('T3ENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateT3Output> update(@PathVariable String id,  @RequestBody @Valid UpdateT3Input t3) {

	    FindT3ByIdOutput currentT3 = _t3AppService.findById(Integer.valueOf(id));
		Optional.ofNullable(currentT3).orElseThrow(() -> new EntityNotFoundException(String.format("Unable to update. T3 with id=%s not found.", id)));


		t3.setVersiono(currentT3.getVersiono());
	    UpdateT3Output output = _t3AppService.update(Integer.valueOf(id),t3);
		return new ResponseEntity(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('T3ENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindT3ByIdOutput> findById(@PathVariable String id) {

    	FindT3ByIdOutput output = _t3AppService.findById(Integer.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

		return new ResponseEntity(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('T3ENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return ResponseEntity.ok(_t3AppService.find(searchCriteria,Pageable));
	}
	

}


