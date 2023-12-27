package com.example.instagram.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram.MainActivity
import com.example.instagram.SignUpActivity
import com.example.instagram.databinding.ActivityLoginBinding
import com.example.instagram.model.AuthService
import com.example.instagram.model.LoginView
import com.example.instagram.model.Result
import com.example.instagram.model.SignUpResponse
import com.example.instagram.model.User

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignInBtn.setOnClickListener {
            login()
        }

        binding.loginSignUpNewAccountBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    // 로그인 함수
    private fun login() {
        if (binding.loginEditIdEt.text.toString().isEmpty() || binding.loginEditIdEt.text.toString().isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if (binding.loginEditPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val id : String = binding.loginEditIdEt.text.toString()
        val password : String = binding.loginEditPasswordEt.text.toString()

        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User("",id,"", password))   // API 호출

        // null 인지 아닌지 확인
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun saveJwt(jwt: String) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        // 키 값 : "jwt", 인자값 : jwt
        editor.putString("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(code: String, result: Result) {
        when (code) {
            "COMMON200" -> {
                saveJwt(result.jwt)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure(resp: SignUpResponse) {
        Toast.makeText(this, "다시 시도하세요.",Toast.LENGTH_SHORT).show()
    }
}
