package com.haier.rrswl.itms.util;

import com.haier.rrswl.itms.wx.dto.Scan;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

public class TextMessageUtil {

    /**
     * 将发送消息封装成对应的xml格式
     */
    public static String messageToxml(Scan message) {
        XStream xstream = new XStream();
        xstream.alias("xml", message.getClass());
        return xstream.toXML(message);
    }

    /**
     * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
     *
     * @param FromUserName
     * @param ToUserName
     */
    public static String initMessage(String FromUserName, String ToUserName) {
        Scan text = new Scan();
        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setMsgType("text");
        text.setContent("欢迎开始微信公众号");
        text.setCreateTime(new Date().getTime());
        return messageToxml(text);
    }
}
