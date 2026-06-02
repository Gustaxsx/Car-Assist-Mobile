package com.example.car_assist_mobile.screens.lembrete

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LembreteScreenViewModel : ViewModel() {
    var listaLembretes by mutableStateOf(listOf(
        Reminder("Troca de óleo em 2 dias", true),
        Reminder("Revisão dos freios", true),
        Reminder("Renovação do seguro", false),
        Reminder("Calibragem dos pneus", false)
    ))

    fun buscarLembretes(veiculoId: Int) {
    }
}