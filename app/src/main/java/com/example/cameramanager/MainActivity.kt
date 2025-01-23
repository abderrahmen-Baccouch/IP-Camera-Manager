package com.example.cameramanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cameramanager.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cameraList = mutableListOf<Camera>()
    private lateinit var cameraAdapter: CameraAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/") // Your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Initialize ApiService
        apiService = retrofit.create(ApiService::class.java)

        cameraAdapter = CameraAdapter(cameraList) { camera ->
            val intent = Intent(this, ViewCameraActivity::class.java).apply {
                putExtra("camera_ip", camera.ip)
            }
            startActivity(intent)
        }

        binding.cameraRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cameraRecyclerView.adapter = cameraAdapter

        toggleEmptyState()

        binding.addCameraFab.setOnClickListener {
            val intent = Intent(this, AddCameraActivity::class.java)
            startActivityForResult(intent, 1)
        }

        // Fetch cameras from the backend
        fetchCameras()
    }

    // Fetch cameras from the backend using Retrofit
    private fun fetchCameras() {
        val call = apiService.getAllCameras()
        call.enqueue(object : Callback<List<Camera>> {
            override fun onResponse(call: Call<List<Camera>>, response: Response<List<Camera>>) {
                if (response.isSuccessful) {
                    val cameras = response.body()
                    if (cameras != null) {
                        cameraList.clear()
                        cameraList.addAll(cameras)
                        cameraAdapter.notifyDataSetChanged()
                        toggleEmptyState()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load cameras", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Camera>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Toggle between showing the list of cameras and the empty state
    private fun toggleEmptyState() {
        if (cameraList.isEmpty()) {
            binding.illustrationImage.visibility = View.VISIBLE
            binding.descriptionText.visibility = View.VISIBLE
            binding.cameraRecyclerView.visibility = View.GONE
        } else {
            binding.illustrationImage.visibility = View.GONE
            binding.descriptionText.visibility = View.GONE
            binding.cameraRecyclerView.visibility = View.VISIBLE
        }
    }
}
