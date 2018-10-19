package apextechies.kifayti.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import apextechies.kifayti.R
import apextechies.kifayti.adapter.ViewAllAdapter
import callusplz.apextechies.callusplz.adapter.CategoryAdapter
import kotlinx.android.synthetic.main.activity_viewall.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.common_toolbar.*
import java.util.ArrayList

class ViewAllActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewall)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = intent.getStringExtra("name")

        viewallRV.layoutManager = GridLayoutManager(this, 2)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        getData()
    }

    private fun getData() {

        var list  = ArrayList<String>()

        for (i in 1..10){
            list.add(i.toString())
        }

        viewallRV.adapter = ViewAllAdapter(this, list)
    }
}