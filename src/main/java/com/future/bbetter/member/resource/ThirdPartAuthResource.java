package com.future.bbetter.member.resource;

public interface ThirdPartAuthResource {

	/**
	 * 檢查該userId是否已存在
	 * @author Charles
	 * @date 2017年10月18日 下午10:59:11
	 * @param userId
	 * @param source
	 * @return boolean
	 */
	public boolean checkIsExist(String userId, Integer source);
	
	/**
	 * 藉由providerId取得memberId
	 * @author Charles
	 * @date 2017年10月19日 下午9:29:49
	 * @param providerId
	 * @param source
	 * @return Long
	 */
	public Long getMemberId(String providerId, Integer source);
	
	/**
	 * 新增一筆第三方驗證註冊資訊
	 * @author Charles
	 * @date 2017年10月18日 下午11:47:58
	 * @param memberId
	 * @param providerId
	 * @param source
	 * @throws exceptionType 如果...將拋出exceptionType異常.
	 */
	public void insert(Long memberId, String providerId, Integer source);
}
