package com.example.uasdpm2011500083

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpMatkul: AdapterDosen
    private lateinit var dataMatkul: ArrayList<DataDosen>
    private lateinit var lvMataKuliah: ListView
    private lateinit var linTidakAda: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvMataKuliah = findViewById(R.id.lvMataKuliah)
        linTidakAda = findViewById(R.id.linTidakAda)

        dataMatkul = ArrayList()
        adpMatkul = AdapterDosen(this@MainActivity, dataMatkul)

        lvMataKuliah.adapter = adpMatkul

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, EntriDataDosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = campuss(this@MainActivity)
        val data = db.tampil()
        repeat(dataMatkul.size) { dataMatkul.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val matkul = DataDosen(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpMatkul.add(matkul)
                adpMatkul.notifyDataSetChanged()
            }
            lvMataKuliah.visibility = View.VISIBLE
            linTidakAda.visibility  = View.GONE
        } else {
            lvMataKuliah.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}