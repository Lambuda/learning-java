package com.haier.rrswl.itms.wx.service.impl;

import com.haier.rrswl.itms.util.TextMessageUtil;
import com.haier.rrswl.itms.wx.dto.Scan;
import com.haier.rrswl.itms.wx.service.WxMsgHandler;
import org.springframework.stereotype.Service;


/**
 * 消息类型为 TEXT 文本类型 处理器
 */
@Service("text")
public class TextMsgHandler implements WxMsgHandler {

    /**
     * 消息处理
     * @param scan 接收的微信消息
     * @return 返回类型为xml 格式的字符串
     */
    @Override
    public String dealMsg(Scan scan) {
        String message = TextMessageUtil.initMessage(scan.getFromUserName(), scan.getToUserName());
        return message;
    }
}
