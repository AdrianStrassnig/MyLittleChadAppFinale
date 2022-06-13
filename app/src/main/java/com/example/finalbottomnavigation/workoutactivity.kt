package com.example.finalbottomnavigation

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.Socket

class workoutactivity : AppCompatActivity() {
    private var userid:String? = null
    private var username:String? = null
    private var height:String? = null
    private var weight:String? = null
    private var age:String? = null
    private var petname:String? = null
    private var xp:String? = null
    private var petlv:String? = null
    private val addy = "172.16.36.159"
    private val port = 7755

    private val navigasjonen = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.ic_workouts -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.ic_profile -> {
                val intent = Intent(this, profileactivity::class.java)
                val extras = Bundle()
                var alldata = "alldata;"+userid+";"+petname+";"+xp+";"+petlv+";"+username+";"+height+";"+weight+";"+age
                extras.putString("key1", alldata.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_status -> {
                val intent = Intent(this@workoutactivity, statusactivity::class.java)
                val extras = Bundle()
                var alldata = "alldata;"+userid+";"+petname+";"+xp+";"+petlv+";"+username+";"+height+";"+weight+";"+age
                extras.putString("key1", alldata.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)
        //bottomNavigation.setSelectedItemId(R.id.home)
        bottomNavigation.getMenu().getItem(0).setChecked(true);
        bottomNavigation.setOnNavigationItemSelectedListener(navigasjonen)
        val bundle = intent.extras
        var data:String? = null
        data=bundle!!.getString("key1","Default")

        val delim = ";"
        val list = data?.split(delim)

        petname = list?.elementAt(2)
        xp = list?.elementAt(3)
        petlv =list?.elementAt(4)
        userid =list?.elementAt(1)
        username = list?.elementAt(5)
        height = list?.elementAt(6)
        weight = list?.elementAt(7)
        age = list?.elementAt(8)


        var kej = true;
        var b = secondfragment()
        var a = supportFragmentManager.beginTransaction()


        var btnstrt = findViewById<Button>(R.id.btnstart)
        btnstrt.setOnClickListener{
            val intent = Intent(this@workoutactivity , subworkoutactivity::class.java)
            val extras = Bundle()
            val userid2 = userid
            var dataworkout ="6 Sätze Backsquats;5 Sätze Beinstrecker;5 Sätze Lunges;"+userid+";leg"
            extras.putString("key1", data.toString()+"+"+dataworkout)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtras(extras)
            startActivity(intent)
        }
        var btnstrt2 = findViewById<Button>(R.id.btnstart2)
        btnstrt2.setOnClickListener{
            val intent = Intent(this@workoutactivity , subworkoutactivity::class.java)
            val extras = Bundle()
            val userid2 = userid
            var dataworkout ="8 Sätze Bankdrücken;4 Sätze Dips;10 Flies mit Hanteln;"+userid+";brust"
            extras.putString("key1", data.toString()+"+"+dataworkout)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtras(extras)
            startActivity(intent)
        }
        var btnstrt3 = findViewById<Button>(R.id.btnstart3)
        btnstrt3.setOnClickListener{
            val intent = Intent(this@workoutactivity , subworkoutactivity::class.java)
            val extras = Bundle()
            val userid2 = userid
            var dataworkout ="10 Sätze deadlifts;10 Klimmzüge;Max. Reverse Flies mit Hanteln;"+userid+";ruecken"
            extras.putString("key1", data.toString()+"+"+dataworkout)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            intent.putExtras(extras)
            startActivity(intent)
        }





    }
}