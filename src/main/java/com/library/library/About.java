package com.library.library;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class About {
    @FXML
    private ImageView img;

    public void showImage() {
        try {
            img.setImage(new javafx.scene.image.Image("/images/about.jpg"));
            img.setCache(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

