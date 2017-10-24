package com.future.bbetter.authentication.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ReconnectFilter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@PropertySource("classpath:application.properties")
@EnableSocial//啟用 Spring Social 的相關功能
public class SocialConfig implements SocialConfigurer {
	@Autowired
    private DataSource dataSource;
	
    @Bean
    public FacebookConnectionFactory facebookConnectionFactory(Environment env) {
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(env.getProperty("facebook.appId"), env.getProperty("facebook.appSecret"));
        facebookConnectionFactory.setScope("email");
        return facebookConnectionFactory;
    }

    /**
	 * 允許應用添加需要支持的社交網絡對應的連接工廠的實現
	 * Spring Social提供了以動態，自定服務方式建立與一個或多個提供商的連接
	 * @author Charles
	 * @date 2017年10月15日 下午11:07:12
	 * @param cfConfig
	 * @param env
	 * @return void
	 */
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
        cfConfig.addConnectionFactory(facebookConnectionFactory(env));
        //可以註冊多個第三方社交工廠
        //registry.addConnectionFactory(new TwitterConnectionFactory("consumerKey", "consumerSecret"));
    }

    /**
	 * 獲取當前登錄用戶的訊息
	 * @author Charles
	 * @date 2017年10月15日 下午11:08:21
	 * @return UserIdSource
	 */
    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

//    @Override
//    @Bean
//    public UserIdSource getUserIdSource() {
//        return new UserIdSource() {
//            @Override
//            public String getUserId() {
//                User user = getUser();
//                if (user == null) {
//                    throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
//                }
//                return user.getId();
//            }
//        };
//    }
    
    /**
	 * 用來管理用戶與社交網絡服務提供者之間的對應關係
	 * @author Charles
	 * @date 2017年10月15日 下午11:09:09
	 * @param connectionFactoryLocator
	 * @return UsersConnectionRepository
	 */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }
    
    /**
	 * 重新連接
	 * @author Charles
	 * @date 2017年10月15日 下午9:19:09
	 * @return ReconnectFilter
	 */
//    @Bean
//    public ReconnectFilter apiExceptionHandler(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
//        return new ReconnectFilter(usersConnectionRepository, userIdSource);
//    }
}
