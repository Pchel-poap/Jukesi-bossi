package com.example.jukesi_bossi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AuthorAdapter(
    context: Context,
    authors: List<Author>
) : ArrayAdapter<Author>(
    context,
    R.layout.item_author,
    authors
) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_author, parent, false)

        val author = getItem(position)!!

        val image = view.findViewById<ImageView>(R.id.imageAuthor)
        val name = view.findViewById<TextView>(R.id.textAuthor)

        image.setImageResource(author.image)
        name.text = author.name

        return view
    }
}