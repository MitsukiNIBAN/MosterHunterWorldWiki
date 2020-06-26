package com.mitsuki.mvvm.base

import androidx.lifecycle.ViewModel

open class BaseViewModel<M : BaseModel>(val model: M) : ViewModel()