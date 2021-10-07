package com.fastcode.example.domain.core.authorization.userpreference;

import com.querydsl.core.types.Predicate;

import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserpreferenceManager implements IUserpreferenceManager {

	@NonNull private final IUserpreferenceRepository  _userpreferenceRepository;

	public Userpreference create(Userpreference userpreference) {

		return _userpreferenceRepository.save(userpreference);
	}
	
	public void delete(Userpreference userpreference) {

		_userpreferenceRepository.delete(userpreference);	
	}

	public Userpreference update(Userpreference userpreference) {

		return _userpreferenceRepository.save(userpreference);
	}
    
    public Userpreference findById(Long userId) {
		Optional<Userpreference> dbUser= _userpreferenceRepository.findById(userId);
		if(dbUser.isPresent()) {
			Userpreference existingUser = dbUser.get();
			return existingUser;
		} else {
			return null;
		}
	}

	public Page<Userpreference> findAll(Predicate predicate, Pageable pageable) {

		return _userpreferenceRepository.findAll(predicate,pageable);
	}


}


