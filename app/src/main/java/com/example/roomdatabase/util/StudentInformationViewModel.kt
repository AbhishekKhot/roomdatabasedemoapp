package com.example.roomdatabase.util

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.db.Student
import com.example.roomdatabase.repository.StudentInformationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StudentInformationViewModel(private val repository : StudentInformationRepository) : ViewModel() {

    val student = repository.studentInfo
    private var isUpdateOrDelete = false
    private lateinit var updateOrDelete: Student

    val name1 = MutableLiveData<String>()
    val email1 = MutableLiveData<String>()
    val saveButtonText = MutableLiveData<String>()
    val clearAllButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
    get() = statusMessage

    init {

        saveButtonText.value = "SAVE"
        clearAllButtonText.value = "CLEAR ALL"
    }

    fun saveOrUpdate() {
        if(name1.value == null){
            statusMessage.value = Event("Please enter the subscriber name")
        }
        else if(email1 == null){
            statusMessage.value = Event("Please enter the subscriber email")
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email1.value!!).matches()){
            statusMessage.value = Event("Please enter valid email address")
        }
        else {
            if (isUpdateOrDelete) {
                updateOrDelete.name = name1.value!!
                updateOrDelete.email = email1.value!!
                update(updateOrDelete)
            } else {
                val name = name1.value!!
                val email = email1.value!!
                insert(Student(0, name, email))
                name1.value = ""
                email1.value = ""
            }
        }
    }

    fun clearOrDelete() {
        if(isUpdateOrDelete){
            delete(updateOrDelete)
        }else{
            clearAll()
        }
    }

    private fun insert(student1: Student) : Job = viewModelScope.launch {
       val rowId:Long = repository.insert(student1)
        if(rowId> -1){
            statusMessage.value = Event("subscriber added successfully")
        }
        else{
            statusMessage.value = Event("Error occurred")
        }

    }

    private fun update(student1: Student) : Job = viewModelScope.launch {
        val NoOfRow:Int =repository.update(student1)
        if(NoOfRow>0) {
            name1.value = ""
            email1.value = ""
            isUpdateOrDelete = false
            saveButtonText.value = "SAVE"
            clearAllButtonText.value = "CLEAR ALL"
            statusMessage.value = Event("$NoOfRow rows updated successfully")
        }
        else{

            statusMessage.value = Event("Error occurred")
        }
    }

    private fun delete(student1: Student) : Job = viewModelScope.launch {
        val noOfRowsDeleted:Int = repository.delete(student1)
        if(noOfRowsDeleted>0) {
            name1.value = ""
            email1.value = ""
            isUpdateOrDelete = false
            saveButtonText.value = "SAVE"
            clearAllButtonText.value = "CLEAR ALL"
            statusMessage.value = Event("$noOfRowsDeleted deleted successfully")
        }
        else{
            statusMessage.value = Event("Error occurred")
        }
    }

    private fun clearAll() : Job = viewModelScope.launch {
        val totalRow:Int =  repository.deleteAll()
        if(totalRow>0){

            statusMessage.value = Event("$totalRow row's deleted successfully")
        }
        else {
            statusMessage.value = Event("Error occurred")
        }
    }

    fun initUpdateAndDelete(subscriber: Student) {
        name1.value = subscriber.name
        email1.value = subscriber.email
        isUpdateOrDelete = true
        updateOrDelete = subscriber
        saveButtonText.value = "UPDATE"
        clearAllButtonText.value = "DELETE"

    }


}