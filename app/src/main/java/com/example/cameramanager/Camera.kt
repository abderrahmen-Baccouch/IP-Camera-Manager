package com.example.cameramanager

import java.io.Serializable

data class Camera(
    val ip: String,
    val username: String,
    val password: String,
    val resolution: String,
    val frameRate: String,
    val compressionEnabled: Boolean,
    val motionDetectionEnabled: Boolean
) : Serializable
