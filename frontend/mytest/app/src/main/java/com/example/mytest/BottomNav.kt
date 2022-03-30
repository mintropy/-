package com.example.mytest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mytest.databinding.ActivityBottomNavBinding

class BottomNav : AppCompatActivity() {
    //여기서 부터
    val PERM_STORAGE = 9
    val PERM_CAMERA = 10

    val REQ_CAMERA = 11
    val REQ_GALLERY = 12

//여기까지 추가


    private lateinit var binding: ActivityBottomNavBinding
    private var photo:String? = "asdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        if(intent.hasExtra("date")){
//            var date = intent.getParcelableExtra<DateDetail>("date")
//            println("test: ${date?.photo}")
//            photo = date?.photo
//            val homeFragment = HomeFragment()
//            val bundle = Bundle()
//            bundle.putString("url",photo)
//            homeFragment.arguments = bundle
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.add(R.id.nav_host_fragment_activity_bottom_nav, homeFragment)
//            transaction.commit()
//            val navView: BottomNavigationView = binding.navView
//
//            val navController = findNavController(R.id.nav_host_fragment_activity_bottom_nav)
//            navView.setupWithNavController(navController)
//
//        }else{
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_nav)
        navView.setupWithNavController(navController)
//            // Passing each menu ID as a set of Ids because each
//            // menu should be considered as top level destinations.
//            val appBarConfiguration = AppBarConfiguration(
//                setOf(
//                    R.id.navigation_home, R.id.navigation_diary, R.id.navigation_garden, R.id.navigation_calendar
//                )
//            )
////        setupActionBarWithNavController(navController, appBarConfiguration)


//        }


        binding.kakaoLogoutButton.setOnClickListener {

//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
//                }else {
//                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
//                }
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
//            }
        }
    }

}