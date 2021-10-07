package com.fastcode.example.application.core.authorization.userpermission;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.fastcode.example.domain.core.authorization.permission.Permission;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.application.core.authorization.userpermission.dto.*;
import com.fastcode.example.domain.core.authorization.userpermission.Userpermission;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IUserpermissionMapper {
   Userpermission createUserpermissionInputToUserpermission(CreateUserpermissionInput userpermissionDto);
   
   @Mappings({ 
   @Mapping(source = "user.id", target = "userId"),  
   @Mapping(source = "user.userName", target = "userDescriptiveField"),
   @Mapping(source = "permission.name", target = "permissionDescriptiveField"),                   
   @Mapping(source = "permission.id", target = "permissionId")                   
   }) 
   CreateUserpermissionOutput userpermissionToCreateUserpermissionOutput(Userpermission entity);
   
    Userpermission updateUserpermissionInputToUserpermission(UpdateUserpermissionInput userpermissionDto);
    
    @Mappings({ 
    @Mapping(source = "entity.permission.displayName", target = "permissionDescriptiveField"),                    
    @Mapping(source = "entity.user.userName", target = "userDescriptiveField"),                    
   	}) 
   	UpdateUserpermissionOutput userpermissionToUpdateUserpermissionOutput(Userpermission entity);
   	@Mappings({ 
   	@Mapping(source = "entity.permission.displayName", target = "permissionDescriptiveField"),                    
   	@Mapping(source = "entity.user.userName", target = "userDescriptiveField"),                    
   	}) 
   	FindUserpermissionByIdOutput userpermissionToFindUserpermissionByIdOutput(Userpermission entity);

   @Mappings({
   @Mapping(source = "foundUserpermission.permissionId", target = "userpermissionPermissionId"),
   @Mapping(source = "foundUserpermission.userId", target = "userpermissionUserId"),
   })
   GetPermissionOutput permissionToGetPermissionOutput(Permission permission, Userpermission foundUserpermission);
   
   @Mappings({
   @Mapping(source = "foundUserpermission.permissionId", target = "userpermissionPermissionId"),
   @Mapping(source = "foundUserpermission.userId", target = "userpermissionUserId"),
   })
   GetUserOutput userToGetUserOutput(User user, Userpermission foundUserpermission);
   
}

