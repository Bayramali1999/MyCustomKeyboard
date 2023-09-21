package com.example.mycustomkeyboard

import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.lang.Character.isLetter
import java.lang.Character.toUpperCase

class MyKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {
    private lateinit var kv: KeyboardView
    private lateinit var keyboard: Keyboard
    private var isCaps: Boolean = false
    private lateinit var checkerLineaLayout: LinearLayout
    private lateinit var checkerCloseBtn: ImageButton
    private lateinit var openCheckerBtn: ImageButton
    private lateinit var checkerText: TextView

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @SuppressLint("MissingInflatedId")
    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_layout, null)
        checkerCloseBtn = view.findViewById(R.id.close_checked_text)
        checkerLineaLayout = view.findViewById(R.id.lv_checked)
        openCheckerBtn = view.findViewById(R.id.btn_checker)
        checkerText = view.findViewById(R.id.tv_checked)
        checkerCloseBtn.visibility = View.GONE
        checkerText.visibility = View.GONE
        checkerLineaLayout.visibility = View.GONE

        kv = view.findViewById(R.id.keyboard)
        keyboard = Keyboard(this, R.xml.qwerty)
        kv.keyboard = keyboard
        kv.setOnKeyboardActionListener(this)

        openCheckerBtn.setOnClickListener {
            kv.visibility = View.INVISIBLE
            checkerCloseBtn.visibility = View.VISIBLE
            checkerText.visibility = View.VISIBLE
            checkerLineaLayout.visibility = View.VISIBLE
            callApi()

        }

        checkerCloseBtn.setOnClickListener {
            kv.visibility = View.VISIBLE
            checkerLineaLayout.visibility = View.GONE
            checkerCloseBtn.visibility = View.GONE
            checkerText.visibility = View.GONE
            changeTextFromEt()
        }


        return view
    }

    private fun changeTextFromEt() {
        //change text
    }

    private fun callApi() {
        //get text and returned text change on
       //ApiInstance.api.getRightSentence()
    }


    override fun onKey(p0: Int, p1: IntArray?) {
        val ic = currentInputConnection
        playClick(p0)

        Log.d("TAG", "onKey: ${p0}")

        when (p0) {
            Keyboard.KEYCODE_DELETE -> {
                val selectedText = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    ic.deleteSurroundingText(1, 0)
                } else {
                    ic.commitText("", 0)
                }

            }

            Keyboard.KEYCODE_SHIFT -> {
                isCaps = !isCaps
                keyboard.setShifted(isCaps)
                kv.invalidateAllKeys()
            }

            Keyboard.KEYCODE_DONE -> {
                ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            }

            else -> {
                var code = p0.toChar();
                if (isLetter(code) && isCaps) {
                    code = toUpperCase(code)
                }
                ic.commitText(code.toString(), 1)
            }
        }
    }

    private fun playClick(i: Int) {
        val am = getSystemService(AUDIO_SERVICE) as AudioManager

        when (i) {
            32 -> {
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
            }

            Keyboard.KEYCODE_DONE -> {
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
            }

            else -> {
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
            }
        }
    }

    override fun onText(p0: CharSequence?) {
    }

    override fun swipeLeft() {

    }

    override fun swipeRight() {
    }

    override fun swipeDown() {
    }

    override fun swipeUp() {
    }

    override fun onPress(p0: Int) {
    }

    override fun onRelease(p0: Int) {
    }

}