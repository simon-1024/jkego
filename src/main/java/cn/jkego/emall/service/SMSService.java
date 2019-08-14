package cn.jkego.emall.service;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class SMSService implements Serializable {

    @Autowired
    private Jedis jedis;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送手机验证码
     *
     * @param mobile
     */
    public void sendSms(String mobile) {

        //生成六位数字随机数
        String randomNumeric = RandomStringUtils.randomNumeric(6);

        //在缓存中留一份
        jedis.set("checkcode_" + mobile, randomNumeric);
        //设置缓存过期时间
        //jedis.expire("checkcode_" + mobile, 300);

        //给用户发一份
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("randomNumeric", randomNumeric);
        rabbitTemplate.convertAndSend("sms", map);


    }


    //检查输入的验证码是否正确
    public boolean checkcode(String mobile, String checkcode) {

        if (checkcode.equals(jedis.get("checkcode_" + mobile))) {

            return true;
        }
        return false;
    }



}
