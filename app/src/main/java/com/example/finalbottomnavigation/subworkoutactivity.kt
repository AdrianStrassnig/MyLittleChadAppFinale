package com.example.finalbottomnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket

class subworkoutactivity : AppCompatActivity() {
    private val addy = "172.16.36.159"
    private val port = 7755
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subworkoutactivity)


        val bundle = intent.extras
        var alldata:String? = null


        alldata=bundle!!.getString("key1","Default")
        val delim = "+"
        val alldatalist = alldata?.split(delim)
        val splitter = ";"
        var workoutlist = alldatalist?.elementAt(1)?.split(splitter)
        var datalist = alldatalist?.elementAt(0)?.split(splitter)

        //Checking the alldata
        var check = findViewById<TextView>(R.id.checkdataview)
        check.text = datalist.toString()

        //Setting the first workout
        var firstview = findViewById<TextView>(R.id.textView5)
        firstview.text = workoutlist?.elementAt(0)
        //Setting the second workout
        var secondview = findViewById<TextView>(R.id.textView3)
        secondview.text = workoutlist?.elementAt(1)
        //Setting the third workout
        var thirdview = findViewById<TextView>(R.id.textView4)
        thirdview.text = workoutlist?.elementAt(2)


        var btn_done = findViewById<Button>(R.id.btndone)
        var xpnew= datalist?.elementAt(4)?.toInt()
        btn_done.setOnClickListener{
            when (workoutlist?.elementAt(4)){
                "leg" -> SendToServer("upxp;"+datalist?.elementAt(1).toString()+";3")
                "brust" -> SendToServer("upxp;"+datalist?.elementAt(1).toString()+";5")
                "ruecken" -> SendToServer("upxp;"+datalist?.elementAt(1).toString()+";4")
            }

            if (workoutlist?.elementAt(4).toString() == "leg") {
                SendToServer("upxp;"+datalist?.elementAt(1).toString()+";3")
                if (xpnew != null) { xpnew += 3 }
            } else if (workoutlist?.elementAt(4).toString() == "brust") {
                SendToServer("upxp;"+datalist?.elementAt(1).toString()+";5")
                if (xpnew != null) { xpnew += 3 }
            } else if (workoutlist?.elementAt(4).toString() == "ruecken") {
                SendToServer("upxp;"+datalist?.elementAt(1).toString()+";4")
                if (xpnew != null) { xpnew += 3 }
            }

            val toworkact = Intent(this@subworkoutactivity , statusactivity::class.java)
            val extras = Bundle()
            extras.putString("key1", datalist?.elementAt(0)+";"+datalist?.elementAt(1)+";"+datalist?.elementAt(2)+";"+xpnew+";"+datalist?.elementAt(4)+";"+datalist?.elementAt(5)+";"+datalist?.elementAt(6)+";"+datalist?.elementAt(7)+";"+datalist?.elementAt(8)+";")
            toworkact.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            toworkact.putExtras(extras)
            overridePendingTransition(0, 0);
            startActivity(toworkact)
        }
        var btn_cancel =findViewById<Button>(R.id.btncancel)
        btn_cancel.setOnClickListener{
            val myToast = Toast.makeText(applicationContext,"Workout was cancelled",Toast.LENGTH_SHORT)
            myToast.show()

            val toworkact = Intent(this@subworkoutactivity , statusactivity::class.java)
            val extras = Bundle()
            extras.putString("key1", datalist?.elementAt(0)+";"+datalist?.elementAt(1)+";"+datalist?.elementAt(2)+";"+datalist?.elementAt(3)+";"+datalist?.elementAt(4)+";"+datalist?.elementAt(5)+";"+datalist?.elementAt(6)+";"+datalist?.elementAt(7)+";"+datalist?.elementAt(8)+";")
            toworkact.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            toworkact.putExtras(extras)
            overridePendingTransition(0, 0);
            startActivity(toworkact)
        }

        val textView = findViewById<TextView>(R.id.counterview)
        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(1000000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                textView?.setText("Time remaining: " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                val myToast = Toast.makeText(applicationContext,"Workout was cancelled",Toast.LENGTH_SHORT)
                val toworkact = Intent(this@subworkoutactivity , statusactivity::class.java)
                val extras = Bundle()
                extras.putString("key1", datalist?.elementAt(0)+";"+datalist?.elementAt(1)+";"+datalist?.elementAt(2)+";"+datalist?.elementAt(3)+";"+datalist?.elementAt(4)+";"+datalist?.elementAt(5)+";"+datalist?.elementAt(6)+";"+datalist?.elementAt(7)+";"+datalist?.elementAt(8)+";")
                toworkact.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                toworkact.putExtras(extras)
                overridePendingTransition(0, 0);
                startActivity(toworkact)
            }
        }.start()
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
                catch (a: Exception){
                }
            }
            return true; //RETURNS TRUE IF SUCCESSFULL
        }
        catch(e: Exception) {
            return false
        }
    }
}