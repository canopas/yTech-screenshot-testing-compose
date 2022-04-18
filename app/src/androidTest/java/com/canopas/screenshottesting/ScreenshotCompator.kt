package com.canopas.screenshottesting

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.test.platform.app.InstrumentationRegistry
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

fun assertScreenshotMatchesGolden(
    folderName: String,
    goldenName: String,
    node: SemanticsNodeInteraction
) {
    val bitmap = node.captureToImage().asAndroidBitmap()

    // Save screenshot to file for debugging / first time transfer to assets
    val newFilename = goldenName
    saveScreenshot(
        folderName,
        newFilename,
        bitmap
    )
    val context = InstrumentationRegistry.getInstrumentation().context
    val appPackageName = BuildConfig.APPLICATION_ID
    val golden = try {
        val goldenFilename = "$folderName/${goldenName}.png"
        context.resources.assets.open(goldenFilename)
            .use { BitmapFactory.decodeStream(it) }
    } catch (e: FileNotFoundException) {
        throw FileNotFoundException(
            e.message + " was not found in assets\n" +
                    "First time running this screenshot test? \n" +
                    "Go to Device File Explorer -> data/data/$appPackageName/files/$folderName \n" +
                    "and copy over the screenshot: " +
                    "$newFilename to the assets/$folderName folder in androidTest \n" +
                    "with updated name ${e.message?.removePrefix("$folderName/")}"
        )
    }
    golden.compare(bitmap)
}

private fun saveScreenshot(folderName: String, filename: String, bmp: Bitmap) {
    val parent = InstrumentationRegistry.getInstrumentation().targetContext.filesDir
    val path = File(parent, folderName)
    if (!path.exists()) {
        path.mkdirs()
    }
    FileOutputStream("$path/$filename.png").use { out ->
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    println("Saved screenshot to $path/$filename.png")
}

private fun Bitmap.compare(other: Bitmap) {
    if (this.width != other.width || this.height != other.height) {
        throw AssertionError("Size of screenshot does not match golden file (check device density)")
    }
    // Compare row by row to save memory on device
    val row1 = IntArray(width)
    val row2 = IntArray(width)
    for (column in 0 until height) {
        // Read one row per bitmap and compare
        this.getRow(row1, column)
        other.getRow(row2, column)
        if (!row1.contentEquals(row2)) {
            throw AssertionError("Sizes match but bitmap content has differences")
        }
    }
}

private fun Bitmap.getRow(pixels: IntArray, column: Int) {
    this.getPixels(pixels, 0, width, 0, column, width, 1)
}
