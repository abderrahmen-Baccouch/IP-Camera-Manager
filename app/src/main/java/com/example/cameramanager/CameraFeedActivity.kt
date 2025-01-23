package com.example.cameramanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.cameramanager.databinding.ActivityCameraFeedBinding

class CameraFeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraFeedBinding
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lire la vidéo locale depuis le dossier raw
        val mediaItem = MediaItem.fromUri("android.resource://${packageName}/raw/the_platform_2")

        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release() // Libérer les ressources du player
    }
}
