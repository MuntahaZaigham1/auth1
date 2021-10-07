package com.fastcode.example.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.fastcode.example.domain.core.authorization.rolepermission.Rolepermission;
import com.fastcode.example.domain.core.authorization.user.User;
import com.fastcode.example.domain.core.authorization.userpermission.Userpermission;
import com.fastcode.example.domain.core.authorization.rolepermission.IRolepermissionRepository;
import com.fastcode.example.domain.core.authorization.userrole.Userrole;
import com.fastcode.example.domain.core.authorization.userrole.IUserroleRepository;
import com.fastcode.example.domain.core.authorization.userpermission.IUserpermissionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    @Qualifier("rolepermissionRepository")
    @NonNull private final IRolepermissionRepository rolepermissionRepository;

    @Qualifier("userroleRepository")
    @NonNull private final IUserroleRepository userroleRepository;

    @Qualifier("userpermissionRepository")
    @NonNull private final IUserpermissionRepository userpermissionRepository;

    public List<String> getAllPermissionsFromUserAndRole(User user) {

        List<String> permissions = new ArrayList<>();
        List<Userrole> ure = userroleRepository.findByUserId(user.getId());

        for (Userrole ur : ure) {

            List<Rolepermission> srp= rolepermissionRepository.findByRoleId(ur.getRoleId());
            for (Rolepermission item : srp) {
                permissions.add(item.getPermission().getName());
            }
        }

        List<Userpermission> spe = userpermissionRepository.findByUserId(user.getId());

        for (Userpermission up : spe) {

            if(permissions.contains(up.getPermission().getName()) && (up.getRevoked() != null && up.getRevoked()))
            {
                permissions.remove(up.getPermission().getName());
            }
            if(!permissions.contains(up.getPermission().getName()) && (up.getRevoked()==null || !up.getRevoked()))
            {
                permissions.add(up.getPermission().getName());

            }
        }
		
		return permissions
				.stream()
				.distinct()
				.collect(Collectors.toList());
	}
	
	public String getTokenFromCookies(Cookie[] cookies)
    {
    	if(cookies !=null) {
    		for(Cookie c : cookies)
			{
				if(c.getName().equals(SecurityConstants.HEADER_STRING_AUTHENTICATION)) {
					return c.getValue();
				}
			}
    	}
    	return null;
    }
	
	public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .map(authentication -> {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                    return springSecurityUser.getUsername();
                } else if (authentication.getPrincipal() instanceof String) {
                    return (String) authentication.getPrincipal();
                }
                return null;
            });
    }

}

