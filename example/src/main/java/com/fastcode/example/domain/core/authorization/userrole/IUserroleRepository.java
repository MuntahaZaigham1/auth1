package com.fastcode.example.domain.core.authorization.userrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
@Repository("userroleRepository")
public interface IUserroleRepository extends JpaRepository<Userrole, UserroleId>,QuerydslPredicateExecutor<Userrole> {

    List<Userrole> findByUserId(Long userId);

    List<Userrole> findByRoleId(Long roleId);
    
	
}

