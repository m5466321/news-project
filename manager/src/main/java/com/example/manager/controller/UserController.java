package com.example.manager.controller;

import com.example.manager.aspect.SystemLogger;
import com.example.manager.config.MD5Config;
import com.example.manager.entity.PageBean;
import com.example.manager.entity.Result;
import com.example.manager.entity.User;
import com.example.manager.entity.UserInfo;
import com.example.manager.service.UserService;
import com.example.manager.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Log4j2
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public Result login(@RequestBody @Validated User user) {
        //根据用户名查询用户
        log.info(user.getUsername()+"登录系统");
        User loginUser = userService.findByUserName(user.getUsername());
        //判断该用户是否存在
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        //判断密码是否正确
        if (MD5Config.getMD5String(user.getPassword()).equals(loginUser.getPassword())) {
            //登录成功
            log.info(user.getUsername()+"登陆成功");
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            claims.put("role", loginUser.getRole());
            String token = JwtUtil.genToken(claims);
            UserInfo userInfo = userService.findByUserInfo(user.getUsername());
            userService.addOpLogin(userInfo.getUsername());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userInfo", userInfo);
            return Result.success(map);
        }else {
            return Result.error("密码错误");
        }

    }
    @PostMapping("/register")
    public Result register(@RequestBody @Validated User register) {
        if(register.getUsername().isEmpty() || register.getPassword().isEmpty()) {
            return Result.error("参数不允许为空");
        }else {
            //查询用户
            String username=register.getUsername();
            User u = userService.findByUserName(username);
            if (u == null) {
                log.info("用户"+register.getUsername()+"成功注册");
                //没有占用
                //注册
                register.setPassword(MD5Config.getMD5String(register.getPassword()));
                userService.register(register);
                return Result.success("注册成功");
            } else {
                //占用
                return Result.error("用户名已被占用");
            }
        }

    }
    @GetMapping("/userInfo")
    public Result<List<UserInfo>> userInfo(Integer id) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        log.info("获取用户信息的id:"+id);
        if (claims.get("id").equals(id.toString())) {//判断是否是本人
            return Result.success(userService.userInfo(id));
        }
        if (claims.get("username").equals("admin")) {//判断是否是管理员
            return Result.success(userService.userInfo(id));
        }else {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(800);
            return Result.error("权限不足");
        }
    }
    @SystemLogger(value = "修改用户信息")
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody @Validated UserInfo user) {
        log.info("修改了用户名为："+user.getUsername()+"的信息");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        if (claims.get("id").equals(user.getId())){//判断是否是本人
            userService.updateUserInfo(user);
            return Result.success("修改成功");
        }
        if (claims.get("username").equals("admin")) {//判断是否是管理员
            userService.updateUserInfo(user);
            return Result.success("修改成功");
        }else {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(800);
            return Result.error("权限不足");
        }
    }
    @SystemLogger(value = "删除用户")
    @DeleteMapping("/deleteUser")
    public Result deleteUser(Integer id) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        log.info("删除用户信息id:"+id);
        if (claims.get("id").equals(id)){//判断是否是本人
            userService.deleteUser(id);
            return Result.success("删除成功");
        }
        if (claims.get("username").equals("admin")) {//判断是否是管理员
            userService.deleteUser(id);
            return Result.success("删除成功");
        }else {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(800);
            return Result.error("权限不足");
        }
    }
    @SystemLogger(value = "更改头像")
    @PostMapping("/updateAvatar")
    public Result updateAvatar(@RequestBody UserInfo userInfo) {
        String avatarUrl="http://localhost:8088/images/"+userInfo.getAvatar();
        Integer id=userInfo.getId();
        userService.updateAvatar(avatarUrl,id);
        return Result.success();
    }
    @GetMapping("/getUserInfo")
    public Result<PageBean<UserInfo>> getUserInfo(Integer pageSize,Integer pageNum) {
        return Result.success(userService.getUserInfo(pageSize,pageNum));
    }

    @PutMapping("/changeRole")
    public Result changeRole(@RequestBody User user){
        log.info("修改用户权限："+user.getUsername());
        Integer id= Integer.valueOf(user.getId());
        Integer role=Integer.valueOf(user.getRole());
        userService.changeRole(id,role);
        return Result.success("权限修改成功");
    }
}
