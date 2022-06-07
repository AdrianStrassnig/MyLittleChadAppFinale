package com.example.finalbottomnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class profileactivity : AppCompatActivity() {
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
        val profileid = findViewById<TextView>(R.id.BundleTextView)
        profileid.text = s
    }
}