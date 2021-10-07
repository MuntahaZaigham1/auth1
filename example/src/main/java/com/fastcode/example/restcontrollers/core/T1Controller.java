package com.fastcode.example.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import java.lang.reflect.InvocationTargetException;
import com.fastcode.example.commons.search.SearchCriteria;
import com.fastcode.example.commons.search.SearchUtils;
import com.fastcode.example.commons.search.OffsetBasedPageRequest;
import com.fastcode.example.application.core.t1.IT1AppService;
import com.fastcode.example.application.core.t1.dto.*;
import java.util.*;
import java.time.*;
import com.fastcode.example.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/t1")
@RequiredArgsConstructor
public class T1Controller {

	@Qualifier("t1AppService")
	@NonNull protected final IT1AppService _t1AppService;
	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('T1ENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST,  produces = {"application/json"})
	public ResponseEntity<CreateT1Output> create(@ModelAttribute  CreateT1Input t1) {
		CreateT1Output output=_t1AppService.create(t1);
		return new ResponseEntity(output, HttpStatus.OK);
	}

	// ------------ Delete t1 ------------
	@PreAuthorize("hasAnyAuthority('T1ENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindT1ByIdOutput output = _t1AppService.findById(Long.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("There does not exist a t1 with a id=%s", id)));

    	_t1AppService.delete(Long.valueOf(id));
    }


	// ------------ Update t1 ------------
    @PreAuthorize("hasAnyAuthority('T1ENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateT1Output> update(@PathVariable String id, @ModelAttribute  UpdateT1Input t1) {

	    FindT1ByIdOutput currentT1 = _t1AppService.findById(Long.valueOf(id));
		Optional.ofNullable(currentT1).orElseThrow(() -> new EntityNotFoundException(String.format("Unable to update. T1 with id=%s not found.", id)));


		t1.setVersiono(currentT1.getVersiono());
	    UpdateT1Output output = _t1AppService.update(Long.valueOf(id),t1);
		return new ResponseEntity(output, HttpStatus.OK);
	}
    
	@GetMapping("/download/{id:.+}/{fieldName}")
    public ResponseEntity downloadFromDB(@PathVariable String id, @PathVariable String fieldName) {
    	FindT1ByIdOutput output = _t1AppService.findById(Long.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));
    	
    	try {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fieldName + "\"")
					.body(PropertyUtils.getProperty(output, fieldName));
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EntityNotFoundException(String.format("Not found"));
		}
    }

    @PreAuthorize("hasAnyAuthority('T1ENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindT1ByIdOutput> findById(@PathVariable String id) {

    	FindT1ByIdOutput output = _t1AppService.findById(Long.valueOf(id));
    	Optional.ofNullable(output).orElseThrow(() -> new EntityNotFoundException(String.format("Not found")));

		return new ResponseEntity(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('T1ENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws Exception {

		if (offset == null) { offset = env.getProperty("fastCode.offset.default"); }
		if (limit == null) { limit = env.getProperty("fastCode.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return ResponseEntity.ok(_t1AppService.find(searchCriteria,Pageable));
	}
	

}


