package com.haier.rrswl.itms.wx.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class Scan {
    @XmlElement(name = "MsgId")
    private Integer MsgId;

    @XmlElement(name = "ToUserName")
    private String ToUserName;

    @XmlElement(name = "FromUserName")
    private String FromUserName;

    @XmlElement(name = "CreateTime")
    private Long CreateTime;

    @XmlElement(name = "MsgType")
    private String MsgType;

    @XmlElement(name = "Event")
    private String Event;

    @XmlElement(name = "EventKey")
    private String EventKey;

    @XmlElement(name = "Content")
    private String Content;

    // 位置消息
    @XmlElement(name = "Scale")
    private Long Scale;

    //位置中文描述
    @XmlElement(name = "Label")
    private String Label;

    public Integer getMsgId() {
        return MsgId;
    }

    public void setMsgId(Integer msgId) {
        MsgId = msgId;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getScale() {
        return Scale;
    }

    public void setScale(Long scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }
}
