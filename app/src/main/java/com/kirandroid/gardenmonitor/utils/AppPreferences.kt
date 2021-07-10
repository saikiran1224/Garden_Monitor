package com.kirandroid.gardenmonitor.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

object AppPreferences {

    private const val NAME = "Garden Monitor"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    //SharedPreferences variables
    private val IS_LOGIN = Pair("is_login", false)
    private val CUST_ID = Pair("custID", "")
    private val CUST_NAME = Pair("custName", "")
    private val CUST_EMAIL = Pair("custEmail", "")
    private val CUST_ADDRESS = Pair("custAddress","")
    private val CUST_PHONE = Pair("custPhone","")

    private val X1_VAL = Pair("x1_val",0)
    private val Y1_VAL = Pair("y1_val",0)
    private val WIDTH = Pair("width",0)
    private val HEIGHT = Pair("height",0)


    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }


    //an inline function to put variable and save it
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun showToast(context: Context, message: String?) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    //SharedPreferences variables getters/setters
    var isLogin: Boolean?
        get() = preferences.getBoolean(IS_LOGIN.first, IS_LOGIN.second)
        set(value) = preferences.edit {
            it.putBoolean(IS_LOGIN.first, value!!)
        }

    var customerID: String?
        get() = preferences.getString(CUST_ID.first, CUST_ID.second)
        set(value) = preferences.edit {
            it.putString(CUST_ID.first, value)
        }

    var customerName: String?
        get() = preferences.getString(CUST_NAME.first, CUST_NAME.second)
        set(value) = preferences.edit {
            it.putString(CUST_NAME.first, value)
        }

    var customerEmail: String?
        get() = preferences.getString(CUST_EMAIL.first, CUST_EMAIL.second)
        set(value) = preferences.edit {
            it.putString(CUST_EMAIL.first, value)
        }

    var customerAddress: String?
        get() = preferences.getString(CUST_ADDRESS.first, CUST_ADDRESS.second)
        set(value) = preferences.edit {
            it.putString(CUST_ADDRESS.first, value)
        }

    var customerPhone: String?
        get() = preferences.getString(CUST_PHONE.first, CUST_PHONE.second)
        set(value) = preferences.edit {
            it.putString(CUST_PHONE.first, value)
        }

    var x1Value: Int?
        get() = preferences.getInt(X1_VAL.first, X1_VAL.second)
        set(value) = preferences.edit {
            it.putInt(X1_VAL.first, value!!)
        }

    var y1Value: Int?
        get() = preferences.getInt(Y1_VAL.first, Y1_VAL.second)
        set(value) = preferences.edit {
            it.putInt(Y1_VAL.first, value!!)
        }

    var width: Int?
        get() = preferences.getInt(WIDTH.first, WIDTH.second)
        set(value) = preferences.edit {
            it.putInt(WIDTH.first, value!!)
        }

    var height: Int?
        get() = preferences.getInt(HEIGHT.first, HEIGHT.second)
        set(value) = preferences.edit {
            it.putInt(HEIGHT.first, value!!)
        }




}