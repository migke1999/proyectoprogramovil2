package com.example.galeria

import android.graphics.Bitmap
import android.graphics.Color


class ConvolutionFilter {
    fun apply_Mask(mask: IntArray, image: Bitmap): Bitmap {
        var c: Int
        val preview: Bitmap
        preview = Bitmap.createBitmap(image.width, image.height, image.config )
        var r = 0
        var g = 0
        var b = 0
        var weight = 0
        var weightSum = 0
        var counter = 0
        for (i in mask.indices) {
            weightSum += mask[i]
        }
        for (i in 1 until image.width - 2) {
            for (j in 1 until image.height - 2) {
                b = 0
                g = b
                r = g
                counter = 0
                for (o in i - 1..i + 1) {
                    for (p in j - 1..j + 1) {
                        c = if (o == -1 && (p == -1 || p == image.height) || o == image.width && (p == -1 || p == image.height)) image.getPixel(i, j) else if (o == -1 || o == image.width) image.getPixel(i, p) else if (p == -1 || p == image.height) image.getPixel(o, j) else image.getPixel(o, p)
                        weight = mask[counter]
                        r += Color.red(c) * weight
                        b += Color.blue(c) * weight
                        g += Color.green(c) * weight
                        counter++
                    }
                }
                if (weightSum == 0) weightSum = 1
                r = r / weightSum
                b = b / weightSum
                g = g / weightSum
                if (r < 0) r = 0
                if (r > 255) r = 255
                if (b < 0) b = 0
                if (b > 255) b = 255
                if (g < 0) g = 0
                if (g > 255) g = 255
                preview.setPixel(i, j, Color.rgb(r, g, b))
            }
        }
        return preview
    }
}