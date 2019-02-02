package hd.lock;

import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品锁
 * 
 * @author mao
 *
 */
public class GoodsLock {

	/**
	 * zookeeper客户端
	 */
	private CuratorFramework client;

	/**
	 * 分布式锁总节点
	 */
	private String lockProject;

	/**
	 * 当前锁总节点
	 */
	private String currentLockProject;

	/**
	 * 计时器
	 */
	private static CountDownLatch countDownLatch = new CountDownLatch(1);

	/**
	 * 日志工具
	 */
	private final static Logger logger = LoggerFactory.getLogger(GoodsLock.class);

	/**
	 * 日志工具
	 */
	private final static String SLASH = "/";

	public GoodsLock() {
	}

	/**
	 * 项目初始化时判断节点是否存在并针对节点做监听
	 */
	public GoodsLock(CuratorFramework client, String lockProject, String currentLockProject) {
		super();
		this.client = client;
		this.lockProject = lockProject;
		this.currentLockProject = currentLockProject;
		try {
			if (client.checkExists().forPath(SLASH + lockProject) == null) {
				client.create().withMode(CreateMode.PERSISTENT).withACL(Ids.OPEN_ACL_UNSAFE).forPath(SLASH + lockProject);
			}
			addWatcherToLock(SLASH + lockProject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("客户端链接zookeeper服务失败。。。。请重试。。。。");
		}
	}

	/**
	 * 获取并打开锁
	 */
	public void openLock() {
		// 使用死循环，当且仅当一个锁释放并且当请请求成功后才跳出
		while (true) {
			try {
				client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).withACL(Ids.OPEN_ACL_UNSAFE).forPath(SLASH + lockProject + SLASH + currentLockProject);
				logger.info("创建分布式锁成功。。。。");
				return;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取分布式锁失败。。。。");
				try {
					// 如果没获取到锁，需要重新设置同步资源值
					if (countDownLatch.getCount() <= 0) {
						countDownLatch = new CountDownLatch(1);
					}
					// 阻塞线程
					countDownLatch.await();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 释放分布式锁
	 * 
	 * @return
	 */
	public boolean releaseLock() {
		try {
			if (client.checkExists().forPath(SLASH + lockProject + SLASH + currentLockProject) != null) {
				client.delete().forPath(SLASH + lockProject + SLASH + currentLockProject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("释放锁失败。。。。");
			return false;
		}
		logger.info("释放锁成功。。。。");
		return true;
	}

	/**
	 * 给主节点添加监听事件
	 * 
	 * @param path
	 * @throws Exception
	 */
	public void addWatcherToLock(String path) throws Exception {
		@SuppressWarnings("resource")
		PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
		pathChildrenCache.start(StartMode.POST_INITIALIZED_EVENT);
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
					String path = event.getData().getPath();
					logger.info("上一个会话已释放锁货会话已断开，节点路劲为{}", path);
					if (path.contains(currentLockProject)) {
						logger.info("释放计时器，让当前请求来获取分布式锁");
						countDownLatch.countDown();
					}
				}
			}
		});
	}
}
