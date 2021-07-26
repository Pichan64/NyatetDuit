package id.ac.amikom.nyatetduit

import android.content.ContentValues
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

class RegisterActivity : AppCompatActivity() {
    var txName: EditText? = null
    var txEmail: EditText? = null
    var txPassword: EditText? = null
    var txConPassword: EditText? = null
    var BtnRegister: Button? = null
    var dbHelper: DBHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        dbHelper = DBHelper(this)
        txName = findViewById<View>(R.id.txNameReg) as EditText
        txEmail = findViewById<View>(R.id.txEmailReg) as EditText
        txPassword = findViewById<View>(R.id.txPasswordReg) as EditText
        txConPassword = findViewById<View>(R.id.txConPassword) as EditText
        BtnRegister = findViewById<View>(R.id.btnRegister) as Button
        val tvRegister = findViewById<View>(R.id.tvRegister) as TextView
        tvRegister.text = fromHtml("Back to " + "</font><font color='#3b5998'>Login</font>")
        tvRegister.setOnClickListener {
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }
        BtnRegister!!.setOnClickListener {
            val LogEmail = txEmail!!.text.toString().trim { it <= ' ' }
            val LogName = txName!!.text.toString().trim { it <= ' ' }
            val LogPassword = txPassword!!.text.toString().trim { it <= ' ' }
            val LogConPassword = txConPassword!!.text.toString().trim { it <= ' ' }
            val values = ContentValues()
            if (LogPassword != LogConPassword) {
                Toast.makeText(this@RegisterActivity, "Password not match", Toast.LENGTH_SHORT)
            } else if (LogPassword == "" || LogEmail == "") {
                Toast.makeText(
                    this@RegisterActivity,
                    "Email or Password cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                values.put(DBHelper.row_email, LogEmail)
                values.put(DBHelper.row_password, LogPassword)
                dbHelper!!.insertData(values)
                Toast.makeText(this@RegisterActivity, "Register Successful", Toast.LENGTH_SHORT)
                    .show()
                finish()
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