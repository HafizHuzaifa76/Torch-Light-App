package com.example.torchlight

import android.annotation.SuppressLint
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var OnOffButton: Button
    lateinit var torchLightImageView: ImageView
    lateinit var cameraManager:CameraManager
    var isFlashlightOn = true
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OnOffButton = findViewById(R.id.OnOffButton)
        torchLightImageView = findViewById(R.id.torchLightImageView)
        OnOffButton.text = "ON"

        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager

        OnOffButton.setOnClickListener {
            try {
                val cameraIds = cameraManager.cameraIdList

                // Find a camera with a flash unit
                var cameraIdWithFlash: String? = null
                for (cameraId in cameraIds) {
                    val characteristics = cameraManager.getCameraCharacteristics(cameraId!!)
                    val hasFlash = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
                    if (hasFlash != null && hasFlash) {
                        cameraIdWithFlash = cameraId
                        break
                    }
                }
                if (cameraIdWithFlash != null) {
                    cameraManager.setTorchMode(cameraIdWithFlash, !isFlashlightOn)
                    isFlashlightOn = !isFlashlightOn
                    if (isFlashlightOn) {
                        OnOffButton.text = "OFF"
                        torchLightImageView.setImageDrawable(getDrawable(R.drawable.img_2))
                    } else {
                        OnOffButton.text = "ON"
                        torchLightImageView.setImageDrawable(getDrawable(R.drawable.img_3))
                    }
                } else {
                    // Handle the case when no camera with flash unit is found
                    // Display a message or take appropriate action
                }
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()
            }
        }
    }
}

