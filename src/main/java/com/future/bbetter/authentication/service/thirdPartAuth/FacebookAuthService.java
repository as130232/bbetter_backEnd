package com.future.bbetter.authentication.service.thirdPartAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.InvalidAuthorizationException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.future.bbetter.authentication.model.AuthResponse;
import com.future.bbetter.authentication.password.Password;
import com.future.bbetter.exception.customize.ThirdVerificationException;
import com.future.bbetter.member.constant.MEMBER;
import com.future.bbetter.member.constant.THIRD_PART_AUTH;
import com.future.bbetter.member.dto.MemberDTO;
import com.future.bbetter.member.resource.MemberResource;
import com.future.bbetter.member.resource.ThirdPartAuthResource;

@Service
public class FacebookAuthService {
	
	@Autowired
	private FacebookConnectionFactory facebookConnectionFactory;
	@Autowired
	private UsersConnectionRepository usersConnectionRepository;
	@Autowired
	private ThirdPartAuthResource thirdPartAuthResource;
	@Autowired
	private MemberResource memberResource;
	
	public void login(AuthResponse authResponse) throws ThirdVerificationException{
		//String url = "https://graph.facebook.com/v2.10/me?fields=id%2Cname&access_token=" + authResponse.getAccessToken();
		AccessGrant accessGrant = new AccessGrant(authResponse.getAccessToken());
		Connection<Facebook> connection = null;
		try {
			//利用前端用戶在Facebook登入驗證得到的token，在後端在向facebook發出請求再驗證一次，
			connection = facebookConnectionFactory.createConnection(accessGrant);
		}catch(ExpiredAuthorizationException e) {
			//過期的token
			throw new ThirdVerificationException(e.getMessage());
		}catch(InvalidAuthorizationException e) {
			//不合法的token
			throw new ThirdVerificationException(e.getMessage());
		}
		
		//表示與facebook連接關聯的授權憑據有效
		boolean isTestSuccess = connection.test();
		//是否過期
		boolean hasExpired = connection.hasExpired();
		if(!isTestSuccess || hasExpired) {
			throw new ThirdVerificationException("會員驗證已過期，請重新登入！");
		}
		//檢測是否已註冊(第三方驗證是否已有資料)
		boolean isExist = thirdPartAuthResource.checkIsExist(authResponse.getUserID(), THIRD_PART_AUTH.SOURCE_FACEBOOK.value);
		//若不存在則註冊
		if(!isExist) {
			this.register(connection);
		}
		
	}

	@Transactional
	public void register(Connection<Facebook> connection) throws ThirdVerificationException{
		//providerId , providerUserId
		ConnectionKey connectionKey = connection.getKey();
		
		//同步資料
		//connection.sync();
		//放入緩存
		//ConnectionData connectionData = connection.createData();
		//新增資訊
	    //usersConnectionRepository.createConnectionRepository(userProfile.getEmail()).addConnection(connection);
        
		//將對facebook進行遠程API調用，以獲取用戶的facebook個人資料數據並將其歸為UserProfile model
	    UserProfile userProfile = connection.fetchUserProfile();
	    //讓客戶端應用程序訪問Facebook的本機API的全部功能。
        Facebook facebook = connection.getApi();
        User user = facebook.userOperations().getUserProfile();
        String email = userProfile.getEmail();
        
        //檢查該email是否已重覆
        boolean isEmailExist = memberResource.checkIsEmailExist(email);
        if(isEmailExist) {
        	throw new ThirdVerificationException("此信箱已註冊過！");
        }
        //轉換對應性別碼
        String password = Password.createRandomPassword();
        Integer gender = MEMBER.GENDER_MALE.value;
        if("FEMALE".equals(user.getGender().toUpperCase())) {
        	gender = MEMBER.GENDER_FEMALE.value;
        }
        //解析生日
        String birthdayStr = user.getBirthday();
        String[] birthdayArray = birthdayStr.split("/");
        String birthdayFormate = birthdayArray[2] + "/"+ birthdayArray[0] + "/" + birthdayArray[1];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birthday = null;
        try {
        	birthday = formatter.parse(birthdayFormate);
        } catch (ParseException e) {
        }
        
        //不需要驗證資料是否正確，facebook已驗證
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setEmail(email);
		memberDTO.setName(user.getName());
		memberDTO.setBirthday(birthday);
		memberDTO.setGender(gender);
		memberDTO.setPassword(password);
		memberDTO.setAddress(user.getLocale().getDisplayCountry());
		memberDTO.setImageUrl(connection.getImageUrl());
		//新增一筆會員
		MemberDTO newMemberDTO = memberResource.addMember(memberDTO);
		
		//並將該會員及來源供應商ID新增至第三方驗證表中
		thirdPartAuthResource.addThirdPartAuth(newMemberDTO.getMemberId(), userProfile.getId(), THIRD_PART_AUTH.SOURCE_FACEBOOK.value);
	}
}
