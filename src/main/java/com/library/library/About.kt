package com.library.library

import javafx.fxml.FXML
import javafx.scene.image.Image
import javafx.scene.image.ImageView

class About {
    @FXML
    private val img: ImageView? = null
    fun showImage() {
        try {
            img!!.image = Image("/images/about.jpg")
            img.isCache = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}