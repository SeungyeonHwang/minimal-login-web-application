package com.login.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.security.MessageDigest
import javax.servlet.http.HttpSession
import kotlin.math.sign

@Controller
class HtmlController {

    @Autowired //SpringFramework Bean 자동 관리
    lateinit var repository: UserRepository //나중에 initialize

    //root page
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("title", "Home")
        return "index"
    }

    //password 암호화 : SHA-256, 비가역적, 복호화 불가능, 로그인할떄 다시 복호화, 매칭
    fun crypto(ss: String): String {
        val sha = MessageDigest.getInstance("SHA-256")
        val hexa = sha.digest(ss.toByteArray())
        val crypto_str = hexa.fold("", { str, it -> str + "%02x".format(it) }) //hexa Byte를 String으로 변환하는 코드
        return crypto_str
    }

    @GetMapping("/{formType}")
    fun htmlForm(model: Model, @PathVariable formType: String): String {
        var response: String = ""

        if (formType.equals("sign")) {
            response = "sign"
        } else if (formType.equals("login")) {
            response = "login"
        }
        model.addAttribute("title", response)

        return response
    }

    //아이디 생성
    @PostMapping("/sign")
    fun postSign(
        model: Model,
        @RequestParam(value = "id") userId: String, //form의 id에 해당하는 값
        @RequestParam(value = "password") password: String
    ): String {
        try {
            val cryptoPass = crypto(password)

            repository.save(User(userId, cryptoPass)) //create
        } catch (e: Exception) {
            e.printStackTrace()
        }
        model.addAttribute("title", "sign success")
        return "login"
    }

    //log - in
    @PostMapping("/login")
    fun postLogin(
        model: Model,
        session: HttpSession,
        @RequestParam(value = "id") userId: String,
        @RequestParam(value = "password") password: String
    ): String {
        var pageName = ""

        try {
            val cryptoPass = crypto(password)
            val db_user = repository.findByUserId(userId)

            if (db_user != null) {
                val db_pass = db_user.password

                if (cryptoPass.equals(db_pass)) {
                    session.setAttribute("userId", db_user.userId)
                    model.addAttribute("title", "welcome : login-success")
                    model.addAttribute("userId", db_user.userId)
                    pageName = "welcome"
                } else {
                    model.addAttribute("title", "login_failed")
                    pageName = "login"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return pageName
    }
}

