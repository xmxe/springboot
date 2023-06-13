package com.xmxe.controller;

import com.xmxe.entity.User;
import com.xmxe.util.DocUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FreemarkerController {
	
	@GetMapping("/freemarker")
    public String fm(Model model) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            user.setUsername("username>>>>" + i);
            user.setPassword("password>>>>" + i);
            users.add(user);
        }
        model.addAttribute("users", users);
        return "user";
	}

    @GetMapping("/createDoc")
    public void createDoc() {
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("aa","haha");dataMap.put("bb","hehe");dataMap.put("cc","jiji");
        String templatePath = "freemarker/createDoc.ftl";
        try {
            OutputStream os = new FileOutputStream(new File("C:\\Users\\wangx\\Desktop\\createDoc.doc"));
            DocUtil.createDoc(dataMap,templatePath,os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
