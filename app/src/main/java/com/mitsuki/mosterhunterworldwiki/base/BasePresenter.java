package com.mitsuki.mosterhunterworldwiki.base;

import android.content.Context;

public abstract class BasePresenter<V extends IView, M extends IModel> {
    protected V mView;
    protected M mModel;

    protected Context mContext;

    public BasePresenter(Context context, V mView) {
        this.mView = mView;
        this.mModel = getModel();
        this.mContext = context;
    }

    public void onDestroy() {
        if (mModel != null)
            mModel.onDestroy();
    }

    protected abstract M getModel();
}
