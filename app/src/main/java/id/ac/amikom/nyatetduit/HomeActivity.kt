package id.ac.amikom.nyatetduit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        ivbutton.setOnClickListener {

            //berpindah activity Intent(activity-saat-ini, activity-tujuan)
            val go = Intent(this@HomeActivity, CRUDMainActivity::class.java)

            //menjalankan intent
            startActivity(go)
        }
    }

}