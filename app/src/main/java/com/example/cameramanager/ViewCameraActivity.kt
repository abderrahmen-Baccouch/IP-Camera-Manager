package com.example.cameramanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.example.cameramanager.databinding.ActivityViewCameraBinding

class ViewCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCameraBinding
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // URL of the HTTP video stream
        val streamUrl = "http://192.168.100.228:8080/video" // Replace with your HTTP stream URL

        Log.d("HTTP", "Loading stream from URL: $streamUrl")

        // Initialize ExoPlayer
        exoPlayer = ExoPlayer.Builder(this).build()
        binding.videoSurface.player = exoPlayer

        // Create a media source from the HTTP URL
        val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-sample")
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(streamUrl))

        // Prepare and play the stream
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release() // Release the ExoPlayer when the activity is destroyed
    }
}
