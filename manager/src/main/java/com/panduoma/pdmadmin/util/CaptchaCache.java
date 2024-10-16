package com.panduoma.pdmadmin.util;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CaptchaCache {
    // 验证码缓存
    private static ConcurrentHashMap<String, String> captchaMap = new ConcurrentHashMap<>();

    //存储验证码
    public void storeCaptcha(String captchaId, String captcha) {
        captchaMap.put(captchaId, captcha);
    }

    //删除验证码
    public void removeCaptcha(String captchaId) {
        captchaMap.remove(captchaId);
    }

    //验证验证码
    public boolean validateCaptcha(String captchaId, String captcha) {
        //获取存储的验证码
        String captchaCode = captchaMap.get(captchaId);
        if (captchaCode == null) {
            return false;
        } else {
            //验证码正确
            if (captchaCode.equals(captcha)) {
                //删除验证码
                captchaMap.remove(captchaId);
                return true;
            } else {
                //验证码错误
                return false;
            }
        }
    }

}
