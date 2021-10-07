package com.fastcode.example.domain.core.t4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
@Repository("t4Repository")
public interface IT4Repository extends JpaRepository<T4, Integer>,QuerydslPredicateExecutor<T4> {

    
	
}

