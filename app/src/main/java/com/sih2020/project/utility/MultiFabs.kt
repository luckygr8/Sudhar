package com.sih2020.project.utility

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sih2020.project.R

class MultiFabs(
    private var view: View,
    private var resID: Int,
    private var hintstr1: String,
    private var resID1: Int,
    private var hintstr2: String,
    private var resID2: Int,
    private var hintstr3: String,
    private var resID3: Int
) {

    constructor(view: View, hintstr1: String, hintstr2: String, hintstr3: String) : this(
        view,
        R.drawable.ic_launcher,
        hintstr1,
        R.drawable.ic_launcher,
        hintstr2,
        R.drawable.ic_launcher,
        hintstr3,
        R.drawable.ic_launcher
    ) {
        fab.setImageResource(resID)
        fab1.setImageResource(resID1)
        fab2.setImageResource(resID2)
        fab3.setImageResource(resID3)

        group1.alpha = 0f
        group2.alpha = 0f
        group3.alpha = 0f

        hint1.text = hintstr1
        hint2.text = hintstr2
        hint3.text = hintstr3
    }

    private var isOpen = false
    private var duration = 200L

    private var group1: LinearLayout
    private var group2: LinearLayout
    private var group3: LinearLayout

    private var fab: FloatingActionButton
    private var fab1: FloatingActionButton
    private var fab2: FloatingActionButton
    private var fab3: FloatingActionButton

    private var hint1: TextView
    private var hint2: TextView
    private var hint3: TextView

    init {
        fab = view.findViewById(R.id.fab)
        fab1 = view.findViewById(R.id.fab1)
        fab2 = view.findViewById(R.id.fab2)
        fab3 = view.findViewById(R.id.fab3)

        hint1 = view.findViewById(R.id.hint1)
        hint2 = view.findViewById(R.id.hint2)
        hint3 = view.findViewById(R.id.hint3)

        group1 = view.findViewById(R.id.fabgroup1)
        group2 = view.findViewById(R.id.fabgroup2)
        group3 = view.findViewById(R.id.fabgroup3)

    }

    fun toggle() {
        if (isOpen)
            close()
        else
            show()
    }

    private fun show() {
        isOpen = true
        group1.animate().translationY(-Functions.getRes().getDimension(R.dimen.standard1)).alpha(1f)
            .duration =
            duration
        group2.animate().translationY(-Functions.getRes().getDimension(R.dimen.standard2)).alpha(1f)
            .duration =
            duration
        group3.animate().translationY(-Functions.getRes().getDimension(R.dimen.standard3)).alpha(1f)
            .duration =
            duration
    }

    private fun close() {
        isOpen = false
        group1.animate().translationY(0f).alpha(0f)
        group2.animate().translationY(0f).alpha(0f)
        group3.animate().translationY(0f).alpha(0f)
    }
}