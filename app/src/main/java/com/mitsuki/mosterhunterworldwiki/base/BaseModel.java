package com.mitsuki.mosterhunterworldwiki.base;

import com.lzy.okgo.OkGo;

public class BaseModel implements IModel{
    @Override
    public void onDestroy() {
        OkGo.getInstance().cancelTag(this);
    }
}
