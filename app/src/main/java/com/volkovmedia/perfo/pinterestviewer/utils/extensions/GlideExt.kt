package com.volkovmedia.perfo.pinterestviewer.utils.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String, options: (GlideExecutor.() -> Unit)? = null) {
    val requestBuilder = Glide.with(context.applicationContext).load(url)
    options?.invoke(GlideExecutor(requestBuilder))
    requestBuilder.into(this)
}

fun ImageView.load(drawable: Drawable, options: (GlideExecutor.() -> Unit)? = null) {
    val requestBuilder = Glide.with(context.applicationContext).load(drawable)
    options?.invoke(GlideExecutor(requestBuilder))
    requestBuilder.into(this)
}

fun String.loadImage(context: Context, options: (GlideExecutor.() -> Unit)? = null): Drawable {
    val requestManager = Glide.with(context.applicationContext)

    val requestBuilder = when (fileExtension) {
        "gif" -> requestManager.asGif()
        else -> requestManager.asDrawable()
    }

    options?.invoke(GlideExecutor(requestBuilder))

    return requestBuilder.load(this)
            .submit()
            .get()
}

class GlideExecutor(private val requestBuilder: RequestBuilder<out Drawable>) {

    var transitionOptions: TransitionOptions<*, in Drawable> = DrawableTransitionOptions.withCrossFade()
        set(value) {
            field = value
            requestBuilder.transition(field)
        }

    var requestOptions: RequestOptions? = null
        set(value) {
            field = value
            requestBuilder.apply(field ?: return)
        }

    fun attachPlaceHolder() {

    }

}