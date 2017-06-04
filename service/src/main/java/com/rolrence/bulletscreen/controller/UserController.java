package com.rolrence.bulletscreen.controller;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.rolrence.bulletscreen.entity.User;
import com.rolrence.bulletscreen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rolrence on 2017/5/30.
 *
 */
@Controller
@SessionAttributes("user")
@RequestMapping("/user")
public class UserController {
    private static Map<String, User> users = new HashMap<String, User>();

    @Autowired
    private UserService userService;

    private void setUserCookies(HttpServletResponse response, User user) {
        response.addCookie(new Cookie("id", String.valueOf(user.getId())));
        response.addCookie(new Cookie("name", user.getName()));
        response.addCookie(new Cookie("password", user.getPassword()));
    }


    // /login?name=xxx&password=yyy
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    // /login?name=xxx&password=yyy
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String login(@RequestParam("name") String name, @RequestParam("password") String password,
                              ModelMap modelMap) {
        User user = userService.auth(name, password);
        if (user != null) {
            modelMap.addAttribute("user", user);
            return "redirect:/python/editor";
        }
        return "user/login";
    }

    // /add?name=xxx&password=yyy
    @ResponseBody
    @RequestMapping(value="/add")
    public boolean addUser(@RequestParam("name") String name, @RequestParam("password") String password) {
        boolean status = userService.add(name, password);
        return status;
    }

    // /update?type=[name|password]&value=[new_name|new_password]
    @ResponseBody
    @RequestMapping(value="/update")
    public boolean changeName(@ModelAttribute("user") User user,
                             @RequestParam("type") String type, @RequestParam("value") String value,
                              HttpServletResponse response) {
        boolean status = false;

        try {
            if (type.equals("name")) {
                this.userService.updateName(user, value);
                response.addCookie(new Cookie("name", value));
            } else if (type.equals("password")) {
                this.userService.updatePassword(user, value);
                response.addCookie(new Cookie("password", value));
            }
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @ResponseBody
    @RequestMapping(value="/count")
    public int userCount() {
        int count = userService.userCount();
        return count;
    }

    @ResponseBody
    @RequestMapping(value="/list")
    public List<User> userList() {
        List<User> users = userService.userList();
        return users;
    }


//    @RequestMapping(value="/list",method=RequestMethod.GET)
//    public ModelAndView getUserList(Model model){
//
//        ModelAndView mv = new ModelAndView();
//        List<User> userList = userService.getUserList();
//        System.out.println("log======table 'user' all records:"+userList.size());
//        mv.addObject("userList", userList);
//        mv.setViewName("user/list");
//        return mv;
//    }
//
//    @RequestMapping(value="/add",method=RequestMethod.GET)
//    public ModelAndView getAdd(){
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("user/add");
//        return mv;
//    }
//
//    @RequestMapping(value="/add",method=RequestMethod.POST)
//    public String add(@ModelAttribute("user") User user){
//        userService.create(user);
//        return "redirect:/user/list";
//    }
//
//    @RequestMapping(value="/show/{userid}",method=RequestMethod.GET)
//    public ModelAndView show(@PathVariable Long userid){
//        User user = userService.findOne(userid);
//
//        ModelAndView mv = new ModelAndView();
//        mv.addObject("user", user);
//        mv.setViewName("user/detail");
//        return mv;
//    }
//
//    @RequestMapping(value="/del/{userid}",method=RequestMethod.GET)
//    public String del(@PathVariable Long userid){
//        userService.deleteById(userid);
//
//        return "redirect:/user/list";
//    }
//
//    @RequestMapping(value="/edit/{userid}",method=RequestMethod.GET)
//    public ModelAndView getEdit(@PathVariable Long userid,Model model){
//        User user = userService.findOne(userid);
//        model.addAttribute("userAttribute", user);
//        ModelAndView mv = new ModelAndView();
//        mv.setViewName("user/edit");
//        return mv;
//    }
//
//    @RequestMapping(value="/save/{userid}",method=RequestMethod.POST)
//    public String saveEdit(@ModelAttribute("userAttribute") User user,@PathVariable Long userid){
//        userService.update(user);
//        return "redirect:/user/list";
//    }
}
