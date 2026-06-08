//package com.example.car_assist_mobile.screens.lembrete
//
//import android.util.Log
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.car_assist_mobile.data.network.RetrofitClient
//import kotlinx.coroutines.launch
//
//class LembreteScreenViewModel : ViewModel() {
//
//    var listaLembretes by mutableStateOf<List<Reminder>>(emptyList())
//    var isLoading by mutableStateOf(false)
//
//    fun buscarLembretes(lembreteId: Int) {
//        if (lembreteId == 0) return
//
//        isLoading = true
//
//        viewModelScope.launch {
//            try {
//                val response = RetrofitClient.apiService.buscarLembretePorId(lembreteId)
//
//                if (response.isSuccessful && response.body() != null) {
//                    val lembretesApi = response.body()!!.data ?: emptyList()
//
//                    listaLembretes = lembretesApi.map { lembreteBanco ->
//                        Reminder(
//                            text = lembreteBanco.titulo,
//                            isUrgent = lembreteBanco.status?.equals("Pendente", ignoreCase = true) == true
//                        )
//                    }
//                } else {
//                    listaLembretes = emptyList()
//                }
//            } catch (e: Exception) {
//                Log.e("LEMBRETES_API", "Erro ao buscar lembrete por ID: ${e.message}")
//                listaLembretes = emptyList()
//            } finally {
//                isLoading = false
//            }
//        }
//    }
//}