package com.example.livemulti

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.pedro.common.ConnectChecker
import com.pedro.encoder.input.gl.render.filters.`object`.ImageObjectFilterRender
import com.pedro.encoder.input.gl.render.filters.`object`.TextObjectFilterRender
import com.pedro.encoder.utils.gl.TranslateTo
import com.pedro.library.rtmp.RtmpStream
import com.pedro.library.view.OpenGlView

/**
 * All streaming logic lives here (version 2: one destination at a time, RtmpStream).
 */
class StreamEngine(
    private val view: OpenGlView,
    context: Context,
    checker: ConnectChecker
) {
    private val stream = RtmpStream(context, checker)
    private var textFilter: TextObjectFilterRender? = null
    private var logoFilter: ImageObjectFilterRender? = null

    val isStreaming: Boolean get() = stream.isStreaming

    fun startPreview() {
        if (stream.isOnPreview) return
        // 720p, 2.5 Mbps, then audio 44.1 kHz stereo 128 kbps
        val videoOk = stream.prepareVideo(1280, 720, 2_500_000)
        val audioOk = stream.prepareAudio(44100, true, 128 * 1000)
        if (videoOk && audioOk) stream.startPreview(view)
    }

    fun stopPreview() {
        if (stream.isOnPreview) stream.stopPreview()
    }

    fun switchCamera() = stream.switchCamera()

    fun goLive(url: String) {
        if (!stream.isStreaming) stream.startStream(url)
    }

    fun stopLive() {
        if (stream.isStreaming) stream.stopStream()
    }

    fun setOverlayText(text: String) {
        val gl = stream.getGlInterface()
        if (text.isBlank()) {
            textFilter?.let { gl.removeFilter(it) }
            textFilter = null
            return
        }
        if (textFilter == null) {
            val f = TextObjectFilterRender()
            gl.addFilter(f)
            textFilter = f
        }
        textFilter?.setText(text, 28f, Color.WHITE)
        textFilter?.setScale(40f, 10f)
        textFilter?.setPosition(TranslateTo.BOTTOM)
    }

    fun setLogo(bitmap: Bitmap) {
        val gl = stream.getGlInterface()
        if (logoFilter == null) {
            val f = ImageObjectFilterRender()
            gl.addFilter(f)
            logoFilter = f
        }
        logoFilter?.setImage(bitmap)
        logoFilter?.setScale(20f, 20f)
        logoFilter?.setPosition(TranslateTo.TOP_RIGHT)
    }

    fun release() {
        stopLive()
        stopPreview()
    }
}
