package com.s16.engmyan.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.DialogFragment
import com.s16.engmyan.Constants
import com.s16.engmyan.R
import com.s16.widget.DialogButtonBar
import com.s16.widget.setPositiveButton
import android.app.Activity
import android.graphics.Point
import kotlinx.android.synthetic.main.fragment_credit.*


class CreditFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_NoTitle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_credit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webViewCredit.settings.allowFileAccess = true
        webViewCredit.loadUrl(Constants.URL_CREDIT)

        dialogButtons.setPositiveButton(android.R.string.ok) { _, _ ->
            dialog!!.cancel()
        }
    }

    override fun onResume() {
        super.onResume()

        val height = (getScreenHeight(activity!!) * 0.95).toInt()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height)
    }

    private fun getScreenHeight(activity: Activity): Int {
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        return size.y
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreditFragment()
    }
}
