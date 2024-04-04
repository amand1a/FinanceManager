package com.example.financemanager.presentation.extension

fun String.toNormalDouble(): Double {
    val stringWithoutComma = this.toDoubleOrNull() ?: 0.0
    return stringWithoutComma / 100
}