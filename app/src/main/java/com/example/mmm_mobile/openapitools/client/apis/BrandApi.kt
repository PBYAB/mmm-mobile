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

import org.openapitools.client.models.BrandDTO
import org.openapitools.client.models.CreateBrandRequest
import org.openapitools.client.models.PageBrandDTO

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

class BrandApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://localhost:8080")
        }
    }

    /**
     * Create a new brand
     * 
     * @param createBrandRequest 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun createBrand(createBrandRequest: CreateBrandRequest) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = createBrandWithHttpInfo(createBrandRequest = createBrandRequest)

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
     * Create a new brand
     * 
     * @param createBrandRequest 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun createBrandWithHttpInfo(createBrandRequest: CreateBrandRequest) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = createBrandRequestConfig(createBrandRequest = createBrandRequest)

        return@withContext request<CreateBrandRequest, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation createBrand
     *
     * @param createBrandRequest 
     * @return RequestConfig
     */
    fun createBrandRequestConfig(createBrandRequest: CreateBrandRequest) : RequestConfig<CreateBrandRequest> {
        val localVariableBody = createBrandRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.POST,
            path = "/api/v1/brands",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Delete brand by ID
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
    suspend fun deleteBrandById(id: kotlin.Long) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = deleteBrandByIdWithHttpInfo(id = id)

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
     * Delete brand by ID
     * 
     * @param id 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun deleteBrandByIdWithHttpInfo(id: kotlin.Long) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = deleteBrandByIdRequestConfig(id = id)

        return@withContext request<Unit, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation deleteBrandById
     *
     * @param id 
     * @return RequestConfig
     */
    fun deleteBrandByIdRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.DELETE,
            path = "/api/v1/brands/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Get all brands
     * 
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return PageBrandDTO
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getAllBrands(page: kotlin.Int? = 0, size: kotlin.Int? = 10, sortBy: kotlin.String? = "id", sortDirection: kotlin.String? = "ASC") : PageBrandDTO = withContext(Dispatchers.IO) {
        val localVarResponse = getAllBrandsWithHttpInfo(page = page, size = size, sortBy = sortBy, sortDirection = sortDirection)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as PageBrandDTO
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
     * Get all brands
     * 
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return ApiResponse<PageBrandDTO?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getAllBrandsWithHttpInfo(page: kotlin.Int?, size: kotlin.Int?, sortBy: kotlin.String?, sortDirection: kotlin.String?) : ApiResponse<PageBrandDTO?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getAllBrandsRequestConfig(page = page, size = size, sortBy = sortBy, sortDirection = sortDirection)

        return@withContext request<Unit, PageBrandDTO>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getAllBrands
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return RequestConfig
     */
    fun getAllBrandsRequestConfig(page: kotlin.Int?, size: kotlin.Int?, sortBy: kotlin.String?, sortDirection: kotlin.String?) : RequestConfig<Unit> {
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
            path = "/api/v1/brands",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Get brand by ID
     * 
     * @param id 
     * @return BrandDTO
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getBrandById(id: kotlin.Long) : BrandDTO = withContext(Dispatchers.IO) {
        val localVarResponse = getBrandByIdWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as BrandDTO
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
     * Get brand by ID
     * 
     * @param id 
     * @return ApiResponse<BrandDTO?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getBrandByIdWithHttpInfo(id: kotlin.Long) : ApiResponse<BrandDTO?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getBrandByIdRequestConfig(id = id)

        return@withContext request<Unit, BrandDTO>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getBrandById
     *
     * @param id 
     * @return RequestConfig
     */
    fun getBrandByIdRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/v1/brands/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Update brand by ID
     * 
     * @param id 
     * @param createBrandRequest 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun updateBrandById(id: kotlin.Long, createBrandRequest: CreateBrandRequest) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = updateBrandByIdWithHttpInfo(id = id, createBrandRequest = createBrandRequest)

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
     * Update brand by ID
     * 
     * @param id 
     * @param createBrandRequest 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun updateBrandByIdWithHttpInfo(id: kotlin.Long, createBrandRequest: CreateBrandRequest) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = updateBrandByIdRequestConfig(id = id, createBrandRequest = createBrandRequest)

        return@withContext request<CreateBrandRequest, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation updateBrandById
     *
     * @param id 
     * @param createBrandRequest 
     * @return RequestConfig
     */
    fun updateBrandByIdRequestConfig(id: kotlin.Long, createBrandRequest: CreateBrandRequest) : RequestConfig<CreateBrandRequest> {
        val localVariableBody = createBrandRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.PUT,
            path = "/api/v1/brands/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
