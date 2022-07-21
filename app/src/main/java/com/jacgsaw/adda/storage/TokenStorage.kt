package com.jacgsaw.adda.storage

interface TokenStorage {

    fun saveToken(token: String)

    fun getToken(): String?

    fun clear()
}