package apextechies.kifayti.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import apextechies.kifayti.R
import apextechies.kifayti.activity.MainActivity
import apextechies.kifayti.common.ClsGeneral
import apextechies.kifayti.common.ConstantValue
import apextechies.kifayti.common.Utilz
import apextechies.kifayti.retrofitnetwork.DownlodableCallback
import apextechies.kifayti.retrofitnetwork.RetrofitDataProvider
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.common_toolbar.*

class SignUpActivity : AppCompatActivity() {

    var retrofitDataProvider: RetrofitDataProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Register"

        retrofitDataProvider = RetrofitDataProvider(this)
        input_phone.setText(ClsGeneral.getPreferences(this, ConstantValue.MOBILE))
        signup.setOnClickListener {
            if (input_name.text.toString().trim().equals("")) Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            else if (input_email.text.toString().trim().equals("")) Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
            else if (input_address.text.toString().trim().equals("")) Toast.makeText(this, "Enter your Address", Toast.LENGTH_SHORT).show()
            else {
                if (Utilz.isInternetConnected(this)) {
                    Utilz.showProgress(this)
                    ClsGeneral.setPreferences(this, ConstantValue.EMAIL, input_email.text.toString())
                    startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                    finishAffinity()
                }else{
                    Utilz.displayMessageAlert(resources.getString(R.string.nointernetconnection), this)
                }
            }




        }

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }
}