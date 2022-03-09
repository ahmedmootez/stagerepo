package com.example.premierepage

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern









class MainActivity : AppCompatActivity() {
    private var retrofitInterface: RetrofitInterface? = null


    private var ShowPass = false
    private val PASSWORD_PATTERN: Pattern = Pattern.compile(
        "^" +  //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
               // "(?=.*[@#$%^&+=])" +  //at least 1 special character
              //  "(?=\\S+$)" +  //no white spaces
                ".{6,}" +  //at least 6 characters
                "$"
    )



    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnValidate : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = RetrofitClient.getInstance()
        retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        val i = Intent(this,ThirdActivity::class.java)



        showHidePassword.setOnClickListener{
            ShowPass = !ShowPass
            showPassword(ShowPass)
        }
        showPassword(ShowPass)
       etEmail = findViewById(R.id.editTextTextEmailAddress)
        etPassword = findViewById(R.id.editTextPassword)
         btnValidate = findViewById(R.id.buttonLogin)





        buttonLogin.setOnClickListener{
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if(email.isEmpty()){
                etEmail.error="Email Obligatoire";
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Veuiller entrer un email valide")
            }
            else if(password.isEmpty()){
                etPassword.error="Mot de passe Obligatoire"
            }else if (!PASSWORD_PATTERN.matcher(password).matches()){
                etPassword.setError("Mot de passe trop faible .. au moins 6 characteres")
            }
            else{
                Toast.makeText(applicationContext, R.string.toast_msg, Toast.LENGTH_LONG).show()


                val map = HashMap<String?, String?>()
                map["email"] = email
                map["password"] = password


                val call = retrofitInterface!!.executeLogin(map)
                call!!.enqueue(object : Callback<LoginResult?> {
                    override fun onResponse(
                        call: Call<LoginResult?>,
                        response: Response<LoginResult?>
                    ) {
                        if (response.code() == 200) {
                            val result = response.body()

                            i.putExtra("token", result!!.getToken())

                            startActivity(i)
                            Toast.makeText(
                                this@MainActivity, "Wrong Credentials",
                                Toast.LENGTH_LONG
                            ).show()


                        } else if (response.code() == 400) {
                            Toast.makeText(
                                this@MainActivity, "Wrong Credentials",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResult?>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity, t.message,
                            Toast.LENGTH_LONG
                        ).show()
                        Toast.makeText(
                            this@MainActivity, "ahmed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })




            }
        }





        buttonSignUp.setOnClickListener{

            val intent_Signup = Intent(this,SignupActivity::class.java)
            startActivity(intent_Signup)
        }



        }
    //fin OnCreate



    private fun showPassword(isShow:Boolean){
        if(isShow){
            editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            showHidePassword.setImageResource(R.drawable.ic_baseline_visibility_off)
        }else {
            editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            showHidePassword.setImageResource(R.drawable.ic_baseline_visibility)
        }
           editTextPassword.setSelection(editTextPassword.text.toString().length)
    }
}

