package com.fastcode.example.application.core.t3;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fastcode.example.domain.core.t3.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.core.t3.dto.*;
import com.fastcode.example.domain.core.t3.QT3;
import com.fastcode.example.domain.core.t3.T3;

import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class T3AppServiceTest {

	@InjectMocks
	@Spy
	protected T3AppService _appService;
	@Mock
	protected IT3Repository _t3Repository;
	
	
	@Mock
	protected IT3Mapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    protected static Integer ID=15;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findT3ById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<T3> nullOptional = Optional.ofNullable(null);
		Mockito.when(_t3Repository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findT3ById_IdIsNotNullAndIdExists_ReturnT3() {

		T3 t3 = mock(T3.class);
		Optional<T3> t3Optional = Optional.of((T3) t3);
		Mockito.when(_t3Repository.findById(any(Integer.class))).thenReturn(t3Optional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.t3ToFindT3ByIdOutput(t3));
	}
	
	
	@Test 
    public void createT3_T3IsNotNullAndT3DoesNotExist_StoreT3() { 
 
        T3 t3Entity = mock(T3.class); 
    	CreateT3Input t3Input = new CreateT3Input();
		
        Mockito.when(_mapper.createT3InputToT3(any(CreateT3Input.class))).thenReturn(t3Entity); 
        Mockito.when(_t3Repository.save(any(T3.class))).thenReturn(t3Entity);

	   	Assertions.assertThat(_appService.create(t3Input)).isEqualTo(_mapper.t3ToCreateT3Output(t3Entity));

    } 
	@Test
	public void updateT3_T3IdIsNotNullAndIdExists_ReturnUpdatedT3() {

		T3 t3Entity = mock(T3.class);
		UpdateT3Input t3= mock(UpdateT3Input.class);
		
		Optional<T3> t3Optional = Optional.of((T3) t3Entity);
		Mockito.when(_t3Repository.findById(any(Integer.class))).thenReturn(t3Optional);
	 		
		Mockito.when(_mapper.updateT3InputToT3(any(UpdateT3Input.class))).thenReturn(t3Entity);
		Mockito.when(_t3Repository.save(any(T3.class))).thenReturn(t3Entity);
		Assertions.assertThat(_appService.update(ID,t3)).isEqualTo(_mapper.t3ToUpdateT3Output(t3Entity));
	}
    
	@Test
	public void deleteT3_T3IsNotNullAndT3Exists_T3Removed() {

		T3 t3 = mock(T3.class);
		Optional<T3> t3Optional = Optional.of((T3) t3);
		Mockito.when(_t3Repository.findById(any(Integer.class))).thenReturn(t3Optional);
 		
		_appService.delete(ID); 
		verify(_t3Repository).delete(t3);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<T3> list = new ArrayList<>();
		Page<T3> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindT3ByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_t3Repository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<T3> list = new ArrayList<>();
		T3 t3 = mock(T3.class);
		list.add(t3);
    	Page<T3> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindT3ByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.t3ToFindT3ByIdOutput(t3));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_t3Repository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QT3 t3 = QT3.t3Entity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(t3,map,searchMap)).isEqualTo(builder);
	}
	
	@Test (expected = Exception.class)
	public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("xyz");
		_appService.checkProperties(list);
	}
	
	@Test
	public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
		List<String> list = new ArrayList<>();
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QT3 t3 = QT3.t3Entity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QT3.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}

}
