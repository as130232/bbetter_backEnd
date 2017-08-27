package com.future.bbetter.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.future.bbetter.member.resource.MemberResource;

@Service
public class MemberService {
	@Autowired
	private MemberResource memberResource;
	
	
}
