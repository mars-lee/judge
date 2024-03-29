package com.yoj.web.controller.user;

import com.yoj.web.bean.User;
import com.yoj.web.bean.util.Msg;
import com.yoj.web.cache.EmailCache;
import com.yoj.web.service.UserService;
import com.yoj.web.util.EmailSender;
import com.yoj.web.util.VerifyImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 重置密码控制层
 * @Author: lmz
 * @Date: 2019/9/27
 */
@RestController
@RequestMapping("/user/reset")
public class ResetPasswordController {

    @Autowired
    UserService userService;
    @Autowired
    EmailSender emailSender;
    @Autowired
    EmailCache emailCache;
    @Autowired
    VerifyImageUtil verifyImageUtil;

    @GetMapping("/emailCode/{email}")
    public Msg getEmailCode(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return Msg.fail("该邮箱没有被注册");
        }
        String checkCode = emailSender.sendResetPasswordEmail(email);
        if(checkCode == null){
            return Msg.fail("发送邮件失败，请稍后重试");
        }
        return Msg.success();
    }

    @PostMapping("/password")
    public Msg resetPassword(@RequestBody User user, HttpServletRequest httpServletRequest) {
        if(!verifyImageUtil.verify(httpServletRequest,user.getImageCode())){
            return Msg.fail().add("imageCode","验证码错误");
        }
        String checkCode = emailCache.getEmailCheckCode(user.getEmail());
        if (checkCode == null || !checkCode.equals(user.getEmailCode())) {
          return  Msg.fail().add("emailCode", "邮箱验证码错误");
        }
        if(userService.updateUserPasswordByEmail(user) == null){
            return Msg.fail("更新失败，请稍后重试");
        }
        emailCache.delEmailCheckCode(user.getEmail());
        return Msg.success();
    }


}
