package com.fastcode.example.application.core.authorization.tokenverification;

import com.fastcode.example.domain.core.authorization.tokenverification.Tokenverification;

public interface ITokenVerificationAppService {
	
	Tokenverification findByTokenAndType(String token, String type);

	Tokenverification generateToken(String type, Long userId );

	Tokenverification findByUserIdAndType( Long userId , String type);

	void deleteToken(Tokenverification entity);

}

