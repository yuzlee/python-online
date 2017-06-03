package com.rolrence.bulletscreen.controller;

import com.rolrence.bulletscreen.service.PythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Created by Rolrence on 2017/5/31.
 *
 */
@Controller
@RequestMapping("/python")
public class PythonController {
    @Autowired
    private PythonService pythonService;

    @RequestMapping(value = "/editor", method = RequestMethod.GET)
    public String editor() {
        return "python/editor";
    }

}
