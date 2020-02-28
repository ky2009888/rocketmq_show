package org.ky2009666.apps.mq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestShowController {
    /**
     * show 方法
     *
     * @return String
     */
    @GetMapping("/show")
    public String show() {
        return "Show Hello";
    }
}
