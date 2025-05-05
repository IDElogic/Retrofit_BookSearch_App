package com.android.retrofit.data

import com.android.retrofit.model.APIResponse
import com.android.retrofit.network.GoogleBookAPIService

/**
 * Repository that gets data of books from googleBookApi
 */
interface BookShelfRepository {
    /** Get data of books from googleBookApi */
    suspend fun getBookData(searchedTerm: String): APIResponse
}

/**
 * Network Implementation of the Repository that gets data of books from googleBookApi
 */
class NetworkRepository (
    private val googleBookApi: GoogleBookAPIService
): BookShelfRepository {
    override suspend fun getBookData(searchedTerm: String): APIResponse = googleBookApi.searchForBook(searchedTerm)
}