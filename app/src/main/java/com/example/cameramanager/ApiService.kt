package com.example.cameramanager;

import CameraResponse
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET
import retrofit2.http.POST;

public interface ApiService {
    // Route POST pour la connexion
    @POST("api/auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("api/cameras")
    fun saveCamera(@Body camera: Camera): Call<CameraResponse>

    @GET("api/cameras")
    fun getAllCameras(): Call<List<Camera>>
}
