package com.sophia.blog_java_backend.controller;

import com.sophia.blog_java_backend.annotation.LoginRequired;
import com.sophia.blog_java_backend.entity.User;
import com.sophia.blog_java_backend.service.LikeService;
import com.sophia.blog_java_backend.service.UserService;
import com.sophia.blog_java_backend.util.CommunityConstant;
import com.sophia.blog_java_backend.util.CommunityUtil;
import com.sophia.blog_java_backend.util.HostHolder;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "site/setting";
    }

    @LoginRequired
    @RequestMapping(path="/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有上传图片！");
            return "site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确！");
            return "site/setting";
        }

        // 生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放的路径
        File dest = new File(uploadPath+"/"+fileName);
        try {
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败："+ e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！",e);
        }
        //更新当前用户头像的路径（web访问路径）
        // http://localhost:8080/community/user/header/XXX.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/test";
    }

    @RequestMapping(path="header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放的路径
        fileName = uploadPath + "/" + fileName;
        // 文件的后缀
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                )
        {
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
        }
    }

    @LoginRequired
    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(Model model, String prevPassword, String newPassword, String confirmPassword) {
        if (StringUtils.isBlank(prevPassword)) {
            model.addAttribute("error", "旧密码不能为空！");
            return "site/setting";
        }

        // make sure the previous password is correct
        // get user
        User user = hostHolder.getUser();
        if (!user.getPassword().equals(CommunityUtil.md5(prevPassword+user.getSalt()))) {
            model.addAttribute("error", "旧密码错误！");
            return "site/setting";
        }

        if (StringUtils.isBlank(newPassword)) {
            model.addAttribute("error", "新密码不能为空！");
            return "site/setting";
        }

        // make sure the previous password is not equal to the new one
        if (user.getPassword().equals(CommunityUtil.md5(newPassword+user.getSalt()))) {
            model.addAttribute("error", "新旧密码相同！");
            return "site/setting";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "确认密码错误！");
            return "site/setting";
        }

        String newEncrypted = CommunityUtil.md5(newPassword+user.getSalt());
        userService.updatePassword(user.getId(),newEncrypted);
        return "redirect:/test";
    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在！");
        }

        // 用户
        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        return "site/profile";
    }
}
