/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package org.openapitools.client.apis

import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.HttpUrl

import org.openapitools.client.models.Country
import org.openapitools.client.models.CountryDTO
import org.openapitools.client.models.CreateCountryRequest
import org.openapitools.client.models.PageCountryDTO

import com.squareup.moshi.Json

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.infrastructure.ApiResponse
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ClientError
import org.openapitools.client.infrastructure.ServerException
import org.openapitools.client.infrastructure.ServerError
import org.openapitools.client.infrastructure.MultiValueMap
import org.openapitools.client.infrastructure.PartConfig
import org.openapitools.client.infrastructure.RequestConfig
import org.openapitools.client.infrastructure.RequestMethod
import org.openapitools.client.infrastructure.ResponseType
import org.openapitools.client.infrastructure.Success
import org.openapitools.client.infrastructure.toMultiValue

class CountryApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://localhost:8080")
        }
    }

    /**
     * Create a new country
     * 
     * @param createCountryRequest 
     * @return Country
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun createCountry(createCountryRequest: CreateCountryRequest) : Country = withContext(Dispatchers.IO) {
        val localVarResponse = createCountryWithHttpInfo(createCountryRequest = createCountryRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as Country
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Create a new country
     * 
     * @param createCountryRequest 
     * @return ApiResponse<Country?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun createCountryWithHttpInfo(createCountryRequest: CreateCountryRequest) : ApiResponse<Country?> = withContext(Dispatchers.IO) {
        val localVariableConfig = createCountryRequestConfig(createCountryRequest = createCountryRequest)

        return@withContext request<CreateCountryRequest, Country>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation createCountry
     *
     * @param createCountryRequest 
     * @return RequestConfig
     */
    fun createCountryRequestConfig(createCountryRequest: CreateCountryRequest) : RequestConfig<CreateCountryRequest> {
        val localVariableBody = createCountryRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.POST,
            path = "/api/v1/countries",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Delete an existing country
     * 
     * @param id 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun deleteCountry(id: kotlin.Long) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = deleteCountryWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> Unit
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Delete an existing country
     * 
     * @param id 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun deleteCountryWithHttpInfo(id: kotlin.Long) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = deleteCountryRequestConfig(id = id)

        return@withContext request<Unit, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation deleteCountry
     *
     * @param id 
     * @return RequestConfig
     */
    fun deleteCountryRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.DELETE,
            path = "/api/v1/countries/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Get a list of all countries
     * 
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return PageCountryDTO
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getCountries(page: kotlin.Int? = 0, size: kotlin.Int? = 10, sortBy: kotlin.String? = "id", sortDirection: kotlin.String? = "ASC") : PageCountryDTO = withContext(Dispatchers.IO) {
        val localVarResponse = getCountriesWithHttpInfo(page = page, size = size, sortBy = sortBy, sortDirection = sortDirection)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as PageCountryDTO
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Get a list of all countries
     * 
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return ApiResponse<PageCountryDTO?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getCountriesWithHttpInfo(page: kotlin.Int?, size: kotlin.Int?, sortBy: kotlin.String?, sortDirection: kotlin.String?) : ApiResponse<PageCountryDTO?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getCountriesRequestConfig(page = page, size = size, sortBy = sortBy, sortDirection = sortDirection)

        return@withContext request<Unit, PageCountryDTO>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getCountries
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return RequestConfig
     */
    fun getCountriesRequestConfig(page: kotlin.Int?, size: kotlin.Int?, sortBy: kotlin.String?, sortDirection: kotlin.String?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (page != null) {
                    put("page", listOf(page.toString()))
                }
                if (size != null) {
                    put("size", listOf(size.toString()))
                }
                if (sortBy != null) {
                    put("sortBy", listOf(sortBy.toString()))
                }
                if (sortDirection != null) {
                    put("sortDirection", listOf(sortDirection.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/v1/countries",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Get a country by ID
     * 
     * @param id 
     * @return CountryDTO
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getCountry(id: kotlin.Long) : CountryDTO = withContext(Dispatchers.IO) {
        val localVarResponse = getCountryWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as CountryDTO
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Get a country by ID
     * 
     * @param id 
     * @return ApiResponse<CountryDTO?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getCountryWithHttpInfo(id: kotlin.Long) : ApiResponse<CountryDTO?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getCountryRequestConfig(id = id)

        return@withContext request<Unit, CountryDTO>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getCountry
     *
     * @param id 
     * @return RequestConfig
     */
    fun getCountryRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/v1/countries/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Update an existing country
     * 
     * @param id 
     * @param createCountryRequest 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun updateCountry(id: kotlin.Long, createCountryRequest: CreateCountryRequest) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = updateCountryWithHttpInfo(id = id, createCountryRequest = createCountryRequest)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> Unit
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()} ${localVarError.body}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
     * Update an existing country
     * 
     * @param id 
     * @param createCountryRequest 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun updateCountryWithHttpInfo(id: kotlin.Long, createCountryRequest: CreateCountryRequest) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = updateCountryRequestConfig(id = id, createCountryRequest = createCountryRequest)

        return@withContext request<CreateCountryRequest, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation updateCountry
     *
     * @param id 
     * @param createCountryRequest 
     * @return RequestConfig
     */
    fun updateCountryRequestConfig(id: kotlin.Long, createCountryRequest: CreateCountryRequest) : RequestConfig<CreateCountryRequest> {
        val localVariableBody = createCountryRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.PUT,
            path = "/api/v1/countries/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
