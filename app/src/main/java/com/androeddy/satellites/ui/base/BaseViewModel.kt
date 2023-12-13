package com.androeddy.satellites.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    open fun onViewCreated(savedInstanceState: Bundle?) {}
    open fun onResume() {}
    open fun onPause() {}
    open fun onDestroy() {}
}