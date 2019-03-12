package com.haier.rrswl.itms.wx.factory;

import com.haier.rrswl.itms.wx.service.WxMsgHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信消息处理接口 工厂类
 */
@Component
public class WxMsgHandlerFactory {

    /**
     * handler 接口池
     */
    @Autowired
    private final Map<String, WxMsgHandler> handler = new ConcurrentHashMap<String, WxMsgHandler>(4);


    /**
     * 获取微信消息处理接口
     *
     * @param type 消息类型
     * @return 消息处理handler
     */
    public WxMsgHandler getHandler(String type) {
        WxMsgHandler wxMsgHandler = handler.get(type);
        if (wxMsgHandler == null) throw new RuntimeException("no WxMsgHandler defind");
        return wxMsgHandler;
    }

}
