package com.dev.rest.controller.apply;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apply/exception")
public class ExceptionController {

    @GetMapping("oom")
    public void oom() {
        System.err.println("------------start--------------" + LocalDateTime.now());
        List<String> list = new ArrayList<>();
        while (true) {
            list.add("abc");
        }
    }
}
