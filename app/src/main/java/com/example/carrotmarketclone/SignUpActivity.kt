package com.example.carrotmarketclone

import DB.DBHelper
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.carrotmarketclone.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    var DB: DBHelper? = null
    private lateinit var binding: ActivitySignUpBinding
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        DB = DBHelper(this)
        val regiId = binding.registerId
        val regiPwd = binding.registerPwd
        val regiPwdConfirm = binding.registerPwdConfirm
        val regiNick = binding.registerNick
        val regiPhone = binding.registerPhone
        val signUpBtn = binding.signUpBtn
        var warnPhone = binding.warnPhone
        var warnPwd = binding.warnPwd

        signUpBtn.setOnClickListener {
            val user = regiId.text.toString()
            val pass = regiPwd.text.toString()
            val repass = regiPwdConfirm.text.toString()
            val nick = regiNick.text.toString()
            val phone = regiPhone.text.toString()
            val pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,15}$"
            val phonePattern = "^(\\+[0-9]+)?[0-9]{10,15}$"
            // 사용자 입력이 비었을 때
            if (user == "" || pass == "" || repass == "" || nick == "" || phone == "")
                Toast.makeText(this, "회원정보를 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            else {
                //비밀번호 형식이 맞을때
                if (Pattern.matches(pwPattern,pass)){
                    if (pass == repass){
                        if (Pattern.matches(phonePattern,phone)){
                            val insert = DB!!.insertData(user, pass, nick, phone)
                            //가입 성공 if 문
                            if (insert == true) {
                                Toast.makeText(this, "가입 성공!!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            //가입 실패시
                            else {
                                Toast.makeText(this, "가입 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
                        //전화번호 형식 잘못 되었을때
                        else {
                            regiPhone.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.red)
                            warnPhone.setTextColor(R.color.red)
                            warnPhone.text = "전화번호 형식이 올바르지 않습니다."
                            Toast.makeText(this, "전화번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                    // 비밀번호 재확인 안되었을 때
                    else {
                        regiPwdConfirm.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.red)
                        Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                // 비밀번호 형식이 잘못 되었을 경우
                else {
                    regiPwd.backgroundTintList = ContextCompat.getColorStateList(applicationContext, R.color.red)
                    warnPwd.setTextColor(R.color.red)
                    warnPwd.text = "비밀번호 형식이 올바르지 않습니다."
                    Toast.makeText(this, "비밀번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}