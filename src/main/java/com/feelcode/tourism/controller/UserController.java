package com.feelcode.tourism.controller;

import com.feelcode.tourism.base.controller.BaseController;
import com.feelcode.tourism.base.utils.StateParameter;
import com.feelcode.tourism.entity.User;
import com.feelcode.tourism.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: UserController
 * @Auther: 朱利尔
 * @Date: 2020/4/30 17:30
 * @Description:
 */
@Controller
@RequestMapping(value="/s/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    /**
     * @auther: zhangyingqi
     * @date: 17:37 2020/4/30
     * @param: [request, user]
     * @return: org.springframework.ui.ModelMap
     * @Description: 用户保存&更新
     */
    @RequestMapping(value="/add", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap add(User user){
        try {
            if(StringUtils.isEmpty(user.getId())){
                user.setId(getUuid());
            }else{
                user.setUpdateDate(new Date());
            }
            userService.save(user);
            logger.info("保存成功");
            return getModelMap(StateParameter.SUCCESS, user, "保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "保存失败");
        }
    }

    /**
     * @auther: 朱利尔
     * @date: 17:47 2020/4/30
     * @param: [id]
     * @return: org.springframework.ui.ModelMap
     * @Description: 删除用户
     */
    @RequestMapping(value="/delete", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap delete(String id){
        try {
            User user = userService.findById(id);
            if(user==null){
                return getModelMap(StateParameter.FAULT, user, "找不到该用户");
            }
            userService.delete(user);
            logger.info("删除成功");
            return getModelMap(StateParameter.SUCCESS, null, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "删除失败");
        }
    }

    /**
     * @auther: 朱利尔
     * @date: 17:47 2020/4/30
     * @param: [request]
     * @return: java.lang.String
     * @Description: 查询用户列表
     */
    @RequestMapping(value="/list", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelMap view(HttpServletRequest request){
        List<User> list = userService.findAll();
        request.setAttribute("list", list);
        logger.info("返回列表页面");
        //return "/demoPage/listPage";
        return getModelMap(StateParameter.SUCCESS, list, "获取用户列表成功");
    }

}
