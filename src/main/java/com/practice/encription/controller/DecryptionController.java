package com.practice.encription.controller;

import com.practice.encription.domain.MyRequestBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luo Bao Ding
 * @since 2018/8/30
 */
@RestController
public class DecryptionController {


    @RequestMapping("/decrypt")
    public String consume(HttpServletRequest request, HttpServletResponse response, @RequestBody MyRequestBody requestBody) {
        System.out.println( "requestBody = [" + requestBody + "]");
        return "success";
    }
}
