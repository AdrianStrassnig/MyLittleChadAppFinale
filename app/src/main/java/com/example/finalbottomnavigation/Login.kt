package com.example.finalbottomnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import org.w3c.dom.Text
import java.io.OutputStream
import java.lang.Exception
import java.net.Socket
import java.util.*
import kotlinx.coroutines.async as async

class Login : AppCompatActivity() {

    private var active: Boolean = false;
    private var data: String = ""
    private var userNameGlobal: String = ""

    private val addy = "192.168.0.87"
    private val port = 7755

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            try {

                active = true;

                var InputPassword: String = findViewById<EditText>(R.id.PasswordBox).text.toString();
                var InputUsername: String = findViewById<EditText>(R.id.UsernameBox).text.toString();

                if (";" in InputPassword || ";" in InputUsername){ //checking if delegator is used in password/username and wether length is appropriate
                    Toast.makeText(this@Login, "illegal character: ; ", Toast.LENGTH_LONG).show()
                }

                else if (InputPassword.length > 30 || InputUsername.length > 30){
                    Toast.makeText(this@Login, "input exceeded 30 characters", Toast.LENGTH_LONG).show()
                }

                else{
                    findViewById<ProgressBar>(R.id.loaderSpinner).visibility = View.VISIBLE;
                    userNameGlobal = InputUsername;
                    var dacontext = this@Login;
                    var usefullstuff = "unset";

                    CoroutineScope(IO).launch {
                        try {

                            usefullstuff = client("login", InputPassword, InputUsername);
                            findViewById<TextView>(R.id.tb_debug).text = usefullstuff;
                            active = true;
                            val intent = Intent(dacontext, statusactivity::class.java)
                            val extras = Bundle()
                            extras.putString("key1", usefullstuff)
                            intent.putExtras(extras)
                            findViewById<ProgressBar>(R.id.loaderSpinner).visibility = View.INVISIBLE;
                            var bruh:String = findViewById<TextView>(R.id.tb_debug).text.split(';')[0]
                            if ( bruh != "alldata" ){
                             throw Exception("Login failed");
                            }
                            startActivity(intent)
                        }
                        catch (e:Exception){

                        }
                    }
                }
            }
            catch (d:Exception){

            }

            findViewById<EditText>(R.id.PasswordBox).setText("");
            findViewById<EditText>(R.id.UsernameBox).setText("");
        }

        findViewById<Button>(R.id.btnRegister).setOnClickListener {
                active = true;

                var InputPassword: String = findViewById<EditText>(R.id.PasswordBox).text.toString();
                var InputUsername: String = findViewById<EditText>(R.id.UsernameBox).text.toString();

                if (";" in InputPassword || ";" in InputUsername){ //checking if delegator is used in password/username and wether length is appropriate
                    Toast.makeText(this@Login, "illegal character: ; ", Toast.LENGTH_LONG).show()
                }

                else if (InputPassword.length > 30 || InputUsername.length > 30){
                    Toast.makeText(this@Login, "input exceeded 30 characters", Toast.LENGTH_LONG).show()
                }

                else{
                    findViewById<ProgressBar>(R.id.loaderSpinner).visibility = View.VISIBLE;
                    userNameGlobal = InputUsername;
                    var dacontext = this@Login;
                    var usefullstuff = "unset";

                    CoroutineScope(IO).launch {
                        try {
                            usefullstuff = client("register", InputPassword, InputUsername);
                            findViewById<TextView>(R.id.tb_debug).text = usefullstuff;
                            usefullstuff = "alldata;${usefullstuff.split(';')[1]};MyLilChad;0;1;${InputUsername};0;0;0"
                            active = true;
                            val intent = Intent(dacontext, statusactivity::class.java)
                            intent.putExtra("key1", usefullstuff)
                            findViewById<ProgressBar>(R.id.loaderSpinner).visibility = View.INVISIBLE;

                            startActivity(intent)
                        }
                        catch (e:Exception){

                        }

                    }
                }

                findViewById<EditText>(R.id.PasswordBox).setText("");
                findViewById<EditText>(R.id.UsernameBox).setText("");
        }


    }

    private suspend fun client(keyword:String ="",pass:String = "", user:String = ""):String = withContext(Dispatchers.IO){
        var recievedThings:String = "";
        if (pass != "" && user != ""){
            try {

                val connection = Socket(addy, port);

                val writer:OutputStream = connection.getOutputStream();

                var susMsg:String = keyword + ";"+user+ ";"+pass

                writer.write(susMsg.toByteArray()); //Username versenden

                writer.flush();

                val scanner = Scanner(connection.inputStream)
                while (scanner.hasNextLine()) {
                    recievedThings += scanner.nextLine();
                    break
                }
                findViewById<TextView>(R.id.tb_debug).text = recievedThings;

                connection.close();
            }
            catch (e: Exception){

            }

        }
        return@withContext recievedThings;


    }


}