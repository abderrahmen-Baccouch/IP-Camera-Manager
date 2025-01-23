package com.example.cameramanager


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cameramanager.databinding.ItemCameraBinding

class CameraAdapter(
    private val cameras: List<Camera>,
    private val onItemClick: (Camera) -> Unit
) : RecyclerView.Adapter<CameraAdapter.CameraViewHolder>() {

    private val images = listOf(R.drawable.camip, R.drawable.camip2, R.drawable.camip3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCameraBinding.inflate(inflater, parent, false)
        return CameraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CameraViewHolder, position: Int) {
        val camera = cameras[position]
        holder.bind(camera)

        val image = images[position % images.size]
        holder.binding.shopIcon.setImageResource(image)

        holder.itemView.setOnClickListener {
            // Ouvrir ViewCameraActivity avec l'adresse IP de la caméra
            val intent = Intent(holder.itemView.context, ViewCameraActivity::class.java).apply {
                putExtra("camera_ip", camera.ip) // Passer l'IP de la caméra
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = cameras.size

    class CameraViewHolder(val binding: ItemCameraBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(camera: Camera) {
            binding.cameraIp.text = "@${camera.ip}"
            binding.cameraUsername.text = "Usr : ${camera.username}"
        }
    }
}
