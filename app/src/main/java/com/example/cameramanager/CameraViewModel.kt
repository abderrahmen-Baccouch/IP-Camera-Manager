package com.example.cameramanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CameraRepository
    val allCameras: LiveData<List<Camera>>

    init {
        val cameraDao = CameraDatabase.getDatabase(application).cameraDao()
        repository = CameraRepository(cameraDao)
        allCameras = repository.allCameras
    }

    fun insert(camera: Camera) = viewModelScope.launch {
        repository.insert(camera)
    }

    fun delete(camera: Camera) = viewModelScope.launch {
        repository.delete(camera)
    }
}
