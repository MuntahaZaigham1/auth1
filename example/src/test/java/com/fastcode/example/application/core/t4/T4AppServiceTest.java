package com.fastcode.example.application.core.t4;

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

import com.fastcode.example.domain.core.t4.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.core.t4.dto.*;
import com.fastcode.example.domain.core.t4.QT4;
import com.fastcode.example.domain.core.t4.T4;

import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class T4AppServiceTest {

	@InjectMocks
	@Spy
	protected T4AppService _appService;
	@Mock
	protected IT4Repository _t4Repository;
	
	
	@Mock
	protected IT4Mapper _mapper;

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
	public void findT4ById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<T4> nullOptional = Optional.ofNullable(null);
		Mockito.when(_t4Repository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findT4ById_IdIsNotNullAndIdExists_ReturnT4() {

		T4 t4 = mock(T4.class);
		Optional<T4> t4Optional = Optional.of((T4) t4);
		Mockito.when(_t4Repository.findById(any(Integer.class))).thenReturn(t4Optional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.t4ToFindT4ByIdOutput(t4));
	}
	
	
	@Test 
    public void createT4_T4IsNotNullAndT4DoesNotExist_StoreT4() { 
 
        T4 t4Entity = mock(T4.class); 
    	CreateT4Input t4Input = new CreateT4Input();
		
        Mockito.when(_mapper.createT4InputToT4(any(CreateT4Input.class))).thenReturn(t4Entity); 
        Mockito.when(_t4Repository.save(any(T4.class))).thenReturn(t4Entity);

	   	Assertions.assertThat(_appService.create(t4Input)).isEqualTo(_mapper.t4ToCreateT4Output(t4Entity));

    } 
	@Test
	public void updateT4_T4IdIsNotNullAndIdExists_ReturnUpdatedT4() {

		T4 t4Entity = mock(T4.class);
		UpdateT4Input t4= mock(UpdateT4Input.class);
		
		Optional<T4> t4Optional = Optional.of((T4) t4Entity);
		Mockito.when(_t4Repository.findById(any(Integer.class))).thenReturn(t4Optional);
	 		
		Mockito.when(_mapper.updateT4InputToT4(any(UpdateT4Input.class))).thenReturn(t4Entity);
		Mockito.when(_t4Repository.save(any(T4.class))).thenReturn(t4Entity);
		Assertions.assertThat(_appService.update(ID,t4)).isEqualTo(_mapper.t4ToUpdateT4Output(t4Entity));
	}
    
	@Test
	public void deleteT4_T4IsNotNullAndT4Exists_T4Removed() {

		T4 t4 = mock(T4.class);
		Optional<T4> t4Optional = Optional.of((T4) t4);
		Mockito.when(_t4Repository.findById(any(Integer.class))).thenReturn(t4Optional);
 		
		_appService.delete(ID); 
		verify(_t4Repository).delete(t4);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<T4> list = new ArrayList<>();
		Page<T4> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindT4ByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_t4Repository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<T4> list = new ArrayList<>();
		T4 t4 = mock(T4.class);
		list.add(t4);
    	Page<T4> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindT4ByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.t4ToFindT4ByIdOutput(t4));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_t4Repository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QT4 t4 = QT4.t4Entity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(t4,map,searchMap)).isEqualTo(builder);
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
		QT4 t4 = QT4.t4Entity;
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
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QT4.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}

}
