package com.example.jukesi_bossi

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class RulesFragment : Fragment(R.layout.fragment_rules) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textRules = view.findViewById<TextView>(R.id.textRules)

        val html = resources.openRawResource(R.raw.rules)
            .bufferedReader()
            .use { it.readText() }

        textRules.text = Html.fromHtml(
            html,
            Html.FROM_HTML_MODE_LEGACY
        )
    }
}