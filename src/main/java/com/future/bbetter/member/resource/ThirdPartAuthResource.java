package com.future.bbetter.member.resource;

public interface ThirdPartAuthResource {

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
	 * 檢查該來源的ProviderId是否已存在
	 * @author Charles
	 * @date 2017年10月18日 下午10:59:11
	 * @param userId
	 * @param source
	 * @return boolean
	 */
	public boolean checkIsExist(String providerId, Integer source);
	
	/**
	 * 新增一位第三方驗證註冊資訊
	 * @author Charles
	 * @date 2017年10月18日 下午11:47:58
	 * @param memberId
	 * @param providerId
	 * @param source
	 */
	public void addThirdPartAuth(Long memberId, String providerId, Integer source);
}
