package com.login.kotlin

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class HtmlController {

    //root page
    @RequestMapping("/")
    fun index(model: Model): String {
            return "index"
    }
}
