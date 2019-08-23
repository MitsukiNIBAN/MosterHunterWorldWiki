package com.mitsuki.mosterhunterworldwiki.mvp.model

import com.mitsuki.mosterhunterworldwiki.mvp.contract.DemoContract
import com.mitsuki.simplemvp.base.BaseModel

class DemoModel : BaseModel(), DemoContract.Model {
    override fun getStr(): String = "test String"
}