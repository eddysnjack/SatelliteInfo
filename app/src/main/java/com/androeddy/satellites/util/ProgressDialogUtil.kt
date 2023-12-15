package com.androeddy.satellites.util

import android.app.ProgressDialog
import android.content.Context


object ProgressDialogUtil {
    private var progressDialog: ProgressDialog? = null
    private var isRunning = false
    fun showProgressDialog(context: Context, message: String?) {
        if (isRunning) return
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage(message)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        isRunning = true
    }

    fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }

        isRunning = false
    }
}