package com.example.car_assist_mobile.data

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("car_assist_prefs", Context.MODE_PRIVATE)

    fun salvarSessao(id: Int, nome: String, email: String, cpf: String, dataNasc: String, senhaAtrevia: String) {
        prefs.edit().apply {
            putInt("USER_ID", id)
            putString("USER_NAME", nome)
            putString("USER_EMAIL", email)
            putString("USER_CPF", cpf)
            putString("USER_DATA_NASC", dataNasc)
            putString("USER_PASSWORD", senhaAtrevia)
            apply()
        }
    }

    fun getUserId(): Int = prefs.getInt("USER_ID", 0)
    fun getUserName(): String = prefs.getString("USER_NAME", "") ?: ""
    fun getUserEmail(): String = prefs.getString("USER_EMAIL", "") ?: ""
    fun getUserCpf(): String = prefs.getString("USER_CPF", "") ?: ""
    fun getUserDataNasc(): String = prefs.getString("USER_DATA_NASC", "") ?: ""
    fun getUserPassword(): String = prefs.getString("USER_PASSWORD", "") ?: ""

    fun limparSessao() {
        prefs.edit().clear().apply()
    }
}