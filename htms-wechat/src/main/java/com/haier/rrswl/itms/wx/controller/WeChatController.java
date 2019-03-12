package com.haier.rrswl.itms.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.haier.rrswl.itms.util.WxUtil;
import com.haier.rrswl.itms.wx.dto.Scan;
import com.haier.rrswl.itms.wx.factory.WxMsgHandlerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: 微信回调处理
 * @date: 2018/9/28 14:01
 */
@Controller
@RequestMapping("/wx")
public class WeChatController {


    @Value("${wx.token}")
    private String token;

    @Resource
    private WxMsgHandlerFactory wxMsgHandlerFactory;

    /**
     * 认证接口
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/index")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //按规则排序并加密
        boolean flag = WxUtil.checkSignature(token, signature, timestamp, nonce);
        if (flag)
            try (PrintWriter p = response.getWriter()) {
                p.print(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    /**
     * 消息接收处理
     *
     * @param scan     消息内容
     * @param response 返回微信
     */
    @PostMapping(value = "/index")
    public void post(@RequestBody Scan scan, HttpServletResponse response) {

        System.out.println(JSONObject.toJSONString(scan));
        StringBuffer str = new StringBuffer();
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        String message = wxMsgHandlerFactory.getHandler(scan.getMsgType()).dealMsg(scan);
        try {
            writer = response.getWriter();
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }

    }

}
