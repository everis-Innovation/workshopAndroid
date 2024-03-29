package com.everis.workshop.ui.base

import android.content.Context
import android.content.ContextWrapper
import android.util.AttributeSet
import android.widget.FrameLayout

abstract class BaseViewGroup: FrameLayout, BaseContracts.View {

    constructor(context: Context):
            this(context, null)

    constructor(context: Context, attrs: AttributeSet?):
            this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
            super(context, attrs, defStyleAttr)

    open fun onResume() {}

    open fun onPause() {}

    open fun onDestroy() {}

    fun getBaseActivity(): BaseActivity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is BaseActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    override fun getActivityContext(): Context? {
        return getBaseActivity()
    }

    override fun showLoadingDialog() {
        getBaseActivity()?.showLoadingDialog()
    }
}