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

import org.openapitools.client.models.CreateRecipeRequest
import org.openapitools.client.models.PageRecipeListItem
import org.openapitools.client.models.RecipeDTO

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

class RecipeApi(basePath: kotlin.String = defaultBasePath, client: OkHttpClient = ApiClient.defaultClient) : ApiClient(basePath, client) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty(ApiClient.baseUrlKey, "http://localhost:8080")
        }
    }

    /**
     * Create a new recipe
     * 
     * @param createRecipeRequest 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun createRecipe(createRecipeRequest: CreateRecipeRequest) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = createRecipeWithHttpInfo(createRecipeRequest = createRecipeRequest)

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
     * Create a new recipe
     * 
     * @param createRecipeRequest 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun createRecipeWithHttpInfo(createRecipeRequest: CreateRecipeRequest) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = createRecipeRequestConfig(createRecipeRequest = createRecipeRequest)

        return@withContext request<CreateRecipeRequest, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation createRecipe
     *
     * @param createRecipeRequest 
     * @return RequestConfig
     */
    fun createRecipeRequestConfig(createRecipeRequest: CreateRecipeRequest) : RequestConfig<CreateRecipeRequest> {
        val localVariableBody = createRecipeRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.POST,
            path = "/api/v1/recipes",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Delete recipe by ID
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
    suspend fun deleteById(id: kotlin.Long) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = deleteByIdWithHttpInfo(id = id)

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
     * Delete recipe by ID
     * 
     * @param id 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun deleteByIdWithHttpInfo(id: kotlin.Long) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = deleteByIdRequestConfig(id = id)

        return@withContext request<Unit, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation deleteById
     *
     * @param id 
     * @return RequestConfig
     */
    fun deleteByIdRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.DELETE,
            path = "/api/v1/recipes/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Get recipe by ID
     * 
     * @param id 
     * @return RecipeDTO
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getById(id: kotlin.Long) : RecipeDTO = withContext(Dispatchers.IO) {
        val localVarResponse = getByIdWithHttpInfo(id = id)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as RecipeDTO
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
     * Get recipe by ID
     * 
     * @param id 
     * @return ApiResponse<RecipeDTO?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getByIdWithHttpInfo(id: kotlin.Long) : ApiResponse<RecipeDTO?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getByIdRequestConfig(id = id)

        return@withContext request<Unit, RecipeDTO>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getById
     *
     * @param id 
     * @return RequestConfig
     */
    fun getByIdRequestConfig(id: kotlin.Long) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/v1/recipes/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Get all recipes
     * 
     * @param name  (optional)
     * @param servings  (optional)
     * @param minKcalPerServing  (optional)
     * @param maxKcalPerServing  (optional)
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return PageRecipeListItem
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun getRecipes(name: kotlin.String? = null, servings: kotlin.collections.List<kotlin.Int>? = null, minKcalPerServing: kotlin.Double? = null, maxKcalPerServing: kotlin.Double? = null, page: kotlin.Int? = 0, size: kotlin.Int? = 10, sortBy: kotlin.String? = "id", sortDirection: kotlin.String? = "ASC") : PageRecipeListItem = withContext(Dispatchers.IO) {
        val localVarResponse = getRecipesWithHttpInfo(name = name, servings = servings, minKcalPerServing = minKcalPerServing, maxKcalPerServing = maxKcalPerServing, page = page, size = size, sortBy = sortBy, sortDirection = sortDirection)

        return@withContext when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as PageRecipeListItem
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
     * Get all recipes
     * 
     * @param name  (optional)
     * @param servings  (optional)
     * @param minKcalPerServing  (optional)
     * @param maxKcalPerServing  (optional)
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return ApiResponse<PageRecipeListItem?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun getRecipesWithHttpInfo(name: kotlin.String?, servings: kotlin.collections.List<kotlin.Int>?, minKcalPerServing: kotlin.Double?, maxKcalPerServing: kotlin.Double?, page: kotlin.Int?, size: kotlin.Int?, sortBy: kotlin.String?, sortDirection: kotlin.String?) : ApiResponse<PageRecipeListItem?> = withContext(Dispatchers.IO) {
        val localVariableConfig = getRecipesRequestConfig(name = name, servings = servings, minKcalPerServing = minKcalPerServing, maxKcalPerServing = maxKcalPerServing, page = page, size = size, sortBy = sortBy, sortDirection = sortDirection)

        return@withContext request<Unit, PageRecipeListItem>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation getRecipes
     *
     * @param name  (optional)
     * @param servings  (optional)
     * @param minKcalPerServing  (optional)
     * @param maxKcalPerServing  (optional)
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 10)
     * @param sortBy  (optional, default to "id")
     * @param sortDirection  (optional, default to "ASC")
     * @return RequestConfig
     */
    fun getRecipesRequestConfig(name: kotlin.String?, servings: kotlin.collections.List<kotlin.Int>?, minKcalPerServing: kotlin.Double?, maxKcalPerServing: kotlin.Double?, page: kotlin.Int?, size: kotlin.Int?, sortBy: kotlin.String?, sortDirection: kotlin.String?) : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, kotlin.collections.List<kotlin.String>>()
            .apply {
                if (name != null) {
                    put("name", listOf(name.toString()))
                }
                if (servings != null) {
                    put("servings", toMultiValue(servings.toList(), "multi"))
                }
                if (minKcalPerServing != null) {
                    put("minKcalPerServing", listOf(minKcalPerServing.toString()))
                }
                if (maxKcalPerServing != null) {
                    put("maxKcalPerServing", listOf(maxKcalPerServing.toString()))
                }
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
            path = "/api/v1/recipes",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Populate database with recipes
     * 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun populateDatabase() : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = populateDatabaseWithHttpInfo()

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
     * Populate database with recipes
     * 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun populateDatabaseWithHttpInfo() : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = populateDatabaseRequestConfig()

        return@withContext request<Unit, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation populateDatabase
     *
     * @return RequestConfig
     */
    fun populateDatabaseRequestConfig() : RequestConfig<Unit> {
        val localVariableBody = null
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        
        return RequestConfig(
            method = RequestMethod.GET,
            path = "/api/v1/recipes/populate",
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }

    /**
     * Update
     * 
     * @param id 
     * @param createRecipeRequest 
     * @return void
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     * @throws UnsupportedOperationException If the API returns an informational or redirection response
     * @throws ClientException If the API returns a client error response
     * @throws ServerException If the API returns a server error response
     */
    @Throws(IllegalStateException::class, IOException::class, UnsupportedOperationException::class, ClientException::class, ServerException::class)
    suspend fun updateRecipeById(id: kotlin.Long, createRecipeRequest: CreateRecipeRequest) : Unit = withContext(Dispatchers.IO) {
        val localVarResponse = updateRecipeByIdWithHttpInfo(id = id, createRecipeRequest = createRecipeRequest)

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
     * Update
     * 
     * @param id 
     * @param createRecipeRequest 
     * @return ApiResponse<Unit?>
     * @throws IllegalStateException If the request is not correctly configured
     * @throws IOException Rethrows the OkHttp execute method exception
     */
    @Throws(IllegalStateException::class, IOException::class)
    suspend fun updateRecipeByIdWithHttpInfo(id: kotlin.Long, createRecipeRequest: CreateRecipeRequest) : ApiResponse<Unit?> = withContext(Dispatchers.IO) {
        val localVariableConfig = updateRecipeByIdRequestConfig(id = id, createRecipeRequest = createRecipeRequest)

        return@withContext request<CreateRecipeRequest, Unit>(
            localVariableConfig
        )
    }

    /**
     * To obtain the request config of the operation updateRecipeById
     *
     * @param id 
     * @param createRecipeRequest 
     * @return RequestConfig
     */
    fun updateRecipeByIdRequestConfig(id: kotlin.Long, createRecipeRequest: CreateRecipeRequest) : RequestConfig<CreateRecipeRequest> {
        val localVariableBody = createRecipeRequest
        val localVariableQuery: MultiValueMap = mutableMapOf()
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        localVariableHeaders["Content-Type"] = "application/json"
        
        return RequestConfig(
            method = RequestMethod.PUT,
            path = "/api/v1/recipes/{id}".replace("{"+"id"+"}", encodeURIComponent(id.toString())),
            query = localVariableQuery,
            headers = localVariableHeaders,
            requiresAuthentication = true,
            body = localVariableBody
        )
    }


    private fun encodeURIComponent(uriComponent: kotlin.String): kotlin.String =
        HttpUrl.Builder().scheme("http").host("localhost").addPathSegment(uriComponent).build().encodedPathSegments[0]
}
