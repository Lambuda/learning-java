package com.haier.rrswl.itms.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.haier.rrswl.itms.util.HttpClient;
import com.haier.rrswl.itms.wx.dto.TemplateData;
import com.haier.rrswl.itms.wx.dto.WechatTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/wx/api")
public class WeiXinController {

    @GetMapping(value = "/test")
    @ResponseBody
    public String test() {
        return this.sendTemplateMessage();
    }

    /**
     * 发送消息
     *
     * @return
     */
    public String sendMessage() {
        JSONObject json = new JSONObject();
        json.put("touser", "oLGf_02EAkyW2TctO_c84K1-N38M");
        json.put("msgtype", "text");
        JSONObject text = new JSONObject();
        text.put("content", "测试");
        json.put("text", text);
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=19_5wZoROvho1eCKmWP-Ig2XNExipNAVC0PHleOMhdnWTRz1jHmvCq2D4QWbKvfQ__5XCHOODzD816MehL5k99ZjW0V6vO9wP1JBV8MnFhYN_B0HdwBIEP_CYVdug1RpzCOJ5iSwyNgAoamqhyPUKBhADAQGX";
        String doPostJson = HttpClient.doPostJson(url, json.toString());
        return doPostJson;
    }


    /**
     * 发送模板消息
     * @return
     */
    public String sendTemplateMessage() {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=19_5wZoROvho1eCKmWP-Ig2XNExipNAVC0PHleOMhdnWTRz1jHmvCq2D4QWbKvfQ__5XCHOODzD816MehL5k99ZjW0V6vO9wP1JBV8MnFhYN_B0HdwBIEP_CYVdug1RpzCOJ5iSwyNgAoamqhyPUKBhADAQGX";

        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id("zQmozFKOWseaAbJLRgvx4_MtKm7WrUTDxDpwopkpYu4");
        wechatTemplate.setTouser("oLGf_02EAkyW2TctO_c84K1-N38M");
        Map<String, TemplateData> mapdata = new HashMap<>();
        // 封装模板数据
        TemplateData first = new TemplateData();
        first.setValue("您好，您已注册成为XXX平台用户。");
        first.setColor("#173177");
        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();
        keyword1.setValue("18661851761");
        first.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        first.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
        keyword3.setValue("欢迎您的加入，请及时购买会员并完善资料>>");
        keyword3.setColor("#173177");
        mapdata.put("remark", keyword3);

        wechatTemplate.setData(mapdata);

        String doPostJson = HttpClient.doPostJson(url, JSONObject.toJSONString(wechatTemplate));
        return doPostJson;
    }

    /**
     * 获取UserAccessToken 实体类
     *
     * @return
     * @throws IOException
     */
    public String getUserAccessToken() {
        // 测试号信息里的appId
        String appId = "wxa66180286f215f05";
        // 测试号信息里的appsecret
        String appsecret = "b3de3293c4ddcec05a7b6c4e3c85ba17";
        // 根据传入的code，拼接出访问定义好的微信接口的url
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret="
                + appsecret;
        // 向相应的url发送请求获取token
        String tokenStr = HttpClient.doGet(url);
        JSONObject json = JSONObject.parseObject(tokenStr);

        //获取令牌【这个令牌每天只能请求2000次 但是又2个小时的超时时间，可以丢redis然后设置超时时间用 建议设置7100秒 这样一天下来也完全够用】
        String token = (String) json.get("access_token");
        System.out.println(token);
        return token;
    }
}
