package com.example.mytest

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.mytest.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : BaseActivity() {

    val PERM_STORAGE = 9
    val PERM_CAMERA = 10

    val REQ_CAMERA = 11
    val REQ_GALLERY = 12

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1. 공용저장소 권한이 있는지 확인
        requirePermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)
        setContentView(binding.root)
        //2. 앨범 버튼이 클릭되면 수정 가능
        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
        binding.mainActivityLayout.setOnClickListener {
            hideKeyboard()
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
        }
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