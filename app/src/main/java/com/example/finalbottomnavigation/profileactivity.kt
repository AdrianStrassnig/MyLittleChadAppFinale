package com.example.finalbottomnavigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket
import java.util.*

class profileactivity : AppCompatActivity() {
    private val addy = "172.16.36.159"
    private val port = 7755
    private val navigasjonen = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.ic_profile -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.ic_workouts -> {
                val intent = Intent(this@profileactivity, workoutactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.BundleTextView)
                extras.putString("key1", userid.text.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_status -> {
                val intent = Intent(this@profileactivity, statusactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.BundleTextView)
                extras.putString("key1", userid.text.toString())
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
        if (list != null) {
            var userid = list.elementAt(1)
            var username = list.elementAt(5)
            var height = list.elementAt(6)
            var weight = list.elementAt(7)
            var age = list.elementAt(8)

            //Setting all values from user
            profileid.text = userid
            var usern = findViewById<TextView>(R.id.textviewusername)
            var userview = findViewById<TextView>(R.id.textviewname)
            var gewichtview = findViewById<TextView>(R.id.textviewgewicht)
            var alterview = findViewById<TextView>(R.id.textviewalter)
            var groesseview = findViewById<TextView>(R.id.textviewgroesse)
            usern.text = username.toString()
            userview.text = username.toString()
            gewichtview.text = weight.toString()
            alterview.text = age.toString()
            groesseview.text = height.toString()

        }
        //Fragment 1...
        var a = profilefragmentonef()
        var b = supportFragmentManager.beginTransaction()

        //Buttons...
        //Button Daten begin....
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

                finalimput.text=inputfour.text.toString()+";"+inputtwo.text.toString()+";"+inputthree.text.toString()+";"+inputone.text.toString()
                val senddata = "saveuser;"+profileid.text.toString()+";"+finalimput.text.toString()
                SendToServer(senddata)
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

                key == true;
            }
        }
        //Button Daten end....
    }

}
