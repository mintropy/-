package com.example.mytest

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.mytest.databinding.ActivityEditDiaryBinding
import com.example.mytest.dto.SpellCheck
import com.example.mytest.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditDialog(context: Context):Dialog(context) {
    private val dialog = Dialog(context)


    private lateinit var onClickListener: DiaryComplete

    fun showDia(string: String?){
        dialog.setContentView(R.layout.activity_edit_diary)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT)
        val keyboard = dialog.findViewById<TextView>(R.id.back)
        val spell = dialog.findViewById<TextView>(R.id.editSpell)
        val diaryText = dialog.findViewById<EditText>(R.id.editDiaryText)
        val create = dialog.findViewById<TextView>(R.id.editCreate)
        if (string!=null){
            diaryText.setText(string)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog?.setOnKeyListener{dialog,keyCode,event->
            if(keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP){
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        keyboard.setOnClickListener {
            dialog.dismiss()
        }
        spell.setOnClickListener{
            println("spell")
            spellCheck(diaryText.text.toString())
        }
        create.setOnClickListener{
            onClickListener.createClicked(diaryText.text.toString())
            dialog.dismiss()
        }
        dialog.show()
    }
    fun setOnClickListener(listener: EditDialog.DiaryComplete){
        onClickListener = listener
    }
    private fun spellCheck(custom_content:String?){
        val diaryText = dialog.findViewById<EditText>(R.id.editDiaryText)
        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var testToken2 = TokenManager.instance.getToken()
        var head = "Bearer "+testToken2?.accessToken

        var retrofit =
            Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8000/")
                .baseUrl("http://j6d102.p.ssafy.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        //creating our api

        var server = retrofit.create(RetrofitService::class.java)

        // 파일, 사용자 아이디, 파일이름

        server.spellCheck(head,custom_content).enqueue(object: Callback<SpellCheck> {
            override fun onFailure(call: Call<SpellCheck>, t: Throwable) {
                Log.d("test","에러"+t.message.toString())
            }

            override fun onResponse(call: Call<SpellCheck>, response: Response<SpellCheck>) {
                println("response: "+response.toString())
                if (response?.isSuccessful) {
                    Log.d("일기 결과2",""+response?.body().toString())
                    println(response.body()?.customContent?.replace("!@!","\""))
                    diaryText.setText(response.body()?.customContent)
                } else {
                    Log.d("실패","Some error occurred...")
                }
            }
        })
    }


    override fun onBackPressed() {
        if(diaryText.text.toString().trim().isEmpty()){
            super.onBackPressed()
        }
        else{
            val builder = AlertDialog.Builder(dialog.context)
            builder.setTitle("일기를 작성중입니다!")
                .setMessage("작성을 중단하시겠습니까?")
                .setPositiveButton("확인",
                    DialogInterface.OnClickListener { dialog, id ->
                        super.onBackPressed()
                    })
                .setNegativeButton("취소",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
            // 다이얼로그를 띄워주기
            builder.show()
        }
    }
    override fun onTouchEvent(event: MotionEvent):Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }
    interface DiaryComplete{
        fun createClicked(content: String)
    }
}