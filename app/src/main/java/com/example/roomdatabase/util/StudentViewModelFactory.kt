package com.example.roomdatabase.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.roomdatabase.repository.StudentInformationRepository
import java.lang.IllegalArgumentException

class StudentViewModelFactory(private val repository: StudentInformationRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StudentInformationViewModel::class.java)){
            return StudentInformationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }

}