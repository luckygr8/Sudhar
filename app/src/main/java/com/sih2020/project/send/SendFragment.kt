package com.sih2020.project.send

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.android.volley.VolleyError
import com.bumptech.glide.Glide
import com.sih2020.project.interfaces.HttpRequests
import com.sih2020.project.R
import com.sih2020.project.constants.Constants
import com.sih2020.project.transferObjects.Problem
import com.sih2020.project.utility.Functions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.InputStream


@Suppress("DEPRECATION")
class SendFragment : Fragment() , HttpRequests {
    override fun onSuccessArrayGet(jsonArray: JSONArray, token: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSuccessObjectGet(jsonObject: JSONObject, token: Int) {
        Log.d(Constants.LOG_TAG,jsonObject.toString())
    }

    override fun onError(volleyError: VolleyError) {
       Log.d(Constants.LOG_TAG,volleyError.toString())
    }

    override fun onSuccessPost(jsonObject: JSONObject, token: Int) {
        Log.d(Constants.LOG_TAG,jsonObject.toString())
    }

    /**
     * This code is just for testing
     */
    private lateinit var imageview: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_send, container, false)

        imageview = root.findViewById(R.id.imageview)
        var button: Button = root.findViewById(R.id.button)
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.putExtra("crop", "true")
        intent.putExtra("scale", true)
        intent.putExtra("aspectX", 16)
        intent.putExtra("aspectY", 9)
        startActivityForResult(Intent.createChooser(intent, "pick a photo"), 1000)
        //startActivityForResult(Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE),1000)
        button.setOnClickListener {

        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            loadPhotoInBackground(data)
        }
    }

    private fun loadPhotoInBackground(data:Intent?){
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Default){
                val imageUri = data?.data
                val imageStream: InputStream? = context?.contentResolver?.openInputStream(imageUri!!)
                val bitmap:Bitmap  = BitmapFactory.decodeStream(imageStream)
                val base64 = toBase64String(bitmap)
                /*val decoded:ByteArray = Base64.decode(base64,0)
                bitmap = BitmapFactory.decodeByteArray(decoded,0,decoded.size)*/
                Log.d(Constants.LOG_TAG,base64)
                CoroutineScope(Dispatchers.Main).launch {
                    imageview.setImageBitmap(bitmap)
                }
            }
        }

        /*Thread(Runnable {
            val imageUri = data?.data
            val imageStream: InputStream? = context?.contentResolver?.openInputStream(imageUri!!)
            val bitmap:Bitmap  = BitmapFactory.decodeStream(imageStream)
            CoroutineScope(Dispatchers.IO).launch {
                Log.d(Constants.LOG_TAG,toBase64String(bitmap))
            }
            CoroutineScope(Dispatchers.Main).launch {
                imageview.setImageBitmap(bitmap)
            }
        }).start()*/
    }
    /*public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,      decodedByte.length);
     }*/
    private fun toBase64String(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val byte: ByteArray = baos.toByteArray()
        val encode: String = Base64.encodeToString(byte, Base64.DEFAULT)
        return encode
    }


}


/*public static String encodeTobase64(Bitmap image) {
    Bitmap immagex = image;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
    byte[] b = baos.toByteArray();
    String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
    return imageEncoded;
}*/

/*var imageUri = data?.data
            var bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, imageUri)
            val URL = "http://139.59.84.88:8000/main/problem/"
            //var obj = Functions.parseObjectToJson(Constants.OBJECT_TYPE_PROBLEM , )
            var obj = Problem(
                imageid = toBase64String(bitmap),
                userid = "user1@gmail.com",
                address = "somewhere in this universe",
                description = "there is a big pothole in the center of our galaxy. Please fix it",
                landmark = "cindrellaServer",
                city = "Ambala",
                wardid = 1,
                roadtype = 1
            )

            Functions.postJsonObject(URL,this,Constants.OBJECT_TYPE_PROBLEM,obj,1)*/
//Functions.showToast(obj.toString())


/*val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            Log.d(Constants.LOG_TAG,"${byteArray.size}")var bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)*/