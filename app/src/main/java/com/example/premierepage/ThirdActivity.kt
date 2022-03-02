package com.example.premierepage

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_third.*
import java.util.*

class ThirdActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener{



    private lateinit var etHauteur : EditText
    private lateinit var etPoids : EditText
    private lateinit var etage : TextView


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)


    //get data from intent
      val intent = intent
        val username = intent.getStringExtra("username")

   // textView
        val resultview = findViewById<TextView>(R.id.username)
        //setText
           resultview.text = username


        etHauteur = findViewById(R.id.Hauteur)
        etPoids = findViewById(R.id.Poids)
        etage = findViewById(R.id.age)

        valider.setOnClickListener{

            val hauteur = etHauteur.text.toString().trim()
            val poids = etPoids.text.toString().trim()
            val age = etage.text.toString().trim()

            radioGroup.setOnCheckedChangeListener{ group,checkedId ->
                if (checkedId == R.id.radio1)
                    Toast.makeText(this,radio1.text.toString(), Toast.LENGTH_SHORT).show()
                if (checkedId == R.id.radio2)
                    Toast.makeText(this,radio2.text.toString(), Toast.LENGTH_SHORT).show()
                if (radioGroup ==null) {
                    radio1.error = "il faut choisir"
                    radio2.error = "il faut choisir"
                }

            }

           if (age.format(Date()).isEmpty()){
                etage.error="age est obligatoire"; }
            else if(hauteur.isEmpty() )  {
                etHauteur.error = "Hauteur est obligatoire";}
            else if (hauteur.toInt()<100 || hauteur.toInt()>250){
                etHauteur.error ="Hauteur est Invalide"
            }
            else if(poids.isEmpty() ) {
                etPoids.error = "Poids est obligatoire";}
            else if (poids.toInt()>200 ||poids.toInt()<10){
                etPoids.error = "Poids est Invalide"; }


            else {
                Toast.makeText(this, "Informations Collectées !", Toast.LENGTH_SHORT).show()
               val intent = Intent(this,FifthActivity::class.java)
               intent.putExtra("poids",poids)
               startActivity(intent)
            }

        }


                //Calendar
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //button click to show DatePicker
        buttonDatePicker.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                //set to textView
                age.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)},year,month,day)

            //Show dialog
            dpd.show()
        }

    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }



}


