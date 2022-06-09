package blog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //this a comment
@RequestMapping("/")
    public String index() {
        return "index";
    }
}