package com.fastcode.example.application.core.authorization.userrole;

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

import com.fastcode.example.domain.core.authorization.userrole.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.domain.core.authorization.userrole.QUserrole;
import com.fastcode.example.domain.core.authorization.userrole.Userrole;
import com.fastcode.example.domain.core.authorization.userrole.UserroleId;

import com.fastcode.example.domain.core.authorization.role.Role;
import com.fastcode.example.domain.core.authorization.role.IRoleRepository;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserroleAppServiceTest {

	@InjectMocks
	@Spy
	protected UserroleAppService _appService;
	@Mock
	protected IUserroleRepository _userroleRepository;
	
	
    @Mock
	protected IRoleRepository _roleRepository;

    @Mock
	protected IUserRepository _userRepository;

	@Mock
	protected IUserroleMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    @Mock
    protected UserroleId userroleId;
    
    private static final Long ID = 15L;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findUserroleById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Userrole> nullOptional = Optional.ofNullable(null);
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(userroleId)).isEqualTo(null);
	}
	
	@Test
	public void findUserroleById_IdIsNotNullAndIdExists_ReturnUserrole() {

		Userrole userrole = mock(Userrole.class);
		Optional<Userrole> userroleOptional = Optional.of((Userrole) userrole);
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
		
	    Assertions.assertThat(_appService.findById(userroleId)).isEqualTo(_mapper.userroleToFindUserroleByIdOutput(userrole));
	}
	
	
	@Test 
    public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExist_StoreUserrole() { 
 
        Userrole userroleEntity = mock(Userrole.class); 
    	CreateUserroleInput userroleInput = new CreateUserroleInput();
		
        Role role = mock(Role.class);
		Optional<Role> roleOptional = Optional.of((Role) role);
        userroleInput.setRoleId(15L);
		
        Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(roleOptional);
        
		
        User user = mock(User.class);
		Optional<User> userOptional = Optional.of((User) user);
        userroleInput.setUserId(15L);
		
        Mockito.when(_userRepository.findById(any(Long.class))).thenReturn(userOptional);
        
		
        Mockito.when(_mapper.createUserroleInputToUserrole(any(CreateUserroleInput.class))).thenReturn(userroleEntity); 
        Mockito.when(_userroleRepository.save(any(Userrole.class))).thenReturn(userroleEntity);

	   	Assertions.assertThat(_appService.create(userroleInput)).isEqualTo(_mapper.userroleToCreateUserroleOutput(userroleEntity));

    } 
	@Test
	public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		CreateUserroleInput userrole = mock(CreateUserroleInput.class);
		
		Mockito.when(_mapper.createUserroleInputToUserrole(any(CreateUserroleInput.class))).thenReturn(null); 
		Assertions.assertThat(_appService.create(userrole)).isEqualTo(null);
	}
	
	@Test
	public void createUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {

		CreateUserroleInput userrole = new CreateUserroleInput();
	    
        userrole.setRoleId(15L);
     
     	Optional<Role> nullOptional = Optional.ofNullable(null);
        Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(nullOptional);
        
//		Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.create(userrole)).isEqualTo(null);
    }
    
    @Test
	public void updateUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		UpdateUserroleInput userroleInput = mock(UpdateUserroleInput.class);
		Userrole userrole = mock(Userrole.class); 
		
     	Optional<Userrole> userroleOptional = Optional.of((Userrole) userrole);
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
		
		Mockito.when(_mapper.updateUserroleInputToUserrole(any(UpdateUserroleInput.class))).thenReturn(userrole); 
		Assertions.assertThat(_appService.update(userroleId,userroleInput)).isEqualTo(null);
	}
	
	@Test
	public void updateUserrole_UserroleIsNotNullAndUserroleDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
		
		UpdateUserroleInput userroleInput = new UpdateUserroleInput();
        userroleInput.setRoleId(15L);
     
    	Userrole userrole = mock(Userrole.class);
		
     	Optional<Userrole> userroleOptional = Optional.of((Userrole) userrole);
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
		
		Mockito.when(_mapper.updateUserroleInputToUserrole(any(UpdateUserroleInput.class))).thenReturn(userrole);
     	Optional<Role> nullOptional = Optional.ofNullable(null);
        Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(nullOptional);
        
	//	Mockito.when(_roleRepository.findById(any(Long.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.update(userroleId,userroleInput)).isEqualTo(null);
	}

		
	@Test
	public void updateUserrole_UserroleIdIsNotNullAndIdExists_ReturnUpdatedUserrole() {

		Userrole userroleEntity = mock(Userrole.class);
		UpdateUserroleInput userrole= mock(UpdateUserroleInput.class);
		
		Optional<Userrole> userroleOptional = Optional.of((Userrole) userroleEntity);
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
	 		
		Mockito.when(_mapper.updateUserroleInputToUserrole(any(UpdateUserroleInput.class))).thenReturn(userroleEntity);
		Mockito.when(_userroleRepository.save(any(Userrole.class))).thenReturn(userroleEntity);
		Assertions.assertThat(_appService.update(userroleId,userrole)).isEqualTo(_mapper.userroleToUpdateUserroleOutput(userroleEntity));
	}
    
	@Test
	public void deleteUserrole_UserroleIsNotNullAndUserroleExists_UserroleRemoved() {

		Userrole userrole = mock(Userrole.class);
		Optional<Userrole> userroleOptional = Optional.of((Userrole) userrole);
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);
 		
		_appService.delete(userroleId); 
		verify(_userroleRepository).delete(userrole);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Userrole> list = new ArrayList<>();
		Page<Userrole> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserroleByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_userroleRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Userrole> list = new ArrayList<>();
		Userrole userrole = mock(Userrole.class);
		list.add(userrole);
    	Page<Userrole> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserroleByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.userroleToFindUserroleByIdOutput(userrole));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_userroleRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QUserrole userrole = QUserrole.userroleEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(userrole,map,searchMap)).isEqualTo(builder);
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
		QUserrole userrole = QUserrole.userroleEntity;
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
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QUserrole.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Role
	@Test
	public void GetRole_IfUserroleIdAndRoleIdIsNotNullAndUserroleExists_ReturnRole() {
		Userrole userrole = mock(Userrole.class);
		Optional<Userrole> userroleOptional = Optional.of((Userrole) userrole);
		Role roleEntity = mock(Role.class);

		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);

		Mockito.when(userrole.getRole()).thenReturn(roleEntity);
		Assertions.assertThat(_appService.getRole(userroleId)).isEqualTo(_mapper.roleToGetRoleOutput(roleEntity, userrole));
	}

	@Test 
	public void GetRole_IfUserroleIdAndRoleIdIsNotNullAndUserroleDoesNotExist_ReturnNull() {
		Optional<Userrole> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getRole(userroleId)).isEqualTo(null);
	}
   
    //User
	@Test
	public void GetUser_IfUserroleIdAndUserIdIsNotNullAndUserroleExists_ReturnUser() {
		Userrole userrole = mock(Userrole.class);
		Optional<Userrole> userroleOptional = Optional.of((Userrole) userrole);
		User userEntity = mock(User.class);

		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(userroleOptional);

		Mockito.when(userrole.getUser()).thenReturn(userEntity);
		Assertions.assertThat(_appService.getUser(userroleId)).isEqualTo(_mapper.userToGetUserOutput(userEntity, userrole));
	}

	@Test 
	public void GetUser_IfUserroleIdAndUserIdIsNotNullAndUserroleDoesNotExist_ReturnNull() {
		Optional<Userrole> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_userroleRepository.findById(any(UserroleId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getUser(userroleId)).isEqualTo(null);
	}

  
	@Test
	public void ParseUserroleKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnUserroleId()
	{
		String keyString= "roleId=15,userId=15";
	
		UserroleId userroleId = new UserroleId();
        userroleId.setRoleId(15L);
        userroleId.setUserId(15L);

		Assertions.assertThat(_appService.parseUserroleKey(keyString)).isEqualToComparingFieldByField(userroleId);
	}
	
	@Test
	public void ParseUserroleKey_KeysStringIsEmpty_ReturnNull()
	{
		String keyString= "";
		Assertions.assertThat(_appService.parseUserroleKey(keyString)).isEqualTo(null);
	}
	
	@Test
	public void ParseUserroleKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
		String keyString= "roleId";

		Assertions.assertThat(_appService.parseUserroleKey(keyString)).isEqualTo(null);
	}
}
