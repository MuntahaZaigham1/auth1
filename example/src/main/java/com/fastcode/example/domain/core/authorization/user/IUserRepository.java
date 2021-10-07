package com.fastcode.example.domain.core.authorization.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
@Repository("userRepository")
public interface IUserRepository extends JpaRepository<User, Long>,QuerydslPredicateExecutor<User> {

 	@Query("select u from User u where u.userName = ?1")
    User findByUserNameIgnoreCase(String value);  
    
    User findByEmailAddressIgnoreCase(String emailAddress);
    
    
	
}

