package com.example.mytest

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.mytest.databinding.ActivityEditDiaryBinding
import com.example.mytest.databinding.ActivityMainBinding
import com.example.mytest.dto.DiaryCreate
import com.example.mytest.dto.SpellCheck
import com.example.mytest.retrofit.RetrofitService
import com.example.mytest.ui.dogam.FlowerDetail
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dagam_item.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10

    val REQ_CAMERA = 11
    val REQ_GALLERY = 12

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var filepath:File? = null
    var date:String? = null
    var bit:Bitmap? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)
        setContentView(binding.root)
        var localDate = Date().time
        var format = SimpleDateFormat("yyyy-MM-dd")
        var format2 = SimpleDateFormat("yyyy년 MM월 dd일")
        binding.date.text = format2.format(localDate)
//        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        date = format.format(localDate)

        println("date: "+date)
        //2. 앨범 버튼이 클릭되면 수정 가능
        binding.photos.setOnClickListener {
            openGallery()
        }
        binding.create.setOnClickListener {
            var custom_content = binding.diaryText.text.toString()
            testRetrofit(filepath, custom_content)
            finish()
        }
        binding.mainActivityLayout.setOnClickListener {
            hideKeyboard()
        }
        binding.date.setOnClickListener() {
            dialog()
        }
        binding.diaryText.movementMethod = ScrollingMovementMethod()
        binding.diaryText.setOnClickListener{
//            val intent = Intent(this, EditDiary::class.java)
//            intent.putExtra("text",binding.diaryText.text.toString())
//            ContextCompat.startActivity(binding.diaryText.context,intent,null)
            val target = EditDialog(this)
            target.showDia(binding.diaryText.text.toString())
            target.setOnClickListener(object: EditDialog.DiaryComplete{
                override fun createClicked(content: String) {
                    binding.diaryText.text = content
                }

            })
        }
    }

//    fun setViews() {
//        // 2. 카메라 요청시 권한을 먼저 체크하고 승인되었으면 카메라를 연다.
//        binding.buttonCamera.setOnClickListener {
//            requirePermissions(arrayOf(android.Manifest.permission.CAMERA), PERM_CAMERA)
//        }
//        // 5. 갤러리 버튼이 클릭 되면 갤러리를 연다.
//        binding.buttonGallery.setOnClickListener {
//            openGallery()
//        }
//    }


    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_GALLERY)
    }

    override fun permissionGranted(requestCode: Int) {
        dialog()
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 사용할 수 있습니다", Toast.LENGTH_SHORT).show()
        finish()
    }

    //4. 카메라를 찍은 후에 호출된다 6. 갤러리에서 선택 후 호출된다
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let { uri ->
            binding.photos.setImageURI(uri)
            var input = contentResolver.openInputStream(uri)
            bit = BitmapFactory.decodeStream(input)
            filepath = convertBitmapToFile(bit)
            testRetrofit(filepath, null)
        }
    }

    fun convertBitmapToFile(bitmap: Bitmap?): File? {
        val newFile = File(applicationContext.filesDir, "picture")
        val out = FileOutputStream(newFile)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 40, out)
        return newFile
    }

    private fun testRetrofit(path : File?,custom_content:String?){
        //creating a file
        binding.imageCaption.text = "사진을 분석중이예요"
        var body : MultipartBody.Part? =null
//        println("path:" +path.toString())
        if (path != null){
            var fileName:String? = "hello.png"
            var name = (1..999999).random().toString()
            fileName = "a"+name + ".png"

            var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),path)
            body  = MultipartBody.Part.createFormData("photo",fileName,requestBody)

        }

        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var testToken2 = TokenManager.instance.getToken()
        var head = "Bearer "+testToken2?.accessToken

//        var content = binding.diaryText.text.toString()
        //creating retrofit object
        var retrofit =
            Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:8000/")
                .baseUrl("http://j6d102.p.ssafy.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        //creating our api

        var server = retrofit.create(RetrofitService::class.java)

        // 파일, 사용자 아이디, 파일이름

        server.createDiary(head,date,custom_content,body).enqueue(object:Callback<DiaryCreate>{
            override fun onFailure(call: Call<DiaryCreate>, t: Throwable) {
                Log.d("test","에러"+t.message.toString())
            }
            override fun onResponse(call: Call<DiaryCreate>, response: Response<DiaryCreate>) {
                println("response: "+response.toString())
                if (response?.isSuccessful) {
                    Log.d("일기 결과2",""+response?.body().toString())
                    binding.imageCaption.text = response.body()?.ko_content
                    if (response?.body()?.custom_content != null && response?.body()?.photo !=null){
                        var intent= Intent(this@MainActivity, BottomNav::class.java)
                        startActivity(intent)
                        finish()
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }
        })
    }
    override fun onBackPressed() {
        if(diaryText.text.toString().trim().isEmpty()){
            super.onBackPressed()
        }
        else{
            val builder = AlertDialog.Builder(this)
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
    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(diaryText.windowToken, 0)
    }
    fun dialog(){
        var c = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this@MainActivity, R.style.Theme_Holo_Light_Dialog_MinWidth,
            { view, year, monthOfYear, dayOfMonth ->
                // TODO Auto-generated method stub
                try {
                    var year =year.toString()
                    var month =String.format("%02d",monthOfYear + 1)
                    var day = String.format("%02d",dayOfMonth)
                    binding.date.text = year+"년"+month+"월"+day+"일"
                    date = year+"-"+month+"-"+day
                    println("date2: "+date)
                    openGallery()
                } catch (e: Exception) {

                    // TODO: handle exception
                    e.printStackTrace()
                    openGallery()
                }
            },
            c[Calendar.YEAR],
            c[Calendar.MONTH],
            c[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.getDatePicker().setCalendarViewShown(false)
        datePickerDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        datePickerDialog.show()
    }
}