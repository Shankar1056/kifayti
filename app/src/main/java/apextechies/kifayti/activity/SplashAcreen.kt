package apextechies.kifayti.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import apextechies.kifayti.R
import apextechies.kifayti.common.ClsGeneral
import apextechies.kifayti.common.ConstantValue
import apextechies.kifayti.model.StopModel
import apextechies.kifayti.retrofitnetwork.DownlodableCallback
import apextechies.kifayti.retrofitnetwork.RetrofitDataProvider

class SplashAcreen: AppCompatActivity() {
var retrofitDataProvider: RetrofitDataProvider?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ctivity_splash)

        retrofitDataProvider = RetrofitDataProvider((this))
        val timer = object : Thread() {
            override fun run() {
                try {
                    //Display for 3 seconds
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    // TODO: handle exception
                    e.printStackTrace()
                } finally {
                    //Goes to Activity  StartingPoint.java(STARTINGPOINT)
                    callApi()

                }
            }
        }
        timer.start()

    }

    private fun callApi() {
        retrofitDataProvider!!.stopApp(object : DownlodableCallback<StopModel> {
            override fun onSuccess(result: StopModel) {
                if (result.data[0].status == 0) {
                    Toast.makeText(this@SplashAcreen, "Call me 8002877277", Toast.LENGTH_LONG).show()
                } else {
                    if (ClsGeneral.getPreferences(this@SplashAcreen, ConstantValue.USERID).equals("")){
                        startActivity(Intent(this@SplashAcreen, LoginActivity::class.java))
                        finishAffinity()
                    }else  if (ClsGeneral.getPreferences(this@SplashAcreen, ConstantValue.EMAIL).equals("")){
                        startActivity(Intent(this@SplashAcreen, SignUpActivity::class.java))
                        finishAffinity()
                    }
                    else{
                        startActivity(Intent(this@SplashAcreen, MainActivity::class.java))
                        finishAffinity()
                    }

                }
            }

            override fun onFailure(error: String) {

            }

            override fun onUnauthorized(errorNumber: Int) {

            }
        })
    }
}