package com.gmail.volodymyrdotsenko.sbr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main controller for all requests within ReactJs.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/{[path:[^.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
