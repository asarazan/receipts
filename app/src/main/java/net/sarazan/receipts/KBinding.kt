package net.sarazan.receipts

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class KBinding<T : ViewDataBinding>(private val activity: Activity, private val layout: Int) : ReadOnlyProperty<Any, KBinding<T>> {

    var data: T by Delegates.notNull()
        private set

    fun onCreate() {
        data = DataBindingUtil.setContentView(activity, layout)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): KBinding<T> {
        return this
    }
}

inline fun <reified T : ViewDataBinding> Activity.bind(layout: Int): KBinding<T> {
    return KBinding(this, layout)
}
