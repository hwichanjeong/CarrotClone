package com.example.carrotmarketclone

import DB.DBHelper
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.carrotmarketclone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    var DB: DBHelper? = null
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        val regiId = binding.registerId
        val regiPwd = binding.registerPwd
        val regiPwdConfirm = binding.registerPwdConfirm
        val regiNick = binding.registerNick
        val regiPhone = binding.registerPhone
        val signUpBtn = binding.signUpBtn
        var warnPhone = binding.warnPhone
        var warnPwd = binding.warnPwd

        auth = Firebase.auth

        signUpBtn.setOnClickListener {
            if (regiPwd.text.toString() == regiPwdConfirm.text.toString()) {
                auth.createUserWithEmailAndPassword(regiId.text.toString(), regiPwdConfirm.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            Toast.makeText(applicationContext, "회원가입 성공!", Toast.LENGTH_SHORT)
                                .show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(baseContext, "비밀번호를 다시 확인해주세요.", Toast.LENGTH_SHORT).show()

            }


        }

    }
}