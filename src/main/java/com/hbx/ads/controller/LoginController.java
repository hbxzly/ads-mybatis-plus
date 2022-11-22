package com.hbx.ads.controller;


import com.hbx.ads.domain.ActiveUser;
import com.hbx.ads.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.hbx.ads.common.Constants.VALIDATE_CODE;

@Controller
public class LoginController {

    @RequestMapping(value = {"/login","/"})
    public String login() {
        return "login";
    }

    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {

        Subject subject = SecurityUtils.getSubject();
        ActiveUser activeUser = (ActiveUser)subject.getPrincipal();


        model.addAttribute("activeUser", activeUser);


        return "home";
    }

    @RequestMapping("/ajaxLogin")
    @ResponseBody
    public Map<String, Object> ajaxLogin(@RequestParam String username, @RequestParam String password,
                                         @RequestParam(required = false) String randomcode, HttpSession session) {


        Map<String, Object> map = new HashMap<>();
        if (randomcode != null && !randomcode.equals("")) {
            //取出session的验证码（正确的验证码）
            String validateCode = (String) session.getAttribute(VALIDATE_CODE);
            //页面中输入的验证和session中的验证进行对比
            if (validateCode != null && !randomcode.equals(validateCode)) {
                //如果校验失败，将验证码错误失败信息放入map中
                map.put("msg", "randomcode_error");
                //直接返回，不再校验账号和密码
            }
        }

        Subject currentUser = SecurityUtils.getSubject();

        if (!currentUser.isAuthenticated()){
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            try{
                currentUser.login(token);
            }catch(UnknownAccountException ex){
                map.put("msg", "account_error");
            }catch(IncorrectCredentialsException ex){
                map.put("msg", "password_error");
            }catch(AuthenticationException ex){
                map.put("msg", "authentication_error");
            }

        }

        return map;

    }

}




