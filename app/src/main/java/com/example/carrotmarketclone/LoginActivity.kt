package com.example.carrotmarketclone

import DB.DBHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.carrotmarketclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    var DB: DBHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        DB = DBHelper(this)

        val loginBtn = binding.loginBtn
        val editId = binding.editTextId
        val editPwd = binding.editPwd

        loginBtn.setOnClickListener {
            val user = editId.text.toString()
            val pwd = editPwd.text.toString()

            if (user == "" || pwd == ""){
                Toast.makeText(this, "아이디와 비번을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val checkUserpass = DB!!.checkUserpass(user,pwd)
                if (checkUserpass == true){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signUp.setOnClickListener{
            val loginIntent = Intent(this, SignUpActivity::class.java)
            startActivity(loginIntent)
        }
    }
}