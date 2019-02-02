package hd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * PC商品客户端启动
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

}