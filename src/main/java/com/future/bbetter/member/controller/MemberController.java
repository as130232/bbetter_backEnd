package com.future.bbetter.member.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.exception.customize.DataNotFoundException;
import com.future.bbetter.exception.customize.ValidateFailException;
import com.future.bbetter.member.model.MemberDTO;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.service.MemberService;

@RestController
@RequestMapping("/public")
//設定該資源需要什麼權限角色
//@PreAuthorize("hasRole('ROLE_USER')")
public class MemberController {
	
	@Autowired
	private MemberResource memberResource;
	@Autowired
	private MemberService memberService;
	
	/**
	 * 取得會員資訊
	 * @author Charles
	 * @date 2017年8月27日 下午7:37:14
	 */
	
	//必須具備USER權限，且該取得的會員資訊必須為會原本人
	//例:已登入但欲取得他人帳戶資訊，利用該IP查到的會員eamil與當前登入中的Authentication的username做比對
	@PreAuthorize("hasRole('USER')")
	@PostAuthorize("returnObject.email == principal.username")
	@GetMapping("/member/{memberId}")
	public MemberDTO getMember(@PathVariable Long memberId) throws DataNotFoundException{
		MemberDTO memberInfo = memberResource.getMember(memberId);
		return memberInfo;
	}
	
	//只有系統管理者才有權限訪問
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/members")
	public List<MemberDTO> getAllMembers(HttpServletRequest req, HttpServletResponse res) {
		//取的用戶的當地語言，未來用於i18n
		Locale userPreferredLocale = req.getLocale();
		return memberResource.getAllMembers();
	}
	
	@RequestMapping(value = "/member/{memberId}", method=RequestMethod.PATCH)
	public void updateUser(@RequestBody MemberDTO updateMemberDTO){
		memberResource.updateMember(updateMemberDTO);
	}
	
	@RequestMapping(value = "/member/{memberId}", method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable Long memberId){
		memberResource.deleteMember(memberId);
	}
	
	
}
