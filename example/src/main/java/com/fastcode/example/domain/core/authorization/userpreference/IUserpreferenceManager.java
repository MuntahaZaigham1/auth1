package com.fastcode.example.domain.core.authorization.userpreference;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserpreferenceManager {
   
    Userpreference create(Userpreference userpreference);

    void delete(Userpreference userpreference);

    Userpreference update(Userpreference userpreference);
    
    Userpreference findById(Long id);
    
    Page<Userpreference> findAll(Predicate predicate, Pageable pageable);
  
}



