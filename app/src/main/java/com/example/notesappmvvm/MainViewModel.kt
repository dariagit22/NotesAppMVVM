package com.example.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notesappmvvm.model.Note
import com.example.notesappmvvm.utils.TYPE_FIREBASE
import com.example.notesappmvvm.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

class MainViewModel(application: Application) : ViewModel() {
    val readTest: MutableLiveData<List<Note>> by lazy {
       MutableLiveData<List<Note>>()
    }

    private val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }

    init {
        readTest.value = when(dbType.value) {
            TYPE_ROOM -> {
                listOf<Note>(
                    Note(title = "Note 1", subtitle = "Sub for note 1"),
                    Note(title = "Note 2", subtitle = "Sub for note 2"),
                    Note(title = "Note 3", subtitle = "Sub for note 3")
                )
            }
            TYPE_FIREBASE -> {
                listOf()
            }
            else -> listOf()
        }
    }

    fun initDatabase(type: String) {
        dbType.value = type
        Log.d("checkData", "MainViewModel initDatabase with type: $type")
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}