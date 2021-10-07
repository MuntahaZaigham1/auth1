package com.fastcode.example.application.core.authorization.user;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.QUser;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.authorization.userpreference.IUserpreferenceRepository;
import com.fastcode.example.domain.core.authorization.userpreference.Userpreference;

import com.fastcode.example.security.SecurityUtils; 
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.fastcode.example.commons.search.*;
import com.fastcode.example.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.time.*;
import java.util.*;

@Service("userAppService")
@RequiredArgsConstructor
public class UserAppService implements IUserAppService {
	public static final long PASSWORD_TOKEN_EXPIRATION_TIME = 3_600_000; // 1 hour

	@Qualifier("userRepository")
	@NonNull protected final IUserRepository _userRepository;

	
    @Qualifier("userpreferenceRepository")
	@NonNull protected final IUserpreferenceRepository _userpreferenceRepository;

	@Qualifier("IUserMapperImpl")
	@NonNull protected final IUserMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateUserOutput create(CreateUserInput input) {

		User user = mapper.createUserInputToUser(input);

		User createdUser = _userRepository.save(user);
		Userpreference userPreference = createDefaultUserPreference(createdUser);
		return mapper.userToCreateUserOutput(createdUser,userPreference);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateUserOutput update(Long userId, UpdateUserInput input) {

		User existing = _userRepository.findById(userId).get();

		User user = mapper.updateUserInputToUser(input);
		user.setTokenverificationsSet(existing.getTokenverificationsSet());
		user.setUserpermissionsSet(existing.getUserpermissionsSet());
		user.setUserrolesSet(existing.getUserrolesSet());
		
		User updatedUser = _userRepository.save(user);
		return mapper.userToUpdateUserOutput(updatedUser);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long userId) {

		User existing = _userRepository.findById(userId).orElse(null); 
		
    	Userpreference userpreference = _userpreferenceRepository.findById(userId).orElse(null);
    	_userpreferenceRepository.delete(userpreference);
	 	
	 	_userRepository.delete(existing);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindUserByIdOutput findById(Long userId) {

		User foundUser = _userRepository.findById(userId).orElse(null);
		if (foundUser == null)  
			return null; 
 	   
		Userpreference userPreference =_userpreferenceRepository.findById(userId).orElse(null);
		
 	    return mapper.userToFindUserByIdOutput(foundUser,userPreference);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
   	public Userpreference createDefaultUserPreference(User user) {
    	
    	Userpreference userpreference = new Userpreference();
    	userpreference.setTheme("default-theme");
    	userpreference.setLanguage("en");
    	userpreference.setId(user.getId());
    	userpreference.setUser(user);
   
    	return _userpreferenceRepository.save(userpreference);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
   	public void updateTheme(User user, String theme) {
    	
		
    	Userpreference userpreference = _userpreferenceRepository.findById(user.getId()).orElse(null);
    	userpreference.setTheme(theme);
    	
    	_userpreferenceRepository.save(userpreference);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
   	public void updateLanguage(User user, String language) {
		
    	Userpreference userpreference = _userpreferenceRepository.findById(user.getId()).orElse(null);
    	userpreference.setLanguage(language);
    	
    	_userpreferenceRepository.save(userpreference);
    }

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindUserWithAllFieldsByIdOutput findWithAllFieldsById(Long userId) {

		User foundUser = _userRepository.findById(userId).orElse(null);
		if (foundUser == null)  
			return null ; 
 	   
 	    return mapper.userToFindUserWithAllFieldsByIdOutput(foundUser);
	}
	
	public UserProfile getProfile(FindUserByIdOutput user)
	{
		return mapper.findUserByIdOutputToUserProfile(user);
	}
	
	public UserProfile updateUserProfile(FindUserWithAllFieldsByIdOutput user, UserProfile userProfile)
	{
		UpdateUserInput userInput = mapper.findUserWithAllFieldsByIdOutputAndUserProfileToUpdateUserInput(user, userProfile);
		UpdateUserOutput output = update(user.getId(),userInput);
		
		return mapper.updateUserOutputToUserProfile(output);
	}
	
	@Transactional(readOnly = true)
	public User getUser() {

		return _userRepository.findByUserNameIgnoreCase(SecurityUtils.getCurrentUserLogin().orElse(null));
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindUserByNameOutput findByUserName(String userName) {

		User foundUser = _userRepository.findByUserNameIgnoreCase(userName);
		if (foundUser == null) {
			return null;
		}

		return  mapper.userToFindUserByNameOutput(foundUser);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindUserByNameOutput findByEmailAddress(String emailAddress) {

		User foundUser = _userRepository.findByEmailAddressIgnoreCase(emailAddress);
		if (foundUser == null) {
			return null;
		}
	
		return  mapper.userToFindUserByNameOutput(foundUser);
	}
	
    @Transactional(propagation = Propagation.REQUIRED)
	public void updateUserData(FindUserWithAllFieldsByIdOutput user)
	{
		User foundUser = mapper.findUserWithAllFieldsByIdOutputToUser(user);
		_userRepository.save(foundUser);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindUserByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<User> foundUser = _userRepository.findAll(search(search), pageable);
		List<User> userList = foundUser.getContent();
		Iterator<User> userIterator = userList.iterator(); 
		List<FindUserByIdOutput> output = new ArrayList<>();

		while (userIterator.hasNext()) {
		User user = userIterator.next();
		Userpreference userPreference =_userpreferenceRepository.findById(user.getId()).orElse(null);
 	    output.add(mapper.userToFindUserByIdOutput(user,userPreference));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws Exception {

		QUser user= QUser.userEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(user, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws Exception  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("emailAddress") ||
				list.get(i).replace("%20","").trim().equals("firstName") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("isActive") ||
				list.get(i).replace("%20","").trim().equals("isEmailConfirmed") ||
				list.get(i).replace("%20","").trim().equals("lastName") ||
				list.get(i).replace("%20","").trim().equals("password") ||
				list.get(i).replace("%20","").trim().equals("phoneNumber") ||
				list.get(i).replace("%20","").trim().equals("userName")
			)) 
			{
			 throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QUser user, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
            if(details.getKey().replace("%20","").trim().equals("emailAddress")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.emailAddress.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(user.emailAddress.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(user.emailAddress.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("firstName")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.firstName.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(user.firstName.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(user.firstName.ne(details.getValue().getSearchValue()));
				}
			}
			 if(details.getKey().replace("%20","").trim().equals("id")) {
			 	if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(user.id.eq(Long.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(user.id.ne(Long.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				  	if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(user.id.between(Long.valueOf(details.getValue().getStartingValue()), Long.valueOf(details.getValue().getEndingValue())));
                   	} else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	  	builder.and(user.id.goe(Long.valueOf(details.getValue().getStartingValue())));
                   	} else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	  	builder.and(user.id.loe(Long.valueOf(details.getValue().getEndingValue())));
					}
				}
			}
			if(details.getKey().replace("%20","").trim().equals("isActive")) {
				if(details.getValue().getOperator().equals("equals") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false"))) {
					builder.and(user.isActive.eq(Boolean.parseBoolean(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false"))) {
					builder.and(user.isActive.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("isEmailConfirmed")) {
				if(details.getValue().getOperator().equals("equals") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false"))) {
					builder.and(user.isEmailConfirmed.eq(Boolean.parseBoolean(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false"))) {
					builder.and(user.isEmailConfirmed.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("lastName")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.lastName.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(user.lastName.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(user.lastName.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("password")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.password.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(user.password.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(user.password.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("phoneNumber")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.phoneNumber.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(user.phoneNumber.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(user.phoneNumber.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("userName")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(user.userName.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(user.userName.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(user.userName.ne(details.getValue().getSearchValue()));
				}
			}
	    
		}
		
		return builder;
	}
	
    
	public Map<String,String> parseUserpermissionsJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("userId", keysString);
		  
		return joinColumnMap;
	}
    
    
	public Map<String,String> parseUserrolesJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("userId", keysString);
		  
		return joinColumnMap;
	}
    
}



