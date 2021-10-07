package com.fastcode.example.application.core.authorization.userpermission;

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

import com.fastcode.example.domain.core.authorization.userpermission.*;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.application.core.authorization.userpermission.dto.*;
import com.fastcode.example.domain.core.authorization.userpermission.QUserpermission;
import com.fastcode.example.domain.core.authorization.userpermission.Userpermission;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionId;

import com.fastcode.example.domain.core.authorization.permission.Permission;
import com.fastcode.example.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserpermissionAppServiceTest {

	@InjectMocks
	@Spy
	protected UserpermissionAppService _appService;
	@Mock
	protected IUserpermissionRepository _userpermissionRepository;
	
	
    @Mock
	protected IPermissionRepository _permissionRepository;

    @Mock
	protected IUserRepository _userRepository;

	@Mock
	protected IUserpermissionMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    @Mock
    protected UserpermissionId userpermissionId;
    
    private static final Long ID = 15L;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findUserpermissionById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Userpermission> nullOptional = Optional.ofNullable(null);
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(userpermissionId)).isEqualTo(null);
	}
	
	@Test
	public void findUserpermissionById_IdIsNotNullAndIdExists_ReturnUserpermission() {

		Userpermission userpermission = mock(Userpermission.class);
		Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermission);
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);
		
	    Assertions.assertThat(_appService.findById(userpermissionId)).isEqualTo(_mapper.userpermissionToFindUserpermissionByIdOutput(userpermission));
	}
	
	
	@Test 
    public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExist_StoreUserpermission() { 
 
        Userpermission userpermissionEntity = mock(Userpermission.class); 
    	CreateUserpermissionInput userpermissionInput = new CreateUserpermissionInput();
		
        Permission permission = mock(Permission.class);
		Optional<Permission> permissionOptional = Optional.of((Permission) permission);
        userpermissionInput.setPermissionId(15L);
		
        Mockito.when(_permissionRepository.findById(any(Long.class))).thenReturn(permissionOptional);
        
		
        User user = mock(User.class);
		Optional<User> userOptional = Optional.of((User) user);
        userpermissionInput.setUserId(15L);
		
        Mockito.when(_userRepository.findById(any(Long.class))).thenReturn(userOptional);
        
		
        Mockito.when(_mapper.createUserpermissionInputToUserpermission(any(CreateUserpermissionInput.class))).thenReturn(userpermissionEntity); 
        Mockito.when(_userpermissionRepository.save(any(Userpermission.class))).thenReturn(userpermissionEntity);

	    CreateUserpermissionOutput output = new CreateUserpermissionOutput();
        Mockito.when(_mapper.userpermissionToCreateUserpermissionOutput(any(Userpermission.class))).thenReturn(output);

	   	Assertions.assertThat(_appService.create(userpermissionInput)).isEqualTo(_mapper.userpermissionToCreateUserpermissionOutput(userpermissionEntity));

    } 
	@Test
	public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		CreateUserpermissionInput userpermission = mock(CreateUserpermissionInput.class);
		
		Mockito.when(_mapper.createUserpermissionInputToUserpermission(any(CreateUserpermissionInput.class))).thenReturn(null); 
		Assertions.assertThat(_appService.create(userpermission)).isEqualTo(null);
	}
	
	@Test
	public void createUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {

		CreateUserpermissionInput userpermission = new CreateUserpermissionInput();
	    
        userpermission.setPermissionId(15L);
     
     	Optional<Permission> nullOptional = Optional.ofNullable(null);
        Mockito.when(_permissionRepository.findById(any(Long.class))).thenReturn(nullOptional);
        
//		Mockito.when(_permissionRepository.findById(any(Long.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.create(userpermission)).isEqualTo(null);
    }
    
    @Test
	public void updateUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNullAndChildIsMandatory_ReturnNull() {

		UpdateUserpermissionInput userpermissionInput = mock(UpdateUserpermissionInput.class);
		Userpermission userpermission = mock(Userpermission.class); 
		
     	Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermission);
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);
		
		Mockito.when(_mapper.updateUserpermissionInputToUserpermission(any(UpdateUserpermissionInput.class))).thenReturn(userpermission); 
		Assertions.assertThat(_appService.update(userpermissionId,userpermissionInput)).isEqualTo(null);
	}
	
	@Test
	public void updateUserpermission_UserpermissionIsNotNullAndUserpermissionDoesNotExistAndChildIsNotNullAndChildIsMandatoryAndFindByIdIsNull_ReturnNull() {
		
		UpdateUserpermissionInput userpermissionInput = new UpdateUserpermissionInput();
        userpermissionInput.setPermissionId(15L);
     
    	Userpermission userpermission = mock(Userpermission.class);
		
     	Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermission);
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);
		
		Mockito.when(_mapper.updateUserpermissionInputToUserpermission(any(UpdateUserpermissionInput.class))).thenReturn(userpermission);
     	Optional<Permission> nullOptional = Optional.ofNullable(null);
        Mockito.when(_permissionRepository.findById(any(Long.class))).thenReturn(nullOptional);
        
	//	Mockito.when(_permissionRepository.findById(any(Long.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.update(userpermissionId,userpermissionInput)).isEqualTo(null);
	}

		
	@Test
	public void updateUserpermission_UserpermissionIdIsNotNullAndIdExists_ReturnUpdatedUserpermission() {

		Userpermission userpermissionEntity = mock(Userpermission.class);
		UpdateUserpermissionInput userpermission= mock(UpdateUserpermissionInput.class);
		
		Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermissionEntity);
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);
	 		
		Mockito.when(_mapper.updateUserpermissionInputToUserpermission(any(UpdateUserpermissionInput.class))).thenReturn(userpermissionEntity);
		Mockito.when(_userpermissionRepository.save(any(Userpermission.class))).thenReturn(userpermissionEntity);
		Assertions.assertThat(_appService.update(userpermissionId,userpermission)).isEqualTo(_mapper.userpermissionToUpdateUserpermissionOutput(userpermissionEntity));
	}
    
	@Test
	public void deleteUserpermission_UserpermissionIsNotNullAndUserpermissionExists_UserpermissionRemoved() {

		Userpermission userpermission = mock(Userpermission.class);
		Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermission);
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);
 		
		_appService.delete(userpermissionId); 
		verify(_userpermissionRepository).delete(userpermission);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Userpermission> list = new ArrayList<>();
		Page<Userpermission> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserpermissionByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_userpermissionRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Userpermission> list = new ArrayList<>();
		Userpermission userpermission = mock(Userpermission.class);
		list.add(userpermission);
    	Page<Userpermission> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindUserpermissionByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.userpermissionToFindUserpermissionByIdOutput(userpermission));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_userpermissionRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QUserpermission userpermission = QUserpermission.userpermissionEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(userpermission,map,searchMap)).isEqualTo(builder);
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
		QUserpermission userpermission = QUserpermission.userpermissionEntity;
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
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QUserpermission.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Permission
	@Test
	public void GetPermission_IfUserpermissionIdAndPermissionIdIsNotNullAndUserpermissionExists_ReturnPermission() {
		Userpermission userpermission = mock(Userpermission.class);
		Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermission);
		Permission permissionEntity = mock(Permission.class);

		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);

		Mockito.when(userpermission.getPermission()).thenReturn(permissionEntity);
		Assertions.assertThat(_appService.getPermission(userpermissionId)).isEqualTo(_mapper.permissionToGetPermissionOutput(permissionEntity, userpermission));
	}

	@Test 
	public void GetPermission_IfUserpermissionIdAndPermissionIdIsNotNullAndUserpermissionDoesNotExist_ReturnNull() {
		Optional<Userpermission> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getPermission(userpermissionId)).isEqualTo(null);
	}
   
    //User
	@Test
	public void GetUser_IfUserpermissionIdAndUserIdIsNotNullAndUserpermissionExists_ReturnUser() {
		Userpermission userpermission = mock(Userpermission.class);
		Optional<Userpermission> userpermissionOptional = Optional.of((Userpermission) userpermission);
		User userEntity = mock(User.class);

		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(userpermissionOptional);

		Mockito.when(userpermission.getUser()).thenReturn(userEntity);
		Assertions.assertThat(_appService.getUser(userpermissionId)).isEqualTo(_mapper.userToGetUserOutput(userEntity, userpermission));
	}

	@Test 
	public void GetUser_IfUserpermissionIdAndUserIdIsNotNullAndUserpermissionDoesNotExist_ReturnNull() {
		Optional<Userpermission> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_userpermissionRepository.findById(any(UserpermissionId.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getUser(userpermissionId)).isEqualTo(null);
	}

  
	@Test
	public void ParseUserpermissionKey_KeysStringIsNotEmptyAndKeyValuePairExists_ReturnUserpermissionId()
	{
		String keyString= "permissionId=15,userId=15";
	
		UserpermissionId userpermissionId = new UserpermissionId();
        userpermissionId.setPermissionId(15L);
        userpermissionId.setUserId(15L);

		Assertions.assertThat(_appService.parseUserpermissionKey(keyString)).isEqualToComparingFieldByField(userpermissionId);
	}
	
	@Test
	public void ParseUserpermissionKey_KeysStringIsEmpty_ReturnNull()
	{
		String keyString= "";
		Assertions.assertThat(_appService.parseUserpermissionKey(keyString)).isEqualTo(null);
	}
	
	@Test
	public void ParseUserpermissionKey_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
		String keyString= "permissionId";

		Assertions.assertThat(_appService.parseUserpermissionKey(keyString)).isEqualTo(null);
	}
}
