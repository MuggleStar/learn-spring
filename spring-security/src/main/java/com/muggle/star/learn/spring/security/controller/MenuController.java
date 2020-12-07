package com.muggle.star.learn.spring.security.controller;

import org.springframework.web.bind.annotation.*;

/**
 * 测试url
 *
 * @author lujianrong
 * @since 2020/12/7 11:35
 */
@RestController
public class MenuController {


    @GetMapping("/menu/one")
    public String getOne(){
        return "getOne";
    }

    @PostMapping("/menu/one")
    public String postOne(){
        return "postOne";
    }
    @PutMapping("/menu/one")
    public String putOne(){
        return "putOne";
    }

    @DeleteMapping("/menu/one")
    public String deleteOne(){
        return "deleteOne";
    }

    @GetMapping("/menu/two")
    public String getTwo(){
        return "getTwo";
    }

    @PostMapping("/menu/two")
    public String postTwo(){
        return "postTwo";
    }
    @PutMapping("/menu/two")
    public String putTwo(){
        return "putTwo";
    }

    @DeleteMapping("/menu/two")
    public String deleteTwo(){
        return "deleteTwo";
    }

    @GetMapping("/menu/three")
    public String getThree(){
        return "getThree";
    }

    @PostMapping("/menu/three")
    public String postThree(){
        return "postThree";
    }
    @PutMapping("/menu/three")
    public String putThree(){
        return "putThree";
    }

    @DeleteMapping("/menu/three")
    public String deleteThree(){
        return "deleteThree";
    }


}
