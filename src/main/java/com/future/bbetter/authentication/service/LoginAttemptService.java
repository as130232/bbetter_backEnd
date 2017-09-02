package com.future.bbetter.authentication.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


/**
 * 會原嘗試登入，若失敗記錄該用戶IP
 * @author Charles
 * @date 2017年9月2日 下午2:17:36
 */
@Service
public class LoginAttemptService {
	//登入不得失敗的次數(超過該次數將該用戶IP屏蔽一分鐘內不得再登入)
	private final int MAX_ATTEMPT = 3;
	//若被攔阻，設定時間內該用戶不得再登入
	private final int blockTimeMins = 1;
	private LoadingCache<String, Integer> blockList;

	public LoginAttemptService() {
		blockList = CacheBuilder.newBuilder().expireAfterWrite(blockTimeMins, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}
	
	/**
	 * 每當用戶登入成功便透過 LoginAttemptService，將該 ip 從 block list 中清除
	 * @author Charles
	 * @date 2017年9月2日 下午2:18:42
	 * @param userIPAddress
	 * @return void
	 */
	public void loginSucceeded(String userIPAddress) {
		blockList.invalidate(userIPAddress);
	}
	
	/**
	 * 每當用戶登入失敗就透過 LoginAttemptService，將該 ip 放入 block list 中，並記錄失敗次數。
	 * @author Charles
	 * @date 2017年9月2日 下午2:18:42
	 * @param userIPAddress
	 * @return void
	 */
	public void loginFailed(String userIPAddress) {
		int attempts = 0;
		try {
			//取得對應ip登入失敗的次數，並累加
			attempts = blockList.get(userIPAddress);
		} catch (ExecutionException e) {
			attempts = 0;
		}
		attempts++;
		blockList.put(userIPAddress, attempts);
	}
	
	/**
	 * 檢查該用戶是否被攔阻
	 * @author Charles
	 * @date 2017年9月2日 下午2:18:42
	 * @param userIPAddress
	 * @return boolean
	 */
	public boolean isBlocked(String userIPAddress) {
		try {
			return blockList.get(userIPAddress) >= MAX_ATTEMPT;
		} catch (ExecutionException e) {
			return false;
		}
	}
}
