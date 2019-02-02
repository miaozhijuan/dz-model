package hd;

import hd.lock.GoodsLock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * redis服务器启动
 * 
 * @author mao
 *
 */
@SpringBootApplication
public class Application {
	@Autowired
	private Environment env;

	/**
	 * 手工启动项目
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 获取zookeeper客户端
	 * 
	 * @return
	 */
	@Bean("client")
	public CuratorFramework getCuratorFrameworkFactory() {
		CuratorFramework client = null;
		client = CuratorFrameworkFactory.builder().connectString(env.getProperty("connectString")).sessionTimeoutMs(Integer.parseInt(env.getProperty("sessionTimeoutMs")))
				.namespace(env.getProperty("namespace")).retryPolicy(new RetryNTimes(Integer.parseInt(env.getProperty("n")), Integer.parseInt(env.getProperty("sleepMsBetweenRetries")))).build();
		client.start();
		return client;
	}

	/**
	 * 注入商品库存锁
	 * 
	 * @return
	 */
	@Bean("goodsLock")
	public GoodsLock getGoodsLock() {
		return new GoodsLock(getCuratorFrameworkFactory(), env.getProperty("lockProject"), env.getProperty("goodsLock"));
	}
}