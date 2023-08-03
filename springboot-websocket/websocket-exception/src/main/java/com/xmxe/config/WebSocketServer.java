package com.xmxe.config;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 前端通过@ServerEndpoint("/webSocket/{uid}") 与后端建立链接
 */

@ServerEndpoint("/webSocket/{uid}")
@Component
public class WebSocketServer {

	private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static final AtomicInteger onlineNum = new AtomicInteger(0);

	// concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
	private static CopyOnWriteArraySet<Session> sessionPools = new CopyOnWriteArraySet<Session>();

	/**
	 * 有客户端连接成功
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "uid") String uid){
		sessionPools.add(session);
		onlineNum.incrementAndGet();
		log.info(uid + "加入webSocket！当前人数为" + onlineNum);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		sessionPools.remove(session);
		int cnt = onlineNum.decrementAndGet();
		log.info("有连接关闭，当前连接数为：{}", cnt);
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(Session session, String message) throws IOException {
		if(session != null){
			synchronized (session) {
				session.getBasicRemote().sendText(message);
			}
		}
	}

	/**
	 * 群发消息
	 */
	public void broadCastInfo(String message) throws IOException {
		for (Session session : sessionPools) {
			if(session.isOpen()){
				sendMessage(session, message);
			}
		}
	}

	/**
	 * 发生错误
	 */
	@OnError
	public void onError(Session session, Throwable throwable){
		log.error("发生错误");
		throwable.printStackTrace();
	}

}