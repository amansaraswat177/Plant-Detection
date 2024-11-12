package com.example.plantdetection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.content.Intent
import android.util.Log
import com.example.plantdetection.ml.LiteModel
import org.tensorflow.lite.DataType
//these two unused are needed
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ops.TransformToGrayscaleOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class MainActivity : AppCompatActivity() {

    private lateinit var selectBtn: Button
    private lateinit var predBtn: Button
    private lateinit var resView: TextView
    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

       val backBtn = findViewById<Button>(R.id.btnBack)

        // main activity intent ->> Welcome
        backBtn.setOnClickListener {
            Log.d("Main Activity", "backBtn button clicked")
            val intent = Intent(this, WelcomePage::class.java)
            startActivity(intent)
            finish()
        }

        val solutionButton = findViewById<Button>(R.id.solBtn)
        solutionButton.setOnClickListener {
            // Create an Intent to navigate to SolutionActivity
            val intent = Intent(this, PlantSolution::class.java)
            startActivity(intent)
            finish()
        }


        selectBtn = findViewById(R.id.selectBtn)
        predBtn = findViewById(R.id.predictBtn)
        resView = findViewById(R.id.resView)
        imageView = findViewById(R.id.imageView)

        val labels = application.assets.open("labels.txt").bufferedReader().readLines()

        // image processor
        val imageProcessor = ImageProcessor.Builder()
//            .add(NormalizeOp(0.0f,255.0f))
//            .add(TransformToGrayscaleOp())
            .add(ResizeOp(200,200,ResizeOp.ResizeMethod.BILINEAR))
            .build()


        selectBtn.setOnClickListener {
            val intent = Intent()
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