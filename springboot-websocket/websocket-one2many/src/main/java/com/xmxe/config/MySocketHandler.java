package com.xmxe.config;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.xmxe.util.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MySocketHandler {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SocketIOServer socketIoServer;

	@PostConstruct
	private void start() {
		try {
			socketIoServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	private void destroy() {
		try {
			socketIoServer.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 连接
	 */
	@OnConnect
	public void connect(SocketIOClient client) {
		String userFlag = client.getHandshakeData().getSingleUrlParam("userFlag");
		SocketUtil.connectMap.put(userFlag, client);
		log.info("客户端userFlag: " + userFlag + "已连接");
	}

	/**
	 * 退出
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		String userFlag = client.getHandshakeData().getSingleUrlParam("userFlag");
		log.info("客户端userFlag:" + userFlag + "断开连接");
		SocketUtil.connectMap.remove(userFlag, client);
	}
}