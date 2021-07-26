package id.ac.amikom.nyatetduit

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    var txEmail: EditText? = null
    var txPassword: EditText? = null
    var BtnLogin: Button? = null
    var dbHelper: DBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        txEmail = findViewById<View>(R.id.txEmail) as EditText
        txPassword = findViewById<View>(R.id.txPassword) as EditText
        BtnLogin = findViewById<View>(R.id.btnLogin) as Button
        dbHelper = DBHelper(this)
        val tvCreateAccount = findViewById<View>(R.id.tvCreateAccount) as TextView
        tvCreateAccount.text = fromHtml(
            "I don't have accound yet. " +
                    "</font><font color='#3b5998'>create one</font>"
        )
        tvCreateAccount.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
        }
        BtnLogin!!.setOnClickListener {
            val LogEmail = txEmail!!.text.toString().trim { it <= ' ' }
            val LogPassword = txPassword!!.text.toString().trim { it <= ' ' }
            val res = dbHelper!!.checkUser(LogEmail, LogPassword)
            if (res == true) {
                Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            } else {
                Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun fromHtml(html: String?): Spanned {
            val result: Spanned
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }
    }
}