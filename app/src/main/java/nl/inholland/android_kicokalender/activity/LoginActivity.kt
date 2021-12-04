package nl.inholland.android_kicokalender.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import nl.inholland.android_kicokalender.R
import nl.inholland.android_kicokalender.model.AccessToken
import nl.inholland.android_kicokalender.model.LoginUser
import nl.inholland.android_kicokalender.utils.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private var loginButton: Button? = null
    private var usernameTextField: EditText? = null
    private var passwordTextField: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initWidgets()

        setLoginButton()
    }

    private fun setLoginButton() {
        loginButton!!.setOnClickListener {
            if(!usernameTextField!!.text.isNullOrBlank()&&!passwordTextField!!.text.isNullOrBlank()){
                loginUser()
            }
            else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initWidgets() {
        loginButton = findViewById(R.id.button_login_page)
        usernameTextField = findViewById(R.id.editTextUsername)
        passwordTextField = findViewById(R.id.editTextPassword)
    }

    private fun loginUser() {
        val user = LoginUser(
            findViewById<EditText>(R.id.editTextUsername).text.toString(),
            findViewById<EditText>(R.id.editTextPassword).text.toString()
        )

        val retrofit = RetrofitBuilder.buildService()
        retrofit.loginUser(user).enqueue(
            object : Callback<AccessToken> {
                override fun onResponse(call: Call<AccessToken>, response: Response<AccessToken>) {
                    try {
                        saveAuthToken(response.body()!!)
                        Toast.makeText(this@LoginActivity, R.string.user_login, Toast.LENGTH_SHORT).show()
                        //going back to the main activity without finish() to refresh the content (show logout button, show if an article is liked)
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } catch (e: Exception){
                        Toast.makeText(this@LoginActivity, R.string.login_error, Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<AccessToken>, t: Throwable) {
                    Log.d("LoginActivity", "onFailure: "+t.message)
                }
            }
        )
    }

    private fun saveAuthToken(accessToken: AccessToken) {
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.apply{
            putString("AUTH_TOKEN_KEY", accessToken.accessToken)
        }.apply()
    }
}