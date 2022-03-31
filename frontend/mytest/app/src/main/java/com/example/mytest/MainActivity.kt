package com.example.mytest

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mytest.databinding.ActivityMainBinding
import com.example.mytest.dto.DiaryCreate
import com.example.mytest.retrofit.RetrofitService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakao.sdk.auth.TokenManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10

    val REQ_CAMERA = 11
    val REQ_GALLERY = 12

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    var filepath:String? = null
    var date:String? = null



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)
        setContentView(binding.root)
        var localDate = Date().time
        var format = SimpleDateFormat("yyyy-MM-dd")
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        date = format.format(localDate)

        println("date: "+date)
        //2. 앨범 버튼이 클릭되면 수정 가능
        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
        binding.create.setOnClickListener {
            var custom_content = binding.diaryText.text.toString()
            testRetrofit(null, custom_content)
        }

        binding.mainActivityLayout.setOnClickListener {
            hideKeyboard()

        }
        binding.daySelect.setOnClickListener() {
            var c = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this@MainActivity, R.style.Theme_Holo_Light_Dialog_MinWidth,
                { view, year, monthOfYear, dayOfMonth ->
                    // TODO Auto-generated method stub
                    try {
                         var year =year.toString()
                         var month =String.format("%02d",monthOfYear + 1)
                         var day = String.format("%02d",dayOfMonth)
                        date = year+"-"+month+"-"+day
                        println("date2: "+date)
                    } catch (e: Exception) {

                        // TODO: handle exception
                        e.printStackTrace()
                    }
                },
                c[Calendar.YEAR],
                c[Calendar.MONTH],
                c[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.getDatePicker().setCalendarViewShown(false);

            datePickerDialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);

            datePickerDialog.show();
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
        openGallery()
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 사용할 수 있습니다", Toast.LENGTH_SHORT).show()
        finish()
    }

    //4. 카메라를 찍은 후에 호출된다 6. 갤러리에서 선택 후 호출된다
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let { uri ->
            binding.imagePreview.setImageURI(uri)
            filepath = absolutelyPath(uri)
            testRetrofit(filepath, null)


        }
    }

//    파일 절대 경로 추출
    fun absolutelyPath(path: Uri): String? {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        return index?.let { c?.getString(it) }
    }

    private fun testRetrofit(path : String?,custom_content:String?){
        //creating a file
        var body : MultipartBody.Part? =null
        if (path != null){
            val file = File(path)
            var fileName = "hello"
            fileName += ".png"
            var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/*"),file)
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
                .baseUrl("http://10.0.2.2:8000/")
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
                if (response?.isSuccessful ) {
                    Log.d("레트로핏 결과2",""+response?.body().toString())
                    if (response?.body()?.content != null && response?.body()?.photo !=null){
//
                        var intent= Intent(this@MainActivity, BottomNav::class.java)
//
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
}