package cn.wenzhuo4657.noifiterBot.app.tigger.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {
    private static final Logger log = LoggerFactory.getLogger(ThymeleafController.class);

    @GetMapping(value = "/index")
    public String hello(Model model) {
        model.addAttribute("home", "test");
        return "index";
    }
}
