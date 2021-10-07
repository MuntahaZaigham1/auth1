package com.fastcode.example.application.core.authorization.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.domain.core.authorization.userpreference.Userpreference;
import com.fastcode.example.application.core.authorization.user.dto.*;
import com.fastcode.example.domain.core.authorization.user.User;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IUserMapper {
   User createUserInputToUser(CreateUserInput userDto);
   
   @Mappings({
   @Mapping(source = "entity.id", target = "id"),
   })
   CreateUserOutput userToCreateUserOutput(User entity,Userpreference userPreference);
   
    @Mappings({
    @Mapping(source = "userProfile.userName", target = "userName"),
    @Mapping(source = "userProfile.emailAddress", target = "emailAddress"),
    @Mapping(source = "userProfile.phoneNumber", target = "phoneNumber"),
    @Mapping(source = "userProfile.lastName", target = "lastName"),
    @Mapping(source = "userProfile.firstName", target = "firstName")
    })
    UpdateUserInput findUserWithAllFieldsByIdOutputAndUserProfileToUpdateUserInput(FindUserWithAllFieldsByIdOutput user, UserProfile userProfile);
    
    User findUserWithAllFieldsByIdOutputToUser(FindUserWithAllFieldsByIdOutput user);
    
    UserProfile updateUserOutputToUserProfile(UpdateUserOutput userDto);
    
    UserProfile findUserByIdOutputToUserProfile(FindUserByIdOutput user);
    
    User updateUserInputToUser(UpdateUserInput userDto);
    
   	UpdateUserOutput userToUpdateUserOutput(User entity);
   	@Mappings({
    @Mapping(source = "entity.versiono", target = "versiono"),
    @Mapping(source = "entity.id", target = "id"),
   	})
   	FindUserByIdOutput userToFindUserByIdOutput(User entity,Userpreference userPreference);
   	FindUserWithAllFieldsByIdOutput userToFindUserWithAllFieldsByIdOutput(User entity);
	FindUserByNameOutput userToFindUserByNameOutput(User entity);

}

