package com.example.premierepage


import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_third.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*




class ThirdActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener{

   var retrofitInterface: RetrofitInterface? = null


    private lateinit var etTaille : EditText
    private lateinit var etPoids : EditText
    private lateinit var etFemme : RadioButton
    private lateinit var etage : TextView


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        val retrofit = RetrofitClient.getInstance()
         retrofitInterface = retrofit.create(RetrofitInterface::class.java)




        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //button click to show DatePicker
        buttonDatePicker.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                //set to textView
                date.setText(""+ mDay +"-"+ mMonth +"-"+ mYear)},year,month,day)

            //Show dialog
            dpd.show()
        }




//ahmed

//ahmed-end


    //get data from intent
      val intent = intent
        val extras = intent.extras
        val username = intent.getStringExtra("username")

   // textView
        val resultview = findViewById<TextView>(R.id.username)
        //setText
           resultview.text = username


        etTaille = findViewById(R.id.Taille)
        etPoids = findViewById(R.id.Poids)
        etage = findViewById(R.id.date)
        etFemme=findViewById(R.id.radFemme)





        valider.setOnClickListener{

            val taille = etTaille.text.toString().trim()
            val poids = etPoids.text.toString().trim()
            val date = etage.text.toString().trim()



            //test
           if (date.format(Date()).isEmpty()){
                etage.error="age est obligatoire"; }
            else if(taille.isEmpty() )  {
                etTaille.error = "Hauteur est obligatoire";}
            else if (taille.toInt()<100 || taille.toInt()>250){
                etTaille.error ="Hauteur est Invalide"
            }
            else if(poids.isEmpty() ) {
                etPoids.error = "Poids est obligatoire";}
            else if (poids.toInt()>200 ||poids.toInt()<10){
                etPoids.error = "Poids est Invalide"; }





            else {

               val map = HashMap<String?, String?>()
               val dates=date.format(Date())


               map["poids"] = poids
               map["taille"] = taille
               if(etFemme.isChecked())
               map["sexe"]="Femme"
               else
               map["sexe"]="Homme"
               map["date"]=dates



               val call = retrofitInterface!!.executeSave(map, extras!!.getString("token"))
               call!!.enqueue(object : Callback<Void?> {
                   override fun onResponse(
                       call: Call<Void?>,
                       response: Response<Void?>
                   ) {
                       if (response.code() == 200) {
                           Toast.makeText(
                               this@ThirdActivity,
                               " success", Toast.LENGTH_LONG
                           ).show()


                       } else if (response.code() == 400) {
                           Toast.makeText(
                               this@ThirdActivity,
                               "Already registered", Toast.LENGTH_LONG
                           ).show()
                       }
                   }

                   override fun onFailure(call: Call<Void?>, t: Throwable) {
                       Toast.makeText(
                           this@ThirdActivity, t.message,
                           Toast.LENGTH_LONG
                       ).show()
                   }
               })
                Toast.makeText(this, "Informations Collectées !", Toast.LENGTH_SHORT).show()
               val intent = Intent(this,FifthActivity::class.java)
               intent.putExtra("poids",poids)
               startActivity(intent)
            }

        }


    }
    //fin OnCreate

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }



}


