package com.example.finalbottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class secondfragment : Fragment() {
    private lateinit var textview1: TextView
    private lateinit var textview2: TextView
    private lateinit var textview3: TextView


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

        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val button: Button? = view?.findViewById(R.id.btndone)
        button?.setOnClickListener {
            activity?.onBackPressed();

        }
        val buttoncncl: Button? = view?.findViewById(R.id.btncancel)
        buttoncncl?.setOnClickListener {
            Toast.makeText(activity, "Workout abgebrochen", Toast.LENGTH_SHORT).show()

            getActivity()?.onBackPressed();

        }
    }
    companion object {

        fun newInstance(): com.example.finalbottomnavigation.Fragment {
            return com.example.finalbottomnavigation.Fragment()
        }
    }
}