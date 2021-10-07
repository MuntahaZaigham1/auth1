package com.fastcode.example.domain.core.authorization.userpermission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
@Repository("userpermissionRepository")
public interface IUserpermissionRepository extends JpaRepository<Userpermission, UserpermissionId>,QuerydslPredicateExecutor<Userpermission> {

    List<Userpermission> findByUserId(Long userId);

    
	
}

