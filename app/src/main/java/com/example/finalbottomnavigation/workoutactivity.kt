package com.example.finalbottomnavigation

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.google.android.material.bottomnavigation.BottomNavigationView

class workoutactivity : AppCompatActivity() {

    private val navigasjonen = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.ic_workouts -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.ic_profile -> {
                val intent = Intent(this, profileactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.TextviewBundleid)
                extras.putString("key1", userid.text.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_status -> {
                val intent = Intent(this@workoutactivity, statusactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.TextviewBundleid)
                extras.putString("key1", userid.text.toString())
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
        var userid:String? = null
        userid=bundle!!.getString("key1","Default")
        val useridtext = findViewById<TextView>(R.id.TextviewBundleid)
        useridtext.text = userid

        var kej = true;
        var b = secondfragment()
        var a = supportFragmentManager.beginTransaction()


        var btnstrt = findViewById<Button>(R.id.btnstart)
        btnstrt.setOnClickListener{
            btnstrt.isClickable = false;
            a.replace(R.id.container, b)
            val mBundle = Bundle()
            mBundle.putString("mText","10 Squats;10 Kilometer laufen;20 mal Seilspringen;"+userid+";leg")
            b.arguments = mBundle
            bottomNavigation.visibility= View.GONE;

            a.commit()
        }
        var btnstrt2 = findViewById<Button>(R.id.btnstart2)
        btnstrt2.setOnClickListener{
            btnstrt2.isClickable = false;
            a.replace(R.id.container, b)
            val mBundle = Bundle()
            mBundle.putString("mText","30 Liegestütze normal;20 Liegstütze incline;10 Flies mi Hanteln;"+userid+";brust")
            b.arguments = mBundle
            a.commit()
        }
        var btnstrt3 = findViewById<Button>(R.id.btnstart3)
        btnstrt3.setOnClickListener{
            btnstrt3.isClickable = false;
            a.replace(R.id.container, b)
            val mBundle = Bundle()
            mBundle.putString("mText","10 mal deadlifts;10 Klimmzüge;Max. Reverse Flies;"+userid+";ruecken")
            b.arguments = mBundle
            a.commit()
        }

    }
}