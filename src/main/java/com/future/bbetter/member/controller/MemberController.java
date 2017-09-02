package com.future.bbetter.member.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.future.bbetter.member.model.MemberDTO;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.service.MemberService;

@RestController
@RequestMapping()
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
	@RequestMapping("/member/{memberId}")
	public MemberDTO getUser(@PathVariable Long memberId){
		return memberResource.getMember(memberId);
	}
	
	@RequestMapping("/members")
	public List<MemberDTO> getAllMembers() {
		return memberResource.getAllMembers();
	}
	
	@RequestMapping(value = "/member", method=RequestMethod.POST)
	public void addUser(@RequestBody MemberDTO memberDTO) throws Exception{
		//錯誤檢查
		List<String> errorList = memberService.checkAddUser(memberDTO);
		if(!errorList.isEmpty()) {
			throw new Exception(errorList.toString());
		}else {
			memberResource.addMember(memberDTO);
		}
		
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
