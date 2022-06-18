package util

import javafx.scene.control.Alert
import javafx.scene.control.ButtonType

object AlertUtil {
    @JvmStatic
    fun showAlert(title: String?, header: String?, content: String?) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = title
        alert.headerText = header
        alert.contentText = content
        alert.showAndWait()
    }

    @JvmStatic
    fun showWarning(title: String?, header: String?, content: String?) {
        val alert = Alert(Alert.AlertType.WARNING)
        alert.title = title
        alert.headerText = header
        alert.contentText = content
        alert.showAndWait()
    }

    @JvmStatic
    fun showError(title: String?, header: String?, content: String?) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = title
        alert.headerText = header
        alert.contentText = content
        alert.showAndWait()
    }

    @JvmStatic
    fun showConfirm(title: String?, header: String?, content: String?): Int {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.title = title
        alert.headerText = header
        alert.contentText = content
        val result = alert.showAndWait()
        var i = 0
        if (result.orElse(null) == ButtonType.OK) {
            i = 1
        }
        return i
    }
}