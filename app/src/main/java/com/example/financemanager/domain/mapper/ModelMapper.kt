package com.example.financemanager.domain.mapper

interface ModelMapper<S, D> {
    fun map(source: S): D
}
