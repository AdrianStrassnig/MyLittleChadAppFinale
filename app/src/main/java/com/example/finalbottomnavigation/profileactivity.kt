package com.example.finalbottomnavigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket
import java.util.*

class profileactivity : AppCompatActivity() {
    private val addy = "192.168.0.87"
    private val port = 7755
    private var userid:String? = null
    private var username:String? = null
    private var height:String? = null
    private var weight:String? = null
    private var age:String? = null
    private var petname:String? = null
    private var xp:String? = null
    private var petlv:String? = null

    //Changing values

    private val navigasjonen = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.ic_profile -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.ic_workouts -> {
                var usernamesend = findViewById<TextView>(R.id.textviewusernamenochange)
                var heightsend = findViewById<TextView>(R.id.textviewgroesse)
                var weightsend = findViewById<TextView>(R.id.textviewgewicht)
                var agesend = findViewById<TextView>(R.id.textviewalter)
                val alldata = "alldata;"+userid.toString()+";"+petname.toString()+";"+xp.toString()+";"+petlv.toString()+";"+usernamesend.text.toString()+";"+heightsend.text.toString()+";"+weightsend.text.toString()+";"+agesend.text.toString()

                val intent = Intent(this@profileactivity, workoutactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.BundleTextView)
                extras.putString("key1", alldata.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_status -> {
                var usernamesend = findViewById<TextView>(R.id.textviewusernamenochange)
                var heightsend = findViewById<TextView>(R.id.textviewgroesse)
                var weightsend = findViewById<TextView>(R.id.textviewgewicht)
                var agesend = findViewById<TextView>(R.id.textviewalter)
                val alldata = "alldata;"+userid.toString()+";"+petname.toString()+";"+xp.toString()+";"+petlv.toString()+";"+usernamesend.text.toString()+";"+heightsend.text.toString()+";"+weightsend.text.toString()+";"+agesend.text.toString()

                val intent = Intent(this@profileactivity, statusactivity::class.java)
                val extras = Bundle()
                extras.putString("key1", alldata.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private fun SendToServer(message: String = ""):Boolean{ //HELPERMETHOD TO SEND
        try{
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val connection = Socket(addy,port);
                    val writer: OutputStream = connection.getOutputStream();
                    writer.write(message.toByteArray());
                    writer.flush();
                }
                catch (a:Exception){
                }
            }
            return true; //RETURNS TRUE IF SUCCESSFULL
        }
        catch(e: Exception) {
            return false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)

        //bottomNavigation.setSelectedItemId(R.id.home)
        bottomNavigation.getMenu().getItem(2).setChecked(true);
        bottomNavigation.setOnNavigationItemSelectedListener(navigasjonen)

        val bundle = intent.extras
        var s:String? = null


        s=bundle!!.getString("key1","Default")
        var profileid = findViewById<TextView>(R.id.BundleTextView)
        profileid.text = s

        //Getting all values from user...
        val delim = ";"
        val list = s?.split(delim)


        petname = list?.elementAt(2)
        xp = list?.elementAt(3)
        petlv =list?.elementAt(4)

        userid =list?.elementAt(1)
        username = list?.elementAt(5)
        height = list?.elementAt(6)
        weight = list?.elementAt(7)
        age = list?.elementAt(8)

        //Setting all values from user
        profileid.text = userid

        var petnameview = findViewById<TextView>(R.id.textviewusernamenochange)
        var userview = findViewById<TextView>(R.id.textviewname)
        var gewichtview = findViewById<TextView>(R.id.textviewgewicht)
        var alterview = findViewById<TextView>(R.id.textviewalter)
        var groesseview = findViewById<TextView>(R.id.textviewgroesse)
        userview.text = petname.toString()
        petnameview.text = username.toString()

        alterview.text = age.toString()
        groesseview.text = height.toString()


        val t = ","
        val listweights = weight?.split(t)
        var currentweight = listweights?.last()
        gewichtview.text = currentweight.toString()

        //Fragment 1...
        var a = profilefragmentonef()
        var b = supportFragmentManager.beginTransaction()

        var key = true
        var btnandern = findViewById<Button>(R.id.btn_profile_aendern)
        btnandern.setOnClickListener{
            if (key == true){
                a = profilefragmentonef()
                b = supportFragmentManager.beginTransaction()
                b.replace(R.id.profile_act, a)
                b.commit()
                btnandern.text = "Speichern"
                key = false
            }

            else{
                var inputone = findViewById<TextView>(R.id.editviewone)
                var inputtwo = findViewById<TextView>(R.id.editviewtwo)
                var inputthree = findViewById<TextView>(R.id.editviewthree)
                var inputfour = findViewById<TextView>(R.id.editviewfour)
                var finalimput = findViewById<TextView>(R.id.textviewinputtext)


                val stringone: String = inputone.text.toString()
                val stringtwo: String = inputtwo.text.toString()
                val stringthree: String = inputthree.text.toString()
                val stringfour: String = inputfour.text.toString()

                if(stringone.trim().length == 0) { Toast.makeText(applicationContext, "Sie müssen alle Felder ausfüllen! ", Toast.LENGTH_SHORT).show() }
                else if(stringtwo.trim().length == 0) { Toast.makeText(applicationContext, "Sie müssen alle Felder ausfüllen! ", Toast.LENGTH_SHORT).show() }
                else if(stringthree.trim().length == 0) { Toast.makeText(applicationContext, "Sie müssen alle Felder ausfüllen! ", Toast.LENGTH_SHORT).show() }
                else if(stringfour.trim().length == 0) { Toast.makeText(applicationContext, "Sie müssen alle Felder ausfüllen! ", Toast.LENGTH_SHORT).show() }
                else{
                    finalimput.text=inputfour.text.toString()+";"+inputtwo.text.toString()+";"+inputthree.text.toString()+";"+inputone.text.toString()
                    val senddata = "saveuser;"+profileid.text.toString()+";"+finalimput.text.toString()

                    val finaldata = "alldata"+";"+userid.toString()+";"+inputfour.text.toString()+";"+xp.toString()+";"+petlv.toString()+";"+username.toString()+";"+inputthree.text.toString()+";"+weight.toString()+","+inputtwo.text.toString()+";"+inputone.text.toString()
                    //Setting values to left user values:

                    userview.text = inputone.text.toString()
                    gewichtview.text = inputthree.text.toString()
                    alterview.text = inputtwo.text.toString()
                    groesseview.text = inputfour.text.toString()

                    SendToServer(senddata)

                    val intent = Intent(this@profileactivity, profileactivity::class.java)
                    val extras2 = Bundle()
                    extras2.putString("key1", finaldata.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    intent.putExtras(extras2)
                    startActivity(intent)

                    key == true;
                }
            }
        }

        //Button Daten end....





    }
}
