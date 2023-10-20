@file:Suppress("DEPRECATION")

package icu.sincos.ZhiWeiXiaoYuan_Extra.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import jp.wasabeef.glide.transformations.BlurTransformation


object BitmapUtils {

    fun Bitmap.zoom(newHeight: Float, newWidth: Float): Bitmap {
        val matrix = Matrix()
        val scaleWidth = newWidth / width
        val scaleHeight = newHeight / height
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(
            this, 0, 0, width,
            height, matrix, true
        )
    }

    fun Bitmap.blur(context: Context, radius: Float, ty: Float): Bitmap {
        val bitmap = Bitmap.createScaledBitmap(
            this,
            (width / ty).toInt(), (height / ty).toInt(), false
        ) //先缩放图片，增加模糊速度
        val rs = RenderScript.create(context)
        val input = Allocation.createFromBitmap(
            rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
            Allocation.USAGE_SCRIPT
        )
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setRadius(25F.coerceAtLeast(radius))
        script.setInput(input)
        script.forEach(output)
        output.copyTo(bitmap)
        rs.destroy()
        return bitmap
    }

    fun Bitmap.brightness(): Float {
        val bmp = zoom(3F, 3F) //转3*3大小的位图
        val pixel = bmp.getPixel(1, 1) //取中间位置的像素
        val r = (pixel shr 16 and 0xff) / 255.0f
        val g = (pixel shr 8 and 0xff) / 255.0f
        val b = (pixel and 0xff) / 255.0f
        return 0.299f * r + 0.587f * g + 0.114f * b //计算灰阶
    }

    fun Bitmap.drawColor(color: Int): Bitmap {
        val newBit = Bitmap.createBitmap(this)
        val canvas = Canvas(newBit)
        canvas.drawColor(color)
        return newBit
    }

    fun Bitmap.handleImageEffect(saturation: Float): Bitmap {
        val saturationMatrix = ColorMatrix()
        saturationMatrix.setSaturation(saturation)
        val imageMatrix = ColorMatrix()
        imageMatrix.postConcat(saturationMatrix)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(imageMatrix)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(this, 0F, 0F, paint)
        return bitmap
    }

    fun Bitmap.mesh(floats: FloatArray): Bitmap {
        val fArr2 = FloatArray(72)
        var i = 0
        while (i <= 5) {
            var i2 = 0
            var i3 = 5
            while (i2 <= i3) {
                val i4 = i * 12 + i2 * 2
                val i5 = i4 + 1
                fArr2[i4] = floats[i4] * width.toFloat()
                fArr2[i5] = floats[i5] * height.toFloat()
                i2++
                i3 = 5
            }
            i++
        }
        val newBit = Bitmap.createBitmap(this)
        val canvas = Canvas(newBit)
        canvas.drawBitmapMesh(newBit, 5, 5, fArr2, 0, null, 0, null)
        return newBit
    }

    fun setBlurBackground(imgBackground: ImageView, bitmap: Bitmap) {
        Glide.with(imgBackground.context)
            .load(bitmap)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(10, 16)))
            .into(object : ViewTarget<ImageView?, Drawable?>(imgBackground) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?,
                ) {
                    val current = resource.current
                    //current.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY)
                    imgBackground.setImageDrawable(current)
                }
            })
    }

}
