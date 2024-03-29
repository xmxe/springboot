package com.xmxe.config;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	/**
	 * 建立连接时调用
	 *
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		// 添加到channelGroup通道组
		MyChannelHandlerPool.channelGroup.add(ctx.channel());
	}

	/**
	 *
	 * 断开连接时调用
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		// 移除到channelGroup 通道组
		MyChannelHandlerPool.channelGroup.remove(ctx.channel());
	}

	/**
	 * 数据交互时调用
	 *
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 首次连接是FullHttpRequest，处理参数
		if (msg instanceof FullHttpRequest) {
			FullHttpRequest request = (FullHttpRequest) msg;
			String uri = request.uri();
			// 获取url参数及值
			Map<String,String> paramMap = getUrlParams(uri);
			System.out.println("接收到的参数是：" + JSON.toJSONString(paramMap));
			// 如果url包含参数，需要处理
			if (uri.contains("?")) {
				String newUri = uri.substring(0, uri.indexOf("?"));
				System.out.println("如果url包含参数需要处理,newUri:"+newUri);
				request.setUri(newUri);
			}

		} else if (msg instanceof TextWebSocketFrame) {
			//正常的TEXT消息类型
			TextWebSocketFrame frame = (TextWebSocketFrame) msg;
			System.out.println("服务器收到客户端数据：" + frame.text());
			sendAllMessage(frame.text());
		}
		super.channelRead(ctx, msg);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

	}

	private void sendAllMessage(String message) {
		// 收到信息后，群发给所有channel
		MyChannelHandlerPool.channelGroup.writeAndFlush(new TextWebSocketFrame(message));
	}

	/**
	 * 获取url参数及值
	 */
	private Map<String,String> getUrlParams(String url) {
		Map<String, String> map = new HashMap<>();
		url = url.replace("?", ";");
		if (!url.contains(";")) {
			return map;
		}
		if (url.split(";").length > 0) {
			String[] arr = url.split(";")[1].split("&");
			for (String s : arr) {
				String key = s.split("=")[0];
				String value = s.split("=")[1];
				map.put(key, value);
			}

		}
		return map;

	}
}