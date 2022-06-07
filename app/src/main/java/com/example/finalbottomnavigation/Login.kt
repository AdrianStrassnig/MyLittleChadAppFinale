package com.example.finalbottomnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket
import java.util.*

class Login : AppCompatActivity() {

    private var active: Boolean = false;
    private var data: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<ProgressBar>(R.id.LoaderSpinner).visibility = View.INVISIBLE;

        findViewById<TextView>(R.id.TextBox).text ="nice"
        val addy = "172.16.37.107"
        val port = 7755
        findViewById<Button>(R.id.daBtn).setOnClickListener {
            active = true;
            val userid = "0";
                val intent = Intent(this, statusactivity::class.java)
            val extras = Bundle()
            extras.putString("key1", userid)
            intent.putExtras(extras)
                startActivity(intent)

            //pw auslesen:
            var InputPassword: String = findViewById<EditText>(R.id.PasswordBox).text.toString();
            var InputUsername: String = findViewById<EditText>(R.id.UsernameBox).text.toString();

            if (";" in InputPassword || ";" in InputUsername){ //checking if delegator is used in password/username and wether length is appropriate
                Toast.makeText(this@Login, "illegal character: ; ", Toast.LENGTH_LONG).show()
            }

            else if (InputPassword.length > 30 || InputUsername.length > 30){
                Toast.makeText(this@Login, "input exceeded 30 characters", Toast.LENGTH_LONG).show()
            }

            else{
                CoroutineScope(IO).launch {
                    findViewById<TextView>(R.id.recievedTextbox).text = client(addy,port, InputPassword, InputUsername);
                }
            }

            findViewById<EditText>(R.id.PasswordBox).setText("");
            findViewById<EditText>(R.id.UsernameBox).setText("");

        }



    }

    private fun client(address: String, port: Int, pass:String = "", user:String = ""):String{
        var recievedThings:String = "recieved:\n";

        if (pass != "" && user != ""){
            try {

                val connection = Socket(address, port);

                val writer:OutputStream = connection.getOutputStream();


                var e: ByteArray  = user.toByteArray()
                var sendung:String = "";
                for (i in e){
                    sendung+=i.toInt().toString();
                    sendung += " - ";
                }

                var ge: ByteArray  = pass.toByteArray()
                var senedeneng:String = "";
                for (d in ge){
                    senedeneng+=d.toInt().toString();
                    senedeneng += " - ";
                }

                findViewById<TextView>(R.id.TextBox).text ="Data Sent: \n$sendung --- ${user.toByteArray().size}\n---\n$senedeneng --- ${pass.toByteArray().size}\n"

                writer.write(user.toByteArray()); //Username versenden
                writer.write(";".toByteArray()); //delegator senden
                writer.write(pass.toByteArray());// password senden



                writer.flush();

                val scanner = Scanner(connection.inputStream)
                while (scanner.hasNextLine()) {
                    recievedThings += scanner.nextLine();
                    break
                }
                findViewById<TextView>(R.id.recievedTextbox).text = recievedThings;

                connection.close();
            }
            catch (e: Exception){

            }
        }
        return recievedThings;

    }
}