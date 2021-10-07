package com.fastcode.example.domain.core.t3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.time.*;
@Repository("t3Repository")
public interface IT3Repository extends JpaRepository<T3, Integer>,QuerydslPredicateExecutor<T3> {

    
	
}

