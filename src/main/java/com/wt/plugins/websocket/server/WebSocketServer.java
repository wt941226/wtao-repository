package com.wt.plugins.websocket.server;


import com.wt.plugins.websocket.config.WsEndPointConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket 服务
 *
 * @author wtao
 * @date 2019-10-22 10:36
 */

@ServerEndpoint(value = "/websocket/{userId}", configurator = WsEndPointConfig.class)
@Component
@Slf4j
@Data
@Scope(value = "prototype")
public class WebSocketServer {


    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static int onlineCount = 0;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收用户ID
     */
    private Integer userId;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId) {
        this.session = session;
        // 在线数加1
        addOnlineCount();
        log.info("有新用户连接: userId = {},当前在线人数为: {}", userId, getOnlineCount());
        webSocketSet.add(this);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 从set中删除
        webSocketSet.remove(this);
        // 在线数减1
        subOnlineCount();
        log.info("有一连接关闭！当前在线人数为: {}", getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自用户 ：userId = {} 的信息: {}", userId, message);
    }


    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message, Integer userId) throws IOException {
        log.info("推送消息到用户: {} ，推送内容: {}", userId, message);

        for (WebSocketServer item : webSocketSet) {

            if (item.userId.equals(userId)) {

                item.sendMessage(message);
            }
        }

    }

    /**
     * 发生错误时 调用的方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }


    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {

        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
