package com.example.galeria

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap

class filtros : AppCompatActivity() {
    lateinit var imagen : ImageView
    lateinit var boton: Button
    lateinit var botonCambiar : Button
    lateinit var botonRegresar : Button
    lateinit var imagen2 : ImageView
    lateinit var bitmap : Bitmap
    var filtros : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtros)
        filtros  = intent.getIntExtra("opcion", 4)
        println("llega " + filtros)
        imagen = findViewById(R.id.imagenPrin)
        boton = findViewById(R.id.boton)
        botonCambiar = findViewById(R.id.cambiar)
        botonRegresar = findViewById(R.id.regresar)

        boton.setOnClickListener {
            var intent : Intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/")
            startActivityForResult(intent,10)
        }
        botonRegresar.setOnClickListener {

            imagen.setImageBitmap(bitmap)
        }

        botonCambiar.setOnClickListener {
            var bitmap2 : Bitmap? = bitmap
            when (filtros) {
                0 -> {
                    bitmap2= applyGscale(bitmap)
                }
                1 -> {
                    bitmap2 = applyInvert(bitmap)
                }
                2 -> {
                    bitmap2= adjustedContrast(bitmap, 50.0)

                }
                3 -> {
                    bitmap2= adjustedContrast(bitmap, 5.0)
                }
                4 -> {
                    bitmap2 = doGamma(bitmap,1.8,1.8,1.8)
                }
                5 -> {
                    bitmap2 = color(bitmap)
                }
                6 -> {
                    bitmap2= smoothing(bitmap)
                }
                7 -> {
                    bitmap2 = gaussian(bitmap)
                }
                8 -> {
                    bitmap2 = sharpen(bitmap)
                }
                9 -> {
                    bitmap2 = mean(bitmap)
                }
                10 -> {
                    bitmap2 = embossing(bitmap)
                }
                11 -> {
                    bitmap2 = edge(bitmap)
                }
                12 -> {
                    bitmap2 = Sapia(bitmap,50,2.2, 0.0, 2.2)
                }
                13 -> {
                    bitmap2 = invento2(bitmap)
                }
                14 -> {
                    bitmap2 = invento3(bitmap)
                }
                15 -> {
                    bitmap2 = mirror(bitmap)
                }

                //var bitmap2 : Bitmap? = doGamma(bitmap,1.8,1.8,1.8)
                //var bitmap2 : Bitmap?= adjustedContrast(bitmap, 50.0)
                //var bitmap2 : Bitmap? = applyInvert(bitmap)
            }
            //var bitmap2 : Bitmap? = doGamma(bitmap,1.8,1.8,1.8)
            //var bitmap2 : Bitmap?= adjustedContrast(bitmap, 50.0)
            //var bitmap2 : Bitmap? = applyInvert(bitmap)
            imagen.setImageBitmap(bitmap2)
        }


    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== AppCompatActivity.RESULT_OK) {
            val selectedImage: Uri? = data?.data
             bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
            imagen.setImageBitmap(bitmap)
        }
    }

    fun applyGscale(src: Bitmap): Bitmap? {
        // constant factors
        val GS_RED = 0.299
        val GS_GREEN = 0.587
        val GS_BLUE = 0.114

        // create output bitmap
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height

        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)
                // retrieve color of all channels
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // take conversion up to one single value
                B = (GS_RED * R + GS_GREEN * G + GS_BLUE * B).toInt()
                G = B
                R = G
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    fun applyInvert(src: Bitmap): Bitmap? {
        // constant factors
        val GS_RED = 0.299
        val GS_GREEN = 0.587
        val GS_BLUE = 0.114

        // create output bitmap
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height

        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)
                // retrieve color of all channels
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // take conversion up to one single value

                B =255 - B
                G =255 - G
                R =255 - R
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    fun Sapia(src: Bitmap, depth: Int, red: Double, green: Double, blue: Double): Bitmap? {
        // image size // 50,2.2, 0.0, 2.2
        val width = src.width
        val height = src.height
        // create output bitmap
        val bmOut = Bitmap.createBitmap(width, height, src.config)
        // constant grayscale
        val GS_RED = 0.3
        val GS_GREEN = 0.59
        val GS_BLUE = 0.11
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src.getPixel(x, y)
                // get color on each channel
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // apply grayscale sample
                R = (GS_RED * R + GS_GREEN * G + GS_BLUE * B).toInt()
                G = R
                B = G

                // apply intensity level for sepid-toning on each channel
                R += (depth * red).toInt()
                if (R > 255) {
                    R = 255
                }
                G += (depth * green).toInt()
                if (G > 255) {
                    G = 255
                }
                B += (depth * blue).toInt()
                if (B > 255) {
                    B = 255
                }

                // set new pixel color to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    private fun adjustedContrast(src: Bitmap, value: Double): Bitmap? {
        // image size
        val width = src.width
        val height = src.height
        // create output bitmap

        // create a mutable empty bitmap
        val bmOut = Bitmap.createBitmap(width, height, src.config)

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        val c = Canvas()
        c.setBitmap(bmOut)


        c.drawBitmap(src, 0F, 0F, Paint(Color.BLACK))


        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        // get contrast value
        val contrast = Math.pow((100 + value) / 100, 2.0)

        // scan through all pixels
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src.getPixel(x, y)
                A = Color.alpha(pixel)
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel)
                R = (((R / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (R < 0) {
                    R = 0
                } else if (R > 255) {
                    R = 255
                }
                G = Color.green(pixel)
                G = (((G / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (G < 0) {
                    G = 0
                } else if (G > 255) {
                    G = 255
                }
                B = Color.blue(pixel)
                B = (((B / 255.0 - 0.5) * contrast + 0.5) * 255.0).toInt()
                if (B < 0) {
                    B = 0
                } else if (B > 255) {
                    B = 255
                }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        return bmOut
    }

    fun doGamma(src: Bitmap, red: Double, green: Double, blue: Double): Bitmap? {
        // create output image
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // get image size
        val width = src.width
        val height = src.height
        // color information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int
        // constant value curve
        val MAX_SIZE = 256
        val MAX_VALUE_DBL = 255.0
        val MAX_VALUE_INT = 255
        val REVERSE = 1.0

        // gamma arrays
        val gammaR = IntArray(MAX_SIZE)
        val gammaG = IntArray(MAX_SIZE)
        val gammaB = IntArray(MAX_SIZE)

        // setting values for every gamma channels
        for (i in 0 until MAX_SIZE) {
            gammaR[i] = Math.min(MAX_VALUE_INT,
                    (MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red) + 0.5).toInt())
            gammaG[i] = Math.min(MAX_VALUE_INT,
                    (MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green) + 0.5).toInt())
            gammaB[i] = Math.min(MAX_VALUE_INT,
                    (MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue) + 0.5).toInt())
        }

        // apply gamma table
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get pixel color
                pixel = src.getPixel(x, y)
                A = Color.alpha(pixel)
                // look up gamma
                R = gammaR[Color.red(pixel)]
                G = gammaG[Color.green(pixel)]
                B = gammaB[Color.blue(pixel)]
                // set new color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }

    fun color(src: Bitmap): Bitmap? {
        // create output bitmap
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height

        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)
                // retrieve color of all channels
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // take conversion up to one single value

                B = 0
                G = 0


                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    fun smoothing( bitmap : Bitmap) : Bitmap {

        var filtro : ConvolutionFilter = ConvolutionFilter()
        val laplacian = intArrayOf(
                1, 1, 1,
                1, 1, 1,
                1, 1, 1)
        var bmout = filtro.apply_Mask(laplacian,bitmap)
        return  bmout
    }
    fun gaussian( bitmap : Bitmap) : Bitmap {

        var filtro : ConvolutionFilter = ConvolutionFilter()
        val laplacian = intArrayOf(
                1, 2, 1,
                2, 4, 2,
                1, 2, 1)
        var bmout = filtro.apply_Mask(laplacian,bitmap)
        return  bmout
    }
    fun sharpen( bitmap : Bitmap) : Bitmap {

        var filtro : ConvolutionFilter = ConvolutionFilter()
        val laplacian = intArrayOf(
                0, -2, 0,
                -2,  11, -2,
                0, -2, -0)
        var bmout = filtro.apply_Mask(laplacian,bitmap)
        return  bmout
    }
    private fun mean( bitmap : Bitmap) : Bitmap {

        var filtro : ConvolutionFilter = ConvolutionFilter()
        val laplacian = intArrayOf(
                -1, -1, -1,
                -1,  9, -1,
                -1, -1, -1)
        var bmout = filtro.apply_Mask(laplacian,bitmap)
        return  bmout
    }
    private fun embossing(bitmap : Bitmap) : Bitmap {

        var filtro : ConvolutionFilter = ConvolutionFilter()
        val laplacian = intArrayOf(
                -1, 0, -1,
                0,  4, 0,
                -1, 0, -1)
        var bmout = filtro.apply_Mask(laplacian,bitmap)
        return  bmout
    }

    private fun edge(bitmap : Bitmap) : Bitmap {

        var filtro : ConvolutionFilter = ConvolutionFilter()
        val laplacian = intArrayOf(
                1, 1, 1,
                0,  0, 0,
                -1, -1, -1)
        var bmout = filtro.apply_Mask(laplacian,bitmap)
        return  bmout
    }
    fun invento2(src: Bitmap) : Bitmap {
        // create output bitmap
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height

        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)
                // retrieve color of all channels
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // take conversion up to one single value

                B = 0


                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    fun invento3 (src : Bitmap) :Bitmap {
        // create output bitmap
        var bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height

        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)
                // retrieve color of all channels
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // take conversion up to one single value


                R = 0


                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        bmOut = sharpen(bmOut)
        // return final image
        return bmOut
    }

    fun invento4(src : Bitmap) :Bitmap{
        // create output bitmap
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height

        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)
                // retrieve color of all channels
                A = Color.alpha(pixel)
                R = Color.red(pixel)
                G = Color.green(pixel)
                B = Color.blue(pixel)
                // take conversion up to one single value
                if(x> width/4 && x < width*3/4 && y>height/4 && y < height*3/4){
                    B = 0
                    R = 0
                } else if(x> width/5 && x < width*4/5 && y>height/5 && y < height*4/5){
                    G = 0
                    B = 0

                }else if(x> width/6 && x < width*5/6 && y>height/6 && y < height*5/6){
                    G = 0
                    R = 0
                } else {
                    B = 0
                }



                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }

        // return final image
        return bmOut
    }
    fun mirror (src:Bitmap) : Bitmap {
        // create output bitmap
        val bmOut = Bitmap.createBitmap(src.width, src.height, src.config)
        // pixel information
        var A: Int
        var R: Int
        var G: Int
        var B: Int
        var pixel: Int

        // get image size
        val width = src.width
        val height = src.height
        var posicion =width-1
        // scan through every single pixel
        for (x in 0 until width) {
            for (y in 0 until height) {
                // get one pixel color
                pixel = src.getPixel(x, y)



                // set new pixel color to output bitmap
                bmOut.setPixel(posicion-x, y,pixel)
            }
        }

        // return final image
        return bmOut
    }





}
