package clj.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration // 配置bean对象
public class MyHibernateDataSourceConfig {
	/**
	 * 将dataSource注成bean对象（连接数据库）
	 * @return
	 */
	@Bean("dataSource")
	public DriverManagerDataSource getConnection() {
		String url = "jdbc:mysql://localhost:3306/cuilijuan?useSSL=false";
		String userName = "root";
		String passWord = "12345678";
		String driverClassName = "com.mysql.jdbc.Driver";
		DriverManagerDataSource dataSource = new DriverManagerDataSource(url, userName, passWord);

		dataSource.setDriverClassName(driverClassName);
		return dataSource;
	}

	@Bean("transactionManager") // 事务管理
	public HibernateTransactionManager getDataSourceTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager d = new HibernateTransactionManager();
		d.setSessionFactory(sessionFactory);
		return d;

	}

	/**
	 * 开启session会话
	 * @param dataSource
	 * @return
	 */
	@Bean("sessionFactory")
	public LocalSessionFactoryBean getSessionFactory(DriverManagerDataSource dataSource) {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource);
		Properties hibernateProperties = new Properties();
//		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//		hibernateProperties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//		hibernateProperties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost/products");
//		hibernateProperties.setProperty("hibernate.connection.username", "root");
//		hibernateProperties.setProperty("hibernate.connection.password", "12345678");
		hibernateProperties.setProperty("dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
		hibernateProperties.setProperty("format_sql", "true");
		hibernateProperties.setProperty("show_sql", "true");
		bean.setHibernateProperties(hibernateProperties);
		bean.setPackagesToScan("clj.entity");//扫描这个包下的所有类，
		return bean;
	}

	/**
	 * 试图解析器
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		return new InternalResourceViewResolver("/WEB-INF/", ".html");
	}
	
	
	/**
	 * 只有这个bean对象才会将对象转换为json对象返回前端
	 * @return
	 */
	@Bean
	public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter()
	{
		RequestMappingHandlerAdapter ad = new RequestMappingHandlerAdapter();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter mc = new MappingJackson2HttpMessageConverter();
		mc.setDefaultCharset(Charset.forName("utf-8"));
		messageConverters.add(mc); 
		ad.setMessageConverters(messageConverters );
		return ad;
	}

}
