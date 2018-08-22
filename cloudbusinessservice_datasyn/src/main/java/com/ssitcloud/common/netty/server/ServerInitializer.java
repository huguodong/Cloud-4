package com.ssitcloud.common.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;

import java.nio.charset.Charset;

import javax.net.ssl.SSLEngine;

import com.ssitcloud.common.netty.server.handler.CloudSyncHandlerAdapter;
import com.ssitcloud.common.netty.server.protocol.CloudSyncDecoder;
import com.ssitcloud.common.netty.server.protocol.CloudSyncEncoder;
import com.ssitcloud.common.netty.server.protocol.SyncProtocolDecoder;
import com.ssitcloud.common.util.PropertiesUtil;

/**
 * Created by yeyalin
 */
@SuppressWarnings({ "unused" })
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * 信任库 地址
	 */
	private static String sCaPath;
	/**
	 * 密钥 地址
	 */
	private static String sChatPath;
	
	/**
	 * 私钥密码
	 */
	private static String keyPwd;
	/**
	 * 授信中心密码
	 */
	private static String trustPwd;
	
	/**
	 * 1-开启SSL认证
	 * 0-不开启SSL认证
	 */
	private static String  isOpenSSL;
	
	static {
		sCaPath = PropertiesUtil.getConfigPropValueAsText("netty.ssl.sCaPath");
		sChatPath = PropertiesUtil.getConfigPropValueAsText("netty.ssl.sChatPath");
		keyPwd = PropertiesUtil.getConfigPropValueAsText("netty.ssl.keyPwd");
		trustPwd = PropertiesUtil.getConfigPropValueAsText("netty.ssl.trustPwd");
		isOpenSSL = PropertiesUtil.getConfigPropValueAsText("netty.ssl.isOpen");
	}
	

	@Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
		
        ChannelPipeline cp = socketChannel.pipeline();
        
        if("1".equals(isOpenSSL)){
        	
        	SSLEngine sslEngine = SecureChatSslContextFactory.getServerContext(sChatPath,sCaPath,keyPwd,trustPwd).createSSLEngine();
        	sslEngine.setUseClientMode(false);//设置服务端模式
        	sslEngine.setNeedClientAuth(true);//需要客户端验证
        	
        	cp.addLast(new SslHandler(sslEngine));
        }
        //报文以$_作为分隔符，可以解决半包黏包的问题
        //ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes(Charset.forName("GBK")));
        //cp.addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
        //指定报文：消息头+整包长度+消息体，通过长度获取消息体来决解半包黏包的问题
        cp.addLast(new SyncProtocolDecoder(Integer.MAX_VALUE, 4, 4, -8,8));
        cp.addLast(new StringDecoder(Charset.forName("GBK")));
        cp.addLast(new StringEncoder(Charset.forName("GBK")));
		cp.addLast(new CloudSyncEncoder());
		cp.addLast(new CloudSyncHandlerAdapter());
    }
}
