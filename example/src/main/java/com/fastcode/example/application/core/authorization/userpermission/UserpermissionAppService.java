package com.fastcode.example.application.core.authorization.userpermission;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.fastcode.example.application.core.authorization.userpermission.dto.*;
import com.fastcode.example.domain.core.authorization.userpermission.IUserpermissionRepository;
import com.fastcode.example.domain.core.authorization.userpermission.QUserpermission;
import com.fastcode.example.domain.core.authorization.userpermission.Userpermission;
import com.fastcode.example.domain.core.authorization.userpermission.UserpermissionId;
import com.fastcode.example.domain.core.authorization.permission.IPermissionRepository;
import com.fastcode.example.domain.core.authorization.permission.Permission;
import com.fastcode.example.domain.core.authorization.user.IUserRepository;
import com.fastcode.example.domain.core.authorization.user.User;

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

@Service("userpermissionAppService")
@RequiredArgsConstructor
public class UserpermissionAppService implements IUserpermissionAppService {
	@Qualifier("userpermissionRepository")
	@NonNull protected final IUserpermissionRepository _userpermissionRepository;

	
    @Qualifier("permissionRepository")
	@NonNull protected final IPermissionRepository _permissionRepository;

    @Qualifier("userRepository")
	@NonNull protected final IUserRepository _userRepository;

	@Qualifier("IUserpermissionMapperImpl")
	@NonNull protected final IUserpermissionMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateUserpermissionOutput create(CreateUserpermissionInput input) {

		Userpermission userpermission = mapper.createUserpermissionInputToUserpermission(input);
		Permission foundPermission = null;
		User foundUser = null;
	  	if(input.getPermissionId()!=null) {
			foundPermission = _permissionRepository.findById(input.getPermissionId()).orElse(null);
			
		}
		else {
			return null;
		}
	  	if(input.getUserId()!=null) {
			foundUser = _userRepository.findById(input.getUserId()).orElse(null);
			
		}
		else {
			return null;
		}
		if(foundUser != null || foundPermission != null)
		{			
			if(!checkIfPermissionAlreadyAssigned(foundUser, foundPermission))
			{
				foundPermission.addUserpermissions(userpermission);
				foundUser.addUserpermissions(userpermission);
				
			//	userpermission.setPermission(foundPermission);
			//	userpermission.setUser(foundUser);
			}		
		}
		else {
			return null;
		}

		Userpermission createdUserpermission = _userpermissionRepository.save(userpermission);
        CreateUserpermissionOutput output = mapper.userpermissionToCreateUserpermissionOutput(createdUserpermission);

		output.setRevoked(input.getRevoked());
		return output;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateUserpermissionOutput update(UserpermissionId userpermissionId, UpdateUserpermissionInput input) {

		Userpermission existing = _userpermissionRepository.findById(userpermissionId).get();

		Userpermission userpermission = mapper.updateUserpermissionInputToUserpermission(input);
		Permission foundPermission = null;
		User foundUser = null;
        
	  	if(input.getPermissionId()!=null) { 
			foundPermission = _permissionRepository.findById(input.getPermissionId()).orElse(null);
		}
		else {
			return null;
		}
        
	  	if(input.getUserId()!=null) { 
			foundUser = _userRepository.findById(input.getUserId()).orElse(null);
		}
		else {
			return null;
		}
		if(foundUser != null || foundPermission != null)
		{			
			if(checkIfPermissionAlreadyAssigned(foundUser, foundPermission))
			{
				//userpermission.setPermission(foundPermission);
				//userpermission.setUser(foundUser);
				userpermission.setRevoked(input.getRevoked());
				foundPermission.addUserpermissions(userpermission);
				foundUser.addUserpermissions(userpermission);
			}		
		}
		else {
			return null;
		}
	    Userpermission updatedUserpermission = _userpermissionRepository.save(userpermission);
		return mapper.userpermissionToUpdateUserpermissionOutput(updatedUserpermission);
	}
	
	public boolean checkIfPermissionAlreadyAssigned(User foundUser,Permission foundPermission)
	{

		List<Userpermission> userPermission = _userpermissionRepository.findByUserId(foundUser.getId());

		Iterator pIterator = userPermission.iterator();
		while (pIterator.hasNext()) {
			Userpermission pe = (Userpermission) pIterator.next();
			if (pe.getPermission() == foundPermission ) {
				return true;
			}
		}
			
		return false;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(UserpermissionId userpermissionId) {

		Userpermission existing = _userpermissionRepository.findById(userpermissionId).orElse(null); 
	 	
        if(existing.getPermission() !=null)
        {
        existing.getPermission().removeUserpermissions(existing);
        }
        if(existing.getUser() !=null)
        {
        existing.getUser().removeUserpermissions(existing);
        }
	 	_userpermissionRepository.delete(existing);
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindUserpermissionByIdOutput findById(UserpermissionId userpermissionId) {

		Userpermission foundUserpermission = _userpermissionRepository.findById(userpermissionId).orElse(null);
		if (foundUserpermission == null)  
			return null; 
 	   
 	    return mapper.userpermissionToFindUserpermissionByIdOutput(foundUserpermission);
	}
	
    //Permission
	// ReST API Call - GET /userpermission/1/permission
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetPermissionOutput getPermission(UserpermissionId userpermissionId) {

		Userpermission foundUserpermission = _userpermissionRepository.findById(userpermissionId).orElse(null);
		if (foundUserpermission == null) {
			logHelper.getLogger().error("There does not exist a userpermission wth a id=%s", userpermissionId);
			return null;
		}
		Permission re = foundUserpermission.getPermission();
		return mapper.permissionToGetPermissionOutput(re, foundUserpermission);
	}
	
    //User
	// ReST API Call - GET /userpermission/1/user
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetUserOutput getUser(UserpermissionId userpermissionId) {

		Userpermission foundUserpermission = _userpermissionRepository.findById(userpermissionId).orElse(null);
		if (foundUserpermission == null) {
			logHelper.getLogger().error("There does not exist a userpermission wth a id=%s", userpermissionId);
			return null;
		}
		User re = foundUserpermission.getUser();
		return mapper.userToGetUserOutput(re, foundUserpermission);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindUserpermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws Exception  {

		Page<Userpermission> foundUserpermission = _userpermissionRepository.findAll(search(search), pageable);
		List<Userpermission> userpermissionList = foundUserpermission.getContent();
		Iterator<Userpermission> userpermissionIterator = userpermissionList.iterator(); 
		List<FindUserpermissionByIdOutput> output = new ArrayList<>();

		while (userpermissionIterator.hasNext()) {
		Userpermission userpermission = userpermissionIterator.next();
 	    output.add(mapper.userpermissionToFindUserpermissionByIdOutput(userpermission));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws Exception {

		QUserpermission userpermission= QUserpermission.userpermissionEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(userpermission, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws Exception  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("permission") ||
//		        list.get(i).replace("%20","").trim().equals("displayName") ||
		        list.get(i).replace("%20","").trim().equals("user") ||
//		        list.get(i).replace("%20","").trim().equals("userName") ||
				list.get(i).replace("%20","").trim().equals("permissionId") ||
				list.get(i).replace("%20","").trim().equals("revoked") ||
				list.get(i).replace("%20","").trim().equals("userId")
			)) 
			{
			 throw new Exception("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QUserpermission userpermission, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		for (Map.Entry<String, SearchFields> details : map.entrySet()) {
			 if(details.getKey().replace("%20","").trim().equals("permissionId")) {
			 	if(details.getValue().getOperator().equals("contains")) {
					builder.and(userpermission.permissionId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(userpermission.permissionId.eq(Long.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(userpermission.permissionId.ne(Long.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				  	if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(userpermission.permissionId.between(Long.valueOf(details.getValue().getStartingValue()), Long.valueOf(details.getValue().getEndingValue())));
                   	} else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	  	builder.and(userpermission.permissionId.goe(Long.valueOf(details.getValue().getStartingValue())));
                   	} else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	  	builder.and(userpermission.permissionId.loe(Long.valueOf(details.getValue().getEndingValue())));
					}
				}
			}
			if(details.getKey().replace("%20","").trim().equals("revoked")) {
				if(details.getValue().getOperator().equals("equals") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false"))) {
					builder.and(userpermission.revoked.eq(Boolean.parseBoolean(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && (details.getValue().getSearchValue().equalsIgnoreCase("true") || details.getValue().getSearchValue().equalsIgnoreCase("false"))) {
					builder.and(userpermission.revoked.ne(Boolean.parseBoolean(details.getValue().getSearchValue())));
				}
			}
			 if(details.getKey().replace("%20","").trim().equals("userId")) {
			 	if(details.getValue().getOperator().equals("contains")) {
					builder.and(userpermission.userId.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(userpermission.userId.eq(Long.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(userpermission.userId.ne(Long.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				  	if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(userpermission.userId.between(Long.valueOf(details.getValue().getStartingValue()), Long.valueOf(details.getValue().getEndingValue())));
                   	} else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	  	builder.and(userpermission.userId.goe(Long.valueOf(details.getValue().getStartingValue())));
                   	} else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	  	builder.and(userpermission.userId.loe(Long.valueOf(details.getValue().getEndingValue())));
					}
				}
			}
	    
		    if(details.getKey().replace("%20","").trim().equals("permission")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(userpermission.permission.displayName.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(userpermission.permission.displayName.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(userpermission.permission.displayName.ne(details.getValue().getSearchValue()));
				}
			}
		    if(details.getKey().replace("%20","").trim().equals("user")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(userpermission.user.userName.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(userpermission.user.userName.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(userpermission.user.userName.ne(details.getValue().getSearchValue()));
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
        if(joinCol != null && joinCol.getKey().equals("permissionId")) {
		    builder.and(userpermission.permission.id.eq(Long.parseLong(joinCol.getValue())));
        }
        
		if(joinCol != null && joinCol.getKey().equals("permission")) {
		    builder.and(userpermission.permission.displayName.eq(joinCol.getValue()));
        }
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
        if(joinCol != null && joinCol.getKey().equals("userId")) {
		    builder.and(userpermission.user.id.eq(Long.parseLong(joinCol.getValue())));
        }
        
		if(joinCol != null && joinCol.getKey().equals("user")) {
		    builder.and(userpermission.user.userName.eq(joinCol.getValue()));
        }
        }
		return builder;
	}
	
	public UserpermissionId parseUserpermissionKey(String keysString) {
		
		String[] keyEntries = keysString.split(",");
		UserpermissionId userpermissionId = new UserpermissionId();
		
		Map<String,String> keyMap = new HashMap<String,String>();
		if(keyEntries.length > 1) {
			for(String keyEntry: keyEntries)
			{
				String[] keyEntryArr = keyEntry.split("=");
				if(keyEntryArr.length > 1) {
					keyMap.put(keyEntryArr[0], keyEntryArr[1]);					
				}
				else {
					return null;
				}
			}
		}
		else {
			String[] keyEntryArr = keysString.split("=");
			if(keyEntryArr.length > 1) {
				keyMap.put(keyEntryArr[0], keyEntryArr[1]);					
			}
			else 
			return null;
		}
		
		userpermissionId.setPermissionId(Long.valueOf(keyMap.get("permissionId")));
		userpermissionId.setUserId(Long.valueOf(keyMap.get("userId")));
		return userpermissionId;
		
	}	
    
    
}



