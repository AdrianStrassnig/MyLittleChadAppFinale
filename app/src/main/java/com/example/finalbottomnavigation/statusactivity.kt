package com.example.finalbottomnavigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.Socket
import java.util.*


class statusactivity : AppCompatActivity() {
    private val navigasjonen = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.ic_status -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.ic_profile -> {
                val intent = Intent(this@statusactivity, profileactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.TextviewId)
                extras.putString("key1", alldata)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_workouts -> {
                val intent = Intent(this@statusactivity, workoutactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.TextviewId)
                extras.putString("key1", alldata)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true

            }

        }
        false

    }
    private val addy = "192.168.0.87"
    private val port = 7755
    private var alldata:String? = " "
    private var dauid:String? = " "
    private var petnamelein:String? = " "
    private var petlevellein:Int? = 1
    private var currentXP:Int? = 0
    private var dickeDATEN:String = ""
    private var username:String = ""
    private var height:Int? = 0
    private var weight:Int? = 0
    private var age:Int? = 0



    private fun SendToServer(message: String = ""):Boolean{ //HELPERMETHOD TO SEND
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val connection = Socket(addy,port);
                val writer: OutputStream = connection.getOutputStream();

                writer.write(message.toByteArray());
                writer.flush();
            }
            return true; //IF SUCCESSFULL

        }
        catch(e: Exception) {
            return false;
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_status)
        setContentView(R.layout.activity_status)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)
        bottomNavigation.getMenu().getItem(1).setChecked(true);
        bottomNavigation.setOnNavigationItemSelectedListener(navigasjonen)

        alldata = intent.getStringExtra("key1");
        findViewById<TextView>(R.id.tb_middebug).text = alldata;

        dickeDATEN = intent.getStringExtra("key1").toString()
        petnamelein = dickeDATEN.split(';')[2]
        petlevellein = dickeDATEN.split(';')[4].toIntOrNull()
        dauid = dickeDATEN.split(';')[1]
        currentXP = dickeDATEN.split(';')[3].toIntOrNull()
        username = dickeDATEN.split(';')[5]
        height = dickeDATEN.split(';')[6].toIntOrNull()
        weight = dickeDATEN.split(';')[7].toIntOrNull()
        age = dickeDATEN.split(';')[8].toIntOrNull()


        findViewById<TextView>(R.id.tbCurrentLevel).text ="lvl${petlevellein}"
        findViewById<TextView>(R.id.tbCurrentXP).text ="${currentXP}"

        if(petlevellein!! <4){
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.dog_lvl1); //clean B)
        }

        else if(petlevellein!! <7){
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.dog_lvl2); //clean B)
        }

        else if(petlevellein!! <10){
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.pet_lvl3); //clean B)
        }

        else{
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.pet_lvl4); //clean B)
        }

        findViewById<TextView>(R.id.editTextPETNAME).text = petnamelein;
        findViewById<ProgressBar>(R.id.progressBarPet).setProgress(petlevellein!!)

        findViewById<ImageButton>(R.id.imageButton_LevelUpPet).setOnClickListener{
            CoroutineScope(IO).launch {
                try {
                    if(currentXP!!>=10){
                        SendToServer("lvl;${dauid};1")
                        petlevellein = petlevellein!! +1
                        alldata = "alldata;${dauid};${petnamelein};${currentXP};${petlevellein};${username};${height};${weight};${age}"
                        currentXP = currentXP!!-10
                        findViewById<TextView>(R.id.tbCurrentXP).text ="${currentXP}"
                        findViewById<TextView>(R.id.tbCurrentLevel).text ="lvl${petlevellein}"


                        updateimage()
                    }

                }
                catch (e:Exception){

                }
            }
        }

        findViewById<ImageButton>(R.id.imageButton_ChangePetName).setOnClickListener{
            findViewById<EditText>(R.id.editTextPETNAME).isFocusable = true

        }

        findViewById<EditText>(R.id.editTextPETNAME).setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    if (findViewById<EditText>(R.id.editTextPETNAME).text.toString() == petnamelein){
                        findViewById<EditText>(R.id.editTextPETNAME).isFocusable = false
                    }

                    else if (findViewById<EditText>(R.id.editTextPETNAME).text.toString() != petnamelein){
                        CoroutineScope(IO).launch {
                            try {
                                SendToServer("setpetname;${dauid};${findViewById<EditText>(R.id.editTextPETNAME).text}");
                                petnamelein =findViewById<EditText>(R.id.editTextPETNAME).text.toString()
                                alldata = "alldata;${dauid};${petnamelein};${currentXP};${petlevellein};${username};${height};${weight};${age}"


                            }
                            catch (e:Exception){

                            }
                        }
                    }
                }
        })




    }


    private fun updateimage(){
        if(petlevellein!! <4){
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.dog_lvl1); //clean B)
        }

        else if(petlevellein!! <7){
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.dog_lvl2); //clean B)
        }

        else if(petlevellein!! <10){
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.pet_lvl3); //clean B)
        }

        else{
            findViewById<ImageView>(R.id.Imagebox_PetBild).setBackgroundResource(R.drawable.pet_lvl4); //clean B)
        }
    }

}