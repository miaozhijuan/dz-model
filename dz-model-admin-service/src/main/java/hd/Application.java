package hd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理服务端启动
 * 
 * @author mao
 *
 */
@MapperScan(basePackages = "hd.mapper")
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