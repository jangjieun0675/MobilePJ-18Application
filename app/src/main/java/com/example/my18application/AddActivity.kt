package com.example.my18application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.my18application.databinding.ActivityAddBinding
import org.checkerframework.checker.units.qual.s
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            if(binding.addEditView.text.isNotEmpty()){
                saveStore()
            }else{
                Toast.makeText(this,"내용을 입력해주세요..",Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }
    fun dateToString(date:Date):String{
        val format = SimpleDateFormat("yyyy-mm-dd hh:mm")
        return format.format(date)
    }
    fun saveStore(){
        val data = mapOf(
            "email" to MyApplication.email,
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date())
        )

        MyApplication.db.collection("news")
            .add(data)
            .addOnSuccessListener {
                Log.d("mobileApp","data firestore save ok")
            }
            .addOnFailureListener {
                Log.d("mobileApp","data firestore save error")
            }
    }
}