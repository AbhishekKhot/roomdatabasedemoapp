package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.adapter.RVAdapter
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.example.roomdatabase.db.Student
import com.example.roomdatabase.db.StudentInfoDAO
import com.example.roomdatabase.db.StudentInformationDatabase
import com.example.roomdatabase.repository.StudentInformationRepository
import com.example.roomdatabase.util.StudentInformationViewModel
import com.example.roomdatabase.util.StudentViewModelFactory

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dao: StudentInfoDAO = StudentInformationDatabase.getInstance(application).subscriberDAO
        val repository = StudentInformationRepository(dao)
        val factory = StudentViewModelFactory(repository)
        var  studentInformationViewModel = StudentInformationViewModel(repository)

        studentInformationViewModel = ViewModelProvider(this,factory).get(studentInformationViewModel::class.java)
        binding.myViewModel = studentInformationViewModel
        binding.lifecycleOwner = this
        binding.recyclerview.layoutManager = LinearLayoutManager(this)


        studentInformationViewModel.student.observe(this, Observer {
            Log.i(TAG,it.toString())
            binding.recyclerview.adapter= RVAdapter(it) { seleteditem: Student ->
                listItemClicked(seleteditem)
            }
        })

        studentInformationViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun listItemClicked(subscriber: Student){
        val dao: StudentInfoDAO = StudentInformationDatabase.getInstance(application).subscriberDAO
        val repository = StudentInformationRepository(dao)
        val  studentInformationViewModel = StudentInformationViewModel(repository)
        studentInformationViewModel.initUpdateAndDelete(subscriber)
    }

}
