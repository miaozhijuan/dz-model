package hd;

import javax.servlet.Servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 后台管理客户端启动
 * 
 * @author mao
 *
 */
@ServletComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {
	/**
	 * 手工启动项目
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 设置 DispatcherServlet
	 * 
	 * @param dispatcherServlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean<Servlet> dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean<Servlet> registration = new ServletRegistrationBean<Servlet>(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.casual");
		registration.addUrlMappings("*.css");
		registration.addUrlMappings("*.js");
		registration.addUrlMappings("*.woff");
		registration.addUrlMappings("*.htm");
		registration.addUrlMappings("*.gif");
		registration.addUrlMappings("*.png");
		registration.addUrlMappings("*.jpg");
		return registration;
	}
}