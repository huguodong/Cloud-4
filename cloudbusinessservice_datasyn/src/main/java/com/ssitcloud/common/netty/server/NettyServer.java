package com.ssitcloud.common.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ssitcloud.common.util.PropertiesUtil;
import com.ssitcloud.redisutils.JedisUtils;

/**
 * netty服务器端
 * @author yeyalin
 *
 */

@Component
public class NettyServer implements ServletContextListener,ApplicationContextAware, InitializingBean {


	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;

	private static ThreadPoolExecutor threadPoolExecutor;
	
	private static int port;

	public static Map<String, Object> proxyBeanMap = new ConcurrentHashMap<>();
	
	static{
		port = Integer.parseInt(PropertiesUtil.getConfigPropValueAsText("netty.server.port"));
	}
	
	/**
	 * 初始化redis客户端
	 */
	public  static JedisUtils redis = null;
	
	public NettyServer() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		
		redis = JedisUtils.getInstance();
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		Map<String, Object> serviceBeanMap = ctx
				.getBeansWithAnnotation(RpcService.class);
		if (MapUtils.isNotEmpty(serviceBeanMap)) {
			for (Object serviceBean : serviceBeanMap.values()) {
				String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).name();
				proxyBeanMap.put(interfaceName, serviceBean);
			}
		}

	}

	public void start() throws Exception {
		if (bossGroup == null && workerGroup == null) {
			//Reactor主从模型线程模式
			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap
					.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
					.childOption(ChannelOption.SO_RCVBUF, 128 * 1024)
					.childOption(ChannelOption.SO_SNDBUF, 128 * 1024)
					.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);


			ChannelFuture future = bootstrap.bind(port).sync();

			future.channel().closeFuture().sync();
		}
	}

	public void stop() {
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}

	public static void submit(Runnable task) {
		if (threadPoolExecutor == null) {
			synchronized (NettyServer.class) {
				if (threadPoolExecutor == null) {
					threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
							TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
									65536));
				}
			}
		}
		threadPoolExecutor.submit(task);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.err.println("nettyListener Startup!");  
        new Thread(){  
            @Override  
            public  void run(){  
                try {  
                    new NettyServer().start();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }.start();  
      
        System.err.println("nettyListener end!");  
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		new NettyServer().stop() ;
		
	}

}
