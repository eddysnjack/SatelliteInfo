package com.androeddy.satellites.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.androeddy.satellites.util.ProgressDialogUtil
import com.androeddy.satellites.util.StringResources
import com.androeddy.satellites.util.UIActionsMessenger

abstract class BaseViewModel : ViewModel() {
    var arguments: Bundle? = null
    var uiActionsMessenger: UIActionsMessenger? = null
    var stringResources: StringResources? = null
    open fun onViewCreated(savedInstanceState: Bundle?) {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onDestroy() {}
}