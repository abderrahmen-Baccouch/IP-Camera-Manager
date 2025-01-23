package com.example.cameramanager


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CameraDao {
    @Insert
    suspend fun insert(camera: Camera)

    @Delete
    suspend fun delete(camera: Camera)

    @Query("SELECT * FROM cameras")
    fun getAllCameras(): LiveData<List<Camera>>
}
