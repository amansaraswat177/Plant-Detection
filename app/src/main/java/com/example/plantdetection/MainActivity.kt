package com.example.plantdetection

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import com.example.plantdetection.ml.LiteModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.TransformToGrayscaleOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel


class MainActivity : AppCompatActivity() {

    lateinit var selectBtn: Button
    lateinit var predBtn: Button
    lateinit var resView: TextView
    lateinit var imageView: ImageView
    lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        selectBtn = findViewById(R.id.selectBtn)
        predBtn = findViewById(R.id.predictBtn)
        resView = findViewById(R.id.resView)
        imageView = findViewById(R.id.imageView)

        var labels = application.assets.open("labels.txt").bufferedReader().readLines()

        // image processor
        var imageProcessor = ImageProcessor.Builder()
//            .add(NormalizeOp(0.0f,255.0f))
//            .add(TransformToGrayscaleOp())
            .add(ResizeOp(200,200,ResizeOp.ResizeMethod.BILINEAR))
            .build()


        selectBtn.setOnClickListener {
            var intent = Intent()
            intent.setAction(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(intent, 100)
        }

        predBtn.setOnClickListener {

            var tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(bitmap)


            tensorImage = imageProcessor.process(tensorImage)

            val model = LiteModel.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 200, 200, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(tensorImage.buffer)


            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            var maxIdx = 0

            outputFeature0.forEachIndexed{ index, fl->
                if(outputFeature0[maxIdx] < fl){
                    maxIdx = index
                }
            }

            resView.setText(labels[maxIdx])


            model.close()
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            var uri = data?.data;
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
            imageView.setImageBitmap(bitmap)

        }

    }

}