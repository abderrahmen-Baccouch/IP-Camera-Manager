package com.example.cameramanager

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CameraConfigActivity : AppCompatActivity() {

    private lateinit var resolutionSpinner: Spinner
    private lateinit var frameRateEditText: EditText
    private lateinit var saveConfigButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_config)

        resolutionSpinner = findViewById(R.id.resolutionSpinner)
        frameRateEditText = findViewById(R.id.frameRateEditText)
        saveConfigButton = findViewById(R.id.saveConfigButton)

        // Populate the resolution dropdown
        val resolutionAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.resolution_array,
            android.R.layout.simple_spinner_item
        )
        resolutionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        resolutionSpinner.adapter = resolutionAdapter

        // Save Configuration Button Click Listener
        saveConfigButton.setOnClickListener {
            saveConfiguration()
        }
    }

    private fun saveConfiguration() {
        val selectedResolution = resolutionSpinner.selectedItem.toString()
        val frameRate = frameRateEditText.text.toString()

        if (frameRate.isEmpty()) {
            Toast.makeText(this, "Please enter a frame rate", Toast.LENGTH_SHORT).show()
            return
        }

        val cameraIp = intent.getStringExtra("camera_ip")
        val cameraUsername = intent.getStringExtra("camera_username")
        val cameraPassword = intent.getStringExtra("camera_password")

        // Example of sending the configuration (can be replaced with API calls)
        Toast.makeText(
            this,
            "Saving Configuration for Camera: $cameraIp\nResolution: $selectedResolution\nFrame Rate: $frameRate FPS",
            Toast.LENGTH_LONG
        ).show()

        // Here, send the data to your backend or camera API
    }
}
