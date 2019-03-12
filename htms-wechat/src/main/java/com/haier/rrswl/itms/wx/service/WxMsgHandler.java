package com.haier.rrswl.itms.wx.service;

import com.haier.rrswl.itms.wx.dto.Scan;

/**
 * 微信消息处理接口
 */
public interface WxMsgHandler {

    /**
     * 消息处理
     * @param scan 接收的微信消息
     * @return xml格式的字符串
     */
    String dealMsg(Scan scan);
}
