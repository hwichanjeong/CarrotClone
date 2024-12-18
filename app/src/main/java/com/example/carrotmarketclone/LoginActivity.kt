package com.example.carrotmarketclone

import DB.DBHelper
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.carrotmarketclone.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    var DB: DBHelper?=null
    lateinit var sharedPreferences : SharedPreferences
    private lateinit var auth: FirebaseAuth
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
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)

        auth = Firebase.auth
        val edit = sharedPreferences.edit()

        val loginBtn = binding.loginBtn
        val editId = binding.editTextId
        val editPwd = binding.editPwd

        loginBtn.setOnClickListener {
            val user = editId.text.toString()
            val pwd = editPwd.text.toString()

            auth.signInWithEmailAndPassword(user, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

        binding.signUp.setOnClickListener{
            val loginIntent = Intent(this, SignUpActivity::class.java)
            startActivity(loginIntent)
        }

        initCheckBox()
    }

    private fun initCheckBox() {
        binding.autoLogin.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "자동 로그인 활성화됨", Toast.LENGTH_SHORT).show()
                // 자동 로그인 로직을 저장 (예: SharedPreferences에 저장)
                saveAutoLoginState(true)
            } else {
                Toast.makeText(this, "자동 로그인 비활성화됨", Toast.LENGTH_SHORT).show()
                // 자동 로그인 비활성화
                saveAutoLoginState(false)
            }
        }
    }

    private fun saveAutoLoginState(isAutoLoginEnabled: Boolean) {
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("auto_login", isAutoLoginEnabled)
            apply()
        }
    }

    override fun onStart() {
        super.onStart()
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isAutoLoginEnabled = sharedPref.getBoolean("auto_login", false)

        if (isAutoLoginEnabled) {
            Toast.makeText(this, "자동 로그인 상태입니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}