package com.library.library

import com.leewyatt.rxcontrols.controls.RXPasswordField
import com.leewyatt.rxcontrols.controls.RXTextField
import com.leewyatt.rxcontrols.event.RXActionEvent
import dao.UserDao
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import javafx.stage.Stage
import util.AlertUtil
import util.Dbutil
import util.StringUtil
import util.User
import java.sql.Connection

class UserAddInterFrm {
    @FXML
    private lateinit var add: Button

    @FXML
    private lateinit var c_passwordTxt: RXPasswordField

    @FXML
    private lateinit var cancel: Button

    @FXML
    private lateinit var female: RadioButton

    @FXML
    private lateinit var isAdmin: CheckBox

    @FXML
    private lateinit var male: RadioButton

    @FXML
    private lateinit var passwordTxt: RXPasswordField

    @FXML
    private lateinit var sex: ToggleGroup

    @FXML
    private lateinit var userNameTxt: RXTextField
    private lateinit var conn: Connection
    private var isAdministrator = 0
    private val dbutil = Dbutil()
    private val userdao = UserDao()
    private var Sex = ""

    @FXML
    fun add(event: ActionEvent) {
        userAddActionPerformed(event)
    }

    private fun userAddActionPerformed(event: ActionEvent) {
        val username = userNameTxt.text
        if (StringUtil.isEmpty(username)) {
            AlertUtil.showError("错误", "错误", "用户名不能为空")
            return
        }
        val password = passwordTxt.text
        if (StringUtil.isEmpty(password)) {
            AlertUtil.showError("错误", "错误", "密码不能为空")
            return
        }
        val c_password = c_passwordTxt.text
        if (StringUtil.isEmpty(c_password)) {
            AlertUtil.showError("错误", "错误", "确认密码不能为空")
            return
        }
        if (password != c_password) {
            AlertUtil.showError("错误", "错误", "两次密码输入不一致")
            return
        }
        if (male.isSelected) {
            Sex = "男"
        }
        if (female.isSelected) {
            Sex = "女"
        }
        if (isAdmin.isSelected) {
            isAdministrator = 1
        }
        val i = AlertUtil.showConfirm("二次确认", "确认", "确认添加${username}用户吗？")
        if (i == 1) {
            conn = getConnection()
            val user = User(username, password, Sex, isAdministrator)
            val n = userdao.add(conn, user)
            if (n > 0) {
                AlertUtil.showAlert("提示", "提示", "添加成功")
            } else {
                AlertUtil.showError("错误", "错误", "添加失败")
            }
            val a = AlertUtil.showConfirm("继续添加", "继续添加？", "是否继续添加用户？")
            if (a == 0) {
                val nowstage = add.scene.window as Stage
                nowstage.close()
            }
        }
    }

    @FXML
    fun cancel(event: ActionEvent) {
        val nowstage = add.scene.window as Stage
        nowstage.close()
    }

    @FXML
    fun deleteText(event: RXActionEvent) {
        val tf = event.source as RXTextField
        tf.clear()
    }

    fun getConnection(): Connection {
        try {
            conn = dbutil.connection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return conn
    }
}