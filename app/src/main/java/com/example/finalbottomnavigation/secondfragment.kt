package com.example.finalbottomnavigation

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket

class secondfragment : Fragment() {
    private lateinit var textview1: TextView
    private lateinit var textview2: TextView
    private lateinit var textview3: TextView
    var userid: String? = null;
    var switchworkout: String? = null;
    private val addy = "172.16.36.159"
    private val port = 7755
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_secondfragment, container, false)

        // Finds the TextView in the custom fragment
        textview1 = view.findViewById<View>(R.id.textView5) as TextView
        textview2 = view.findViewById<View>(R.id.textView4) as TextView
        textview3 = view.findViewById<View>(R.id.textView3) as TextView

        // Gets the data from the passed bundle
        val bundle = arguments
        val message = bundle!!.getString("mText")
        val delim = ";"
        val list = message?.split(delim)
        // Sets the derived data (type String) in the TextView
        if (list != null) {
            textview1.text = list.elementAt(0)
            textview2.text = list.elementAt(1)
            textview3.text = list.elementAt(2)
            userid = list.elementAt(3)
            switchworkout = list.elementAt(4)
        }
        return view
    }
    private fun SendToServer(message: String = ""):Boolean{ //HELPERMETHOD TO SEND
        try{

            CoroutineScope(Dispatchers.IO).launch {
                val connection = Socket(addy,port);
                val writer: OutputStream = connection.getOutputStream();

                writer.write(message.toByteArray());
                writer.flush();
            }
            return true; //RETURNS TRUE IF SUCCESSFULL

        }
        catch(e: Exception) {
            return false;
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button: Button? = view?.findViewById(R.id.btndone)
        button?.setOnClickListener {
            when (switchworkout){
                "leg" -> SendToServer("upxp;"+userid.toString()+";20")
                "brust" -> SendToServer("upxp;"+userid.toString()+";40")
                "ruecken" -> SendToServer("upxp;"+userid.toString()+";30")
            }

            activity?.onBackPressed();

        }
        val buttoncncl: Button? = view?.findViewById(R.id.btncancel)
        buttoncncl?.setOnClickListener {
            Toast.makeText(activity, "Workout cancelled", Toast.LENGTH_SHORT).show()

            getActivity()?.onBackPressed();

        }

        val textView: TextView? = view?.findViewById(R.id.counterview)
        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(300000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                textView?.setText("Time remaining: " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                Toast.makeText(activity, "Wokrout cancelled - you took too long ", Toast.LENGTH_SHORT).show()
                getActivity()?.onBackPressed();
            }
        }.start()

    }
    companion object {

        fun newInstance(): com.example.finalbottomnavigation.Fragment {
            return com.example.finalbottomnavigation.Fragment()
        }
    }
}