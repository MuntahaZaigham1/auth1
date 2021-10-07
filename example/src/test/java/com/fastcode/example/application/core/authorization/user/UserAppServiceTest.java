package com.fastcode.example.application.core.authorization.user;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.fastcode.example.domain.core.authorization.user.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.domain.core.authorization.user.QUser;
import com.fastcode.example.domain.core.authorization.user.User;

import com.fastcode.example.domain.core.authorization.userpreference.Userpreference;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceRepository;
import com.fastcode.example.domain.core.authorization.role.IRoleRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserAppServiceTest {

	@InjectMocks
	@Spy
	protected UserAppService _appService;
	@Mock
	protected IUserRepository _userRepository;
	
	
    @Mock
	protected IUserpreferenceRepository _userpreferenceRepository;

	@Mock
	protected IUserMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
	@Mock
	protected IRoleRepository _roleRepository;

    protected static Long ID=15L;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findUserById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<User> nullOptional = Optional.ofNullable(null);
		Mockito.when(_userRepository.findById(anyLong())).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findUserById_IdIsNotNullAndIdExists_ReturnUser() {

		User user = mock(User.class);
		Optional<User> userOptional = Optional.of((User) user);
		Userpreference userpreference = new Userpreference();
		Optional<Userpreference> userpreferenceOptional = Optional.of((Userpreference) userpreference);
		Mockito.when(_userpreferenceRepository.findById(ID )).thenReturn(userpreferenceOptional);
		Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);
		
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.userToFindUserByIdOutput(user,userpreference));
	}
	
	
	@Test 
    public void createUser_UserIsNotNullAndUserDoesNotExist_StoreUser() { 
 
        User userEntity = mock(User.class); 
    	CreateUserInput userInput = new CreateUserInput();
		
		Userpreference userpreference = new Userpreference();
		Optional<Userpreference> userpreferenceOptional = Optional.of((Userpreference) userpreference);
		Mockito.when(_userpreferenceRepository.findById(ID )).thenReturn(userpreferenceOptional);
		
        Mockito.when(_mapper.createUserInputToUser(any(CreateUserInput.class))).thenReturn(userEntity); 
        Mockito.when(_userRepository.save(any(User.class))).thenReturn(userEntity);

		Assertions.assertThat(_appService.create(userInput)).isEqualTo(_mapper.userToCreateUserOutput(userEntity,userpreference)); 

    } 
	@Test
	public void updateUser_UserIdIsNotNullAndIdExists_ReturnUpdatedUser() {

		User userEntity = mock(User.class);
		UpdateUserInput user= mock(UpdateUserInput.class);
		
		Optional<User> userOptional = Optional.of((User) userEntity);
		Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);
	 		
		Mockito.when(_mapper.updateUserInputToUser(any(UpdateUserInput.class))).thenReturn(userEntity);
		Mockito.when(_userRepository.save(any(User.class))).thenReturn(userEntity);
		Assertions.assertThat(_appService.update(ID,user)).isEqualTo(_mapper.userToUpdateUserOutput(userEntity));
	}
    
	@Test
	public void deleteUser_UserIsNotNullAndUserExists_UserRemoved() {

		User user = mock(User.class);
		Optional<User> userOptional = Optional.of((User) user);
		Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);
		Userpreference userpreference = mock(Userpreference.class);
		Optional<Userpreference> userpreferenceOptional = Optional.of((Userpreference) userpreference);
		Mockito.when(_userRepository.findById(anyLong())).thenReturn(userOptional);
		Mockito.when(_userpreferenceRepository.findById(anyLong())).thenReturn(userpreferenceOptional);
		doNothing().when(_userpreferenceRepository).delete(any(Userpreference.class));
 		
		_appService.delete(ID); 
		verify(_userRepository).delete(user);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<User> list = new ArrayList<>();
		Page<User> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_userRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<User> list = new ArrayList<>();
		User user = mock(User.class);
		list.add(user);
    	Page<User> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

	    Userpreference userpreference = new Userpreference();
		Optional<Userpreference> userpreferenceOptional = Optional.of((Userpreference) userpreference);
		Mockito.when(_userpreferenceRepository.findById(any(Long.class))).thenReturn(userpreferenceOptional);
		
		output.add(_mapper.userToFindUserByIdOutput(user, userpreference));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_userRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QUser user = QUser.userEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("emailAddress",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(user.emailAddress.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(user,map,searchMap)).isEqualTo(builder);
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
        list.add("emailAddress");
        list.add("firstName");
        list.add("lastName");
        list.add("password");
        list.add("phoneNumber");
        list.add("userName");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QUser user = QUser.userEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("emailAddress");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(user.emailAddress.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QUser.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}

	
	@Test 
	public void findUserByName_NameIsNotNullAndUserDoesNotExist_ReturnNull() {
	    Mockito.when(_userRepository.findByUserNameIgnoreCase(anyString())).thenReturn(null);	
		Assertions.assertThat(_appService.findByUserName("User1")).isEqualTo(null);	
	}

	@Test
	public void findUserByName_NameIsNotNullAndUserExists_ReturnAUser() {

		User user = mock(User.class);
	    Mockito.when(_userRepository.findByUserNameIgnoreCase(anyString())).thenReturn(user);
		Assertions.assertThat(_appService.findByUserName("User1")).isEqualTo(_mapper.userToFindUserByNameOutput(user));
	}
	@Test
	public void ParseuserpermissionsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("userId", keyString);
		Assertions.assertThat(_appService.parseUserpermissionsJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
	@Test
	public void ParseuserrolesJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("userId", keyString);
		Assertions.assertThat(_appService.parseUserrolesJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}
