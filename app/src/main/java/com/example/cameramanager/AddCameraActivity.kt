package com.example.cameramanager

import CameraResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cameramanager.databinding.ActivityAddCameraBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCameraBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")  // Use your backend URL here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Initialize ApiService
        apiService = retrofit.create(ApiService::class.java)

        // Set up Spinner for Resolution
        val resolutionOptions = arrayOf("1080p", "720p", "480p", "360p")
        val resolutionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resolutionOptions)
        resolutionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.resolutionSpinner.adapter = resolutionAdapter

        // Set up Spinner for Frame Rate
        val frameRateOptions = arrayOf("30 fps", "60 fps", "120 fps")
        val frameRateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, frameRateOptions)
        frameRateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.frameRateSpinner.adapter = frameRateAdapter

        // Set listener for Save Button
        binding.saveButton.setOnClickListener {
            // Get the values from the inputs
            val camera = Camera(
                ip = binding.ipEditText.text.toString(),
                username = binding.usernameEditText.text.toString(),
                password = binding.passwordEditText.text.toString(),
                resolution = binding.resolutionSpinner.selectedItem.toString(),
                frameRate = binding.frameRateSpinner.selectedItem.toString(),
                compressionEnabled = binding.compressionCheckBox.isChecked,
                motionDetectionEnabled = binding.motionDetectionSwitch.isChecked
            )

            // Send the data to the backend
            saveCameraData(camera)
        }
        binding.cancelButton.setOnClickListener {
            finish()  // This will close the current activity and return to the previous one
        }
    }

    private fun saveCameraData(camera: Camera) {
        val call = apiService.saveCamera(camera)
        call.enqueue(object : Callback<CameraResponse> {
            override fun onResponse(call: Call<CameraResponse>, response: Response<CameraResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddCameraActivity, "Camera added successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Navigate back
                } else if (response.code() == 400) {
                    Toast.makeText(this@AddCameraActivity, "Camera with this IP or Name already exists!", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("API Error", "Response code: ${response.code()}")
                    Toast.makeText(this@AddCameraActivity, "Failed to add camera!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CameraResponse>, t: Throwable) {
                Toast.makeText(this@AddCameraActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}