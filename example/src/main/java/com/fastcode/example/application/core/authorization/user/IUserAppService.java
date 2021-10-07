package com.fastcode.example.application.core.authorization.user;

import org.springframework.data.domain.Pageable;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.authorization.userpreference.Userpreference;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.commons.search.SearchCriteria;

import java.util.*;

public interface IUserAppService {
	
	//CRUD Operations
	CreateUserOutput create(CreateUserInput user);

    void delete(Long id);

    UpdateUserOutput update(Long id, UpdateUserInput input);

    FindUserByIdOutput findById(Long id);
    List<FindUserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception;
    
   	Userpreference createDefaultUserPreference(User user);
   	
   	void updateTheme(User user, String theme);
   	
   	void updateLanguage(User user, String language);
    
    void updateUserData(FindUserWithAllFieldsByIdOutput user);
	
	UserProfile updateUserProfile(FindUserWithAllFieldsByIdOutput user, UserProfile userProfile);
	
	FindUserWithAllFieldsByIdOutput findWithAllFieldsById(Long userId);
	
	UserProfile getProfile(FindUserByIdOutput user);
	 
	User getUser();
    
	FindUserByNameOutput findByUserName(String userName);
    
	FindUserByNameOutput findByEmailAddress(String emailAddress);
    
    //Join Column Parsers

	Map<String,String> parseUserpermissionsJoinColumn(String keysString);

	Map<String,String> parseUserrolesJoinColumn(String keysString);
}

