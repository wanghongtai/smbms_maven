package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Hunter on 2021-05-07.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/list.do")
    public String list(String queryUserName,
                       @RequestParam(defaultValue = "0") Integer queryUserRole,
                       @RequestParam(defaultValue = "1") Integer currentPageNo,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       Model model) throws Exception {

        //1、查询总记录数，计算总页数
        int count = userService.getUserCount(queryUserName, queryUserRole);
        //对页码和每页大小做兼容性处理
        PageSupport pageSupport = new PageSupport(currentPageNo, pageSize, count);
        //2、查询用户列表
        List<User> userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);

        //获取角色列表
        List<Role> roleList = roleService.getRoleList();

        //回显输入信息
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);


        model.addAttribute("userList", userList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("pageSupport", pageSupport);
        return "user/userlist";
    }

    @RequestMapping("/toadd.do")
    public String toAdd() {
        return "user/useradd";
    }


    @ResponseBody
    @RequestMapping("/user_exist.do")
    public Object user_exist(String userCode) {
        User user = userService.getUserByUserCode(userCode);
        return user != null ? true : false;
    }

    @ResponseBody
    @RequestMapping("/rolelist.do")
    public Object rolelist(String userCode) throws Exception {
        List<Role> roleList = roleService.getRoleList();
        return roleList;
    }


    @RequestMapping("/view/{id}")
    public String view(@PathVariable("id") Integer uid, Model model) throws Exception {
        User user = userService.getUserById(uid);
        model.addAttribute("user", user);
        return "user/userview";
    }

    @ResponseBody
    @RequestMapping("/add")
    public Object add(User user, HttpSession session,
                      @RequestParam(value = "idPicPath_1", required = false) MultipartFile file,
                      HttpServletRequest request) throws Exception {


        //获取项目实际路径
        String realPath = request.getServletContext().getRealPath("/statics/images/");
        //D:\Hunter\Dev\mywork\超市订单管理系统\smbms\web\statics

        //保存图片
        file.transferTo(new File(realPath + File.separator + file.getOriginalFilename()));//a.jpg
        //给user的idPicPath赋值
        user.setIdPicPath("statics/images/" + file.getOriginalFilename());

        User loginUser = (User) session.getAttribute(Constants.USER_SESSION);
        user.setCreationDate(new Date());
        user.setCreatedBy(loginUser.getId());
        boolean result = userService.add(user);
        return result;
        //return "redirect:/user/list.do";
    }


}
