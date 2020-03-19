package com.sih2020.project.utility

import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import com.sih2020.project.base.MainActivity
import com.sih2020.project.R

object Validate {

    private val res = MainActivity.getMainContext().resources

    fun validateTextFields(vararg textFields: TextInputEditText):Boolean{
        textFields.forEach {
            if(it.text.isNullOrBlank()){
                it.error = res.getString(R.string.required)
                return false
            }
        }
        return true
    }

    fun validateEmailFields(vararg emailFields:TextInputEditText):Boolean{
        emailFields.forEach {
            if(!Functions.isEmailValid(it.text.toString())){
                it.error = res.getString(R.string.email_is_not_valid)
                return false
            }
        }
        return true
    }

    fun validateSpinners(vararg spinners:Spinner):Boolean{
        spinners.forEach { it ->
            if(it.selectedItemPosition<=0){
                return false
            }
        }
        return true
    }
}