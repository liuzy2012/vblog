package com.vi3nty.blog.controller.admin;


import com.google.code.kaptcha.Producer;
import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import com.vi3nty.blog.entity.User;
import com.vi3nty.blog.service.IUserService;
import com.vi3nty.blog.utils.BlogUtil;
import com.vi3nty.blog.utils.RedisKeyUtil;
import com.vi3nty.blog.utils.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author vi3nty
 * @since 2019-09-10
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private Producer producer;

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    /**
     * 用户post方法提交登录信息
     * @return
     */
    @PostMapping("/tologin")
    @ResponseBody
    public ServerResponse<User> login(String email,String password,String kaptcha,@CookieValue("kaptchaOwner") String kaptchaOwner, HttpSession session){
        User user=new User(password,email);
        String code=null;
        if(StringUtils.isNotEmpty(kaptchaOwner)) {
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            code = (String) redisTemplate.opsForValue().get(redisKey);
            System.out.println("输入的验证码为="+kaptcha+"  系统中验证码为="+code);
            if(StringUtils.equalsIgnoreCase(code,kaptcha)) {
                //根据用户名和密码创建Token
                UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
                //获取subject认证主体
                Subject subject = SecurityUtils.getSubject();
                try {
                    //开始认证
                    subject.login(token);
                    User loginUser = iUserService.userLogin(user).getData();
                    session.setAttribute("userlogin", loginUser);
                    return iUserService.userLogin(user);
                } catch (Exception e) {
                    return ServerResponse.createByError();
                }
            }
            else
                return ServerResponse.createBySuccessMessage("验证码错误");
        }
        else {
            return ServerResponse.createByErrorMessage("请输入验证码");
        }
    }
    @PostMapping("/add")
    @ResponseBody
    public ServerResponse addUser(User user){
        return iUserService.addUser(user);
    }
    @GetMapping("/active/{uid}/{code}")
    @ResponseBody
    public ServerResponse activeUser(@PathVariable("uid") int uid,@PathVariable("code") String code){
        int result=iUserService.activeUser(uid,code);
        if(result==1)
            return ServerResponse.createBySuccess();
        return null;
    }
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response){
        //生成验证码
        String text=producer.createText();
        BufferedImage image=producer.createImage(text);
        //将图片输出给浏览器
        response.setContentType("image/png");
        //将验证码存入redis
        String kaptchaOwner=BlogUtil.generateUUID();
        Cookie cookie=new Cookie("kaptchaOwner",kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);
        String redisKey=RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey,text,60,TimeUnit.SECONDS);
        try {
            OutputStream os=response.getOutputStream();
            ImageIO.write(image,"png",os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping("/{uid}")
    public String getUserById(Model model,@PathVariable(name = "uid") int id){
        model.addAttribute("edresp",iUserService.getUserById(id));
        return "admin/admin-edituser";
    }
    @PostMapping("/update/{uid}")
    @ResponseBody
    public ServerResponse updateUser(HttpServletRequest request,User user,@PathVariable("uid") int id){
        //获取修改前的user，目的是删除旧头像
        User oldUser=iUserService.getUserById(id).getData();
        //当前form表单传来的头像不为空
        if(StringUtils.isNotEmpty(user.getAvatar())&&StringUtils.isNotEmpty(oldUser.getAvatar())){
            String oldPicName=oldUser.getAvatar().substring(oldUser.getAvatar().lastIndexOf("/"));
            File oldPicFile=new File(request.getServletContext().getRealPath("img/user/")+oldPicName);
            oldPicFile.delete();
        }
        user.setId(id);

        //如果前台没有修改密码
        if(!StringUtils.isNotEmpty(user.getPassword()))
            user.setPassword(oldUser.getPassword());
        int result=iUserService.updateUser(user);
        if(result==1)
            return ServerResponse.createBySuccess();
        else
            return ServerResponse.createByError();
    }
    @PostMapping("/uploadPic")
    @ResponseBody
    public ServerResponse uploadUserPic(@RequestParam("userPic")MultipartFile userPic,@RequestParam(value = "userId",required = false) String userId,HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("----------------ID="+userId+"      pic="+userPic.getOriginalFilename());
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        //根据当前时间生成随机文件名
        String fileName=System.currentTimeMillis()+userPic.getOriginalFilename().substring(userPic.getOriginalFilename().lastIndexOf("."));
        File imageFolder=new File(request.getServletContext().getRealPath("img/user/"));
        String destFileName=request.getServletContext().getRealPath("img/user/")+File.separator+fileName;
        if(!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        File destFile=new File(destFileName);
        userPic.transferTo(destFile);
        return ServerResponse.createBySuccess("/blog/img/user/"+fileName);
    }

}
