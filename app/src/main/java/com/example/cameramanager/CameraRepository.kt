package com.example.cameramanager

import androidx.lifecycle.LiveData

class CameraRepository(private val cameraDao: CameraDao) {
    val allCameras: LiveData<List<Camera>> = cameraDao.getAllCameras()

    suspend fun insert(camera: Camera) {
        cameraDao.insert(camera)
    }

    suspend fun delete(camera: Camera) {
        cameraDao.delete(camera)
    }
}
