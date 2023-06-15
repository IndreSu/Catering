package com.exam.Catering.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuccessController {

    @GetMapping("/success")
    public String successPage() {
        return "success"; // Return the name of the success page template (e.g., success.html)
    }
}
