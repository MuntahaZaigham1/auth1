package com.fastcode.example.application.core.authorization.userrole;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.domain.core.authorization.role.Role;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.application.core.authorization.userrole.dto.*;
import com.fastcode.example.domain.core.authorization.userrole.Userrole;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IUserroleMapper {
   Userrole createUserroleInputToUserrole(CreateUserroleInput userroleDto);
   
   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),  
   @Mapping(source = "user.userName", target = "userDescriptiveField"),
   @Mapping(source = "role.displayName", target = "roleDescriptiveField"),                   
   @Mapping(source = "role.id", target = "roleId")                   
   }) 
   CreateUserroleOutput userroleToCreateUserroleOutput(Userrole entity);
   
    Userrole updateUserroleInputToUserrole(UpdateUserroleInput userroleDto);
    
    @Mappings({ 
    @Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),                    
    @Mapping(source = "entity.user.userName", target = "userDescriptiveField"),                    
   	}) 
   	UpdateUserroleOutput userroleToUpdateUserroleOutput(Userrole entity);
   	@Mappings({ 
   	@Mapping(source = "entity.role.displayName", target = "roleDescriptiveField"),                    
   	@Mapping(source = "entity.user.userName", target = "userDescriptiveField"),                    
   	}) 
   	FindUserroleByIdOutput userroleToFindUserroleByIdOutput(Userrole entity);

   @Mappings({
   @Mapping(source = "foundUserrole.roleId", target = "userroleRoleId"),
   @Mapping(source = "foundUserrole.userId", target = "userroleUserId"),
   })
   GetRoleOutput roleToGetRoleOutput(Role role, Userrole foundUserrole);
   
   @Mappings({
   @Mapping(source = "foundUserrole.roleId", target = "userroleRoleId"),
   @Mapping(source = "foundUserrole.userId", target = "userroleUserId"),
   })
   GetUserOutput userToGetUserOutput(User user, Userrole foundUserrole);
   
}

