package com.ssitcloud.common.netty.server;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public final class SecureChatSslContextFactory {
	
	 private static final String PROTOCOL = "TLS";
	
	private static SSLContext SERVER_CONTEXT;//服务器安全套接字协议
	
    private static SSLContext CLIENT_CONTEXT;//客户端安全套接字协议
    
    private static PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    
	public static SSLContext getServerContext(String pkPath,String caPath,String keyPwd,String trustPwd){
		
		if(SERVER_CONTEXT!=null) return SERVER_CONTEXT;
			//客户端SSL私钥
	        char[] keyPassphrase = keyPwd.toCharArray();
	        //客户端keystore 
	        char[] trustPassphrase = trustPwd.toCharArray();
	        
	        KeyManagerFactory kmf = null;
	        TrustManagerFactory tmf = null;
			try {
				//密钥管理器
				if(pkPath!=null){
					Resource keyStoreResource = resolver.getResource(pkPath);
					KeyStore ks = KeyStore.getInstance("PKCS12");
					ks.load(keyStoreResource.getInputStream(), keyPassphrase);
					
					kmf = KeyManagerFactory.getInstance("SunX509");
					kmf.init(ks, keyPassphrase);
				}
				
				if(caPath!=null){
					Resource keyTrustResource = resolver.getResource(caPath);
					KeyStore tks = KeyStore.getInstance("JKS");
					tks.load(keyTrustResource.getInputStream(), trustPassphrase);
					
					tmf = TrustManagerFactory.getInstance("SunX509");
					tmf.init(tks);
				}
				
				SERVER_CONTEXT = SSLContext.getInstance("TLS");
				SERVER_CONTEXT.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		

		return SERVER_CONTEXT;
	 }
	
	
	 public static SSLContext getClientContext(String pkPath,String caPath,String keyPwd,String trustPwd){
		 if(CLIENT_CONTEXT!=null) return CLIENT_CONTEXT;
		 try{
			KeyManagerFactory kmf = null;
			if (pkPath != null) {
				Resource KeyStoreResource = resolver.getResource(pkPath);
				
			    KeyStore ks = KeyStore.getInstance("JKS");
			    ks.load(KeyStoreResource.getInputStream(), keyPwd.toCharArray());
			    
			    kmf = KeyManagerFactory.getInstance("SunX509");
			    kmf.init(ks, keyPwd.toCharArray());
			}
				
			TrustManagerFactory tf = null;
			if (caPath != null) {
				
				Resource KeyTrustResource = resolver.getResource(caPath);
				
			    KeyStore tks = KeyStore.getInstance("JKS");
			    tks.load(KeyTrustResource.getInputStream(), trustPwd.toCharArray());
			    
			    tf = TrustManagerFactory.getInstance("SunX509");
			    tf.init(tks);
			}
			 
			 CLIENT_CONTEXT = SSLContext.getInstance(PROTOCOL);
			 //初始化此上下文
			 //参数一：认证的密钥      参数二：对等信任认证  参数三：伪随机数生成器 。 由于单向认证，服务端不用验证客户端，所以第二个参数为null
			 CLIENT_CONTEXT.init(kmf.getKeyManagers(),tf.getTrustManagers(), null);
			 
		 }catch(Exception e){
			 throw new Error("Failed to initialize the client-side SSLContext");
		 }
		 
		 return CLIENT_CONTEXT;
	 }

}
