package com.example.finalbottomnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

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
                extras.putString("key1", userid.text.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.ic_workouts -> {
                val intent = Intent(this@statusactivity, workoutactivity::class.java)
                val extras = Bundle()
                val userid = findViewById<TextView>(R.id.TextviewId)
                extras.putString("key1", userid.text.toString())
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                intent.putExtras(extras)
                startActivity(intent)
            }
        }
        false

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_status)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.navigation)
        //bottomNavigation.setSelectedItemId(R.id.home)
        bottomNavigation.getMenu().getItem(1).setChecked(true);
        bottomNavigation.setOnNavigationItemSelectedListener(navigasjonen)
        val bundle = intent.extras
        var userid:String? = null
        userid=bundle!!.getString("key1","Default")
        val useridtext = findViewById<TextView>(R.id.TextviewId)
        useridtext.text = userid

    }
}