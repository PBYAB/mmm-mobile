# org.openapitools.client - Kotlin client library for OpenApi specification - MMM

## Requires

* Kotlin 1.4.30
* Gradle 6.8.3

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in OpenAPI definitions.
* Implementation of ApiClient is intended to reduce method counts, specifically to benefit Android targets.

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://localhost:8080*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AllergenApi* | [**createAllergen**](docs/AllergenApi.md#createallergen) | **POST** /api/v1/allergens | Create a new allergen
*AllergenApi* | [**deleteAllergenById**](docs/AllergenApi.md#deleteallergenbyid) | **DELETE** /api/v1/allergens/{id} | Delete allergen by ID
*AllergenApi* | [**findAllergenById**](docs/AllergenApi.md#findallergenbyid) | **GET** /api/v1/allergens/{id} | Find a allergen by ID
*AllergenApi* | [**listAllergens**](docs/AllergenApi.md#listallergens) | **GET** /api/v1/allergens | List all allergens with pagination
*AllergenApi* | [**updateAllergenById**](docs/AllergenApi.md#updateallergenbyid) | **PUT** /api/v1/allergens/{id} | Update allergen by ID
*ArticleApi* | [**createArticle**](docs/ArticleApi.md#createarticle) | **POST** /api/v1/knowledgeBase/articles | Create a new article
*ArticleApi* | [**findArticleById**](docs/ArticleApi.md#findarticlebyid) | **GET** /api/v1/knowledgeBase/articles/{id} | Find an article by ID
*ArticleApi* | [**listCategories1**](docs/ArticleApi.md#listcategories1) | **GET** /api/v1/knowledgeBase/articles | List all categories with pagination
*ArticleCategoryApi* | [**createCategory**](docs/ArticleCategoryApi.md#createcategory) | **POST** /api/v1/knowledgeBase/categories | Create a new category
*ArticleCategoryApi* | [**findCategoryById**](docs/ArticleCategoryApi.md#findcategorybyid) | **GET** /api/v1/knowledgeBase/categories/{id} | Find a category by ID
*ArticleCategoryApi* | [**listCategories**](docs/ArticleCategoryApi.md#listcategories) | **GET** /api/v1/knowledgeBase/categories | List all categories with pagination
*BrandApi* | [**createBrand**](docs/BrandApi.md#createbrand) | **POST** /api/v1/brands | Create a new brand
*BrandApi* | [**deleteBrandById**](docs/BrandApi.md#deletebrandbyid) | **DELETE** /api/v1/brands/{id} | Delete brand by ID
*BrandApi* | [**getAllBrands**](docs/BrandApi.md#getallbrands) | **GET** /api/v1/brands | Get all brands
*BrandApi* | [**getBrandById**](docs/BrandApi.md#getbrandbyid) | **GET** /api/v1/brands/{id} | Get brand by ID
*BrandApi* | [**updateBrandById1**](docs/BrandApi.md#updatebrandbyid1) | **PUT** /api/v1/brands/{id} | Update brand by ID
*CategoryApi* | [**createCategory1**](docs/CategoryApi.md#createcategory1) | **POST** /api/v1/categories | Create a new category
*CategoryApi* | [**deleteCategory**](docs/CategoryApi.md#deletecategory) | **DELETE** /api/v1/categories/{id} | Delete an existing category
*CategoryApi* | [**getCategories**](docs/CategoryApi.md#getcategories) | **GET** /api/v1/categories | Get a list of all categories
*CategoryApi* | [**getCategory**](docs/CategoryApi.md#getcategory) | **GET** /api/v1/categories/{id} | Get a category by ID
*CategoryApi* | [**updateCategory**](docs/CategoryApi.md#updatecategory) | **PUT** /api/v1/categories/{id} | Update an existing country
*Class1AuthenticationApi* | [**authenticate**](docs/Class1AuthenticationApi.md#authenticate) | **POST** /api/v1/auth/authenticate | Authenticate a user
*Class1AuthenticationApi* | [**refreshToken**](docs/Class1AuthenticationApi.md#refreshtoken) | **POST** /api/v1/auth/refresh-token | Refresh a token
*Class1AuthenticationApi* | [**register**](docs/Class1AuthenticationApi.md#register) | **POST** /api/v1/auth/register | Register a new user
*CountryApi* | [**createCountry**](docs/CountryApi.md#createcountry) | **POST** /api/v1/countries | Create a new country
*CountryApi* | [**deleteCountry**](docs/CountryApi.md#deletecountry) | **DELETE** /api/v1/countries/{id} | Delete an existing country
*CountryApi* | [**getCountries**](docs/CountryApi.md#getcountries) | **GET** /api/v1/countries | Get a list of all countries
*CountryApi* | [**getCountry**](docs/CountryApi.md#getcountry) | **GET** /api/v1/countries/{id} | Get a country by ID
*CountryApi* | [**updateCountry**](docs/CountryApi.md#updatecountry) | **PUT** /api/v1/countries/{id} | Update an existing country
*IngredientApi* | [**createIngredient**](docs/IngredientApi.md#createingredient) | **POST** /api/v1/ingredients | Create a new ingredient
*IngredientApi* | [**findAll**](docs/IngredientApi.md#findall) | **GET** /api/v1/ingredients | List all ingredients
*ProductApi* | [**createProduct**](docs/ProductApi.md#createproduct) | **POST** /api/v1/products | Create a new product
*ProductApi* | [**deleteProduct**](docs/ProductApi.md#deleteproduct) | **DELETE** /api/v1/products/{id} | Delete a product
*ProductApi* | [**getProduct**](docs/ProductApi.md#getproduct) | **GET** /api/v1/products/{id} | Get a product by ID
*ProductApi* | [**getProductByBarcode**](docs/ProductApi.md#getproductbybarcode) | **GET** /api/v1/products/barcode/{barcode} | Get a product by barcode
*ProductApi* | [**getProducts**](docs/ProductApi.md#getproducts) | **GET** /api/v1/products | Get all products
*ProductApi* | [**updateProduct**](docs/ProductApi.md#updateproduct) | **PUT** /api/v1/products/{id} | Update a product
*ProductIngredientApi* | [**createProductIngredient**](docs/ProductIngredientApi.md#createproductingredient) | **POST** /api/v1/products/ingredients | Create a new product ingredient
*ProductIngredientApi* | [**deleteProductIngredient**](docs/ProductIngredientApi.md#deleteproductingredient) | **DELETE** /api/v1/products/ingredients/{id} | Delete an existing category
*ProductIngredientApi* | [**getProductIngredient1**](docs/ProductIngredientApi.md#getproductingredient1) | **GET** /api/v1/products/ingredients/{id} | Get a product ingredient by ID
*ProductIngredientApi* | [**getProductIngredients**](docs/ProductIngredientApi.md#getproductingredients) | **GET** /api/v1/products/ingredients | Get a list of all product ingredients
*ProductIngredientApi* | [**updateProductIngredient**](docs/ProductIngredientApi.md#updateproductingredient) | **PUT** /api/v1/products/ingredients/{id} | Update an existing country
*RecipeApi* | [**addReview**](docs/RecipeApi.md#addreview) | **POST** /api/v1/recipes/{id}/review | Add review to recipe
*RecipeApi* | [**createRecipe**](docs/RecipeApi.md#createrecipe) | **POST** /api/v1/recipes | Create a new recipe
*RecipeApi* | [**deleteById**](docs/RecipeApi.md#deletebyid) | **DELETE** /api/v1/recipes/{id} | Delete recipe by ID
*RecipeApi* | [**getRecipe**](docs/RecipeApi.md#getrecipe) | **GET** /api/v1/recipes/{id} | Get a single recipe
*RecipeApi* | [**getRecipes**](docs/RecipeApi.md#getrecipes) | **GET** /api/v1/recipes | Get a list of all recipes
*RecipeApi* | [**updateBrandById**](docs/RecipeApi.md#updatebrandbyid) | **PUT** /api/v1/recipes/{id} | Update
*RecipeReviewApi* | [**createRecipeReview**](docs/RecipeReviewApi.md#createrecipereview) | **POST** /api/v1/review | Create a new product ingredient
*RecipeReviewApi* | [**deleteRecipeReview**](docs/RecipeReviewApi.md#deleterecipereview) | **DELETE** /api/v1/review/{id} | Delete an existing category
*RecipeReviewApi* | [**getProductIngredient**](docs/RecipeReviewApi.md#getproductingredient) | **GET** /api/v1/review/{id} | Get a product ingredient by ID
*RecipeReviewApi* | [**getRecipeReview**](docs/RecipeReviewApi.md#getrecipereview) | **GET** /api/v1/review | Get a list of all product ingredients
*RecipeReviewApi* | [**updateRecipeReview**](docs/RecipeReviewApi.md#updaterecipereview) | **PUT** /api/v1/review/{id} | Update an existing country


<a name="documentation-for-models"></a>
## Documentation for Models

 - [com.example.mmm_mobile.data.entities.Allergen](docs/Allergen.md)
 - [com.example.mmm_mobile.data.entities.AllergenDTO](docs/AllergenDTO.md)
 - [com.example.mmm_mobile.data.entities.ArticleCategoryDTO](docs/ArticleCategoryDTO.md)
 - [com.example.mmm_mobile.data.entities.ArticleDTO](docs/ArticleDTO.md)
 - [com.example.mmm_mobile.data.entities.AuthenticationRequest](docs/AuthenticationRequest.md)
 - [com.example.mmm_mobile.data.entities.AuthenticationResponse](docs/AuthenticationResponse.md)
 - [com.example.mmm_mobile.data.entities.Brand](docs/Brand.md)
 - [com.example.mmm_mobile.data.entities.BrandDTO](docs/BrandDTO.md)
 - [com.example.mmm_mobile.data.entities.Category](docs/Category.md)
 - [com.example.mmm_mobile.data.entities.CategoryDTO](docs/CategoryDTO.md)
 - [com.example.mmm_mobile.data.entities.Country](docs/Country.md)
 - [com.example.mmm_mobile.data.entities.CountryDTO](docs/CountryDTO.md)
 - [com.example.mmm_mobile.data.entities.CreateAllergenRequest](docs/CreateAllergenRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateArticleCategoryRequest](docs/CreateArticleCategoryRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateArticleRequest](docs/CreateArticleRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateBrandRequest](docs/CreateBrandRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateCountryRequest](docs/CreateCountryRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateIngredientRequest](docs/CreateIngredientRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateNutrimentRequest](docs/CreateNutrimentRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateProductCategoryRequest](docs/CreateProductCategoryRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateProductIngredientAnalysisRequest](docs/CreateProductIngredientAnalysisRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateProductIngredientRequest](docs/CreateProductIngredientRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateProductRequest](docs/CreateProductRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateRecipeRequest](docs/CreateRecipeRequest.md)
 - [com.example.mmm_mobile.data.entities.CreateRecipeReviewRequest](docs/CreateRecipeReviewRequest.md)
 - [com.example.mmm_mobile.data.entities.Ingredient](docs/Ingredient.md)
 - [com.example.mmm_mobile.data.entities.Nutriment](docs/Nutriment.md)
 - [com.example.mmm_mobile.data.entities.NutrimentDTO](docs/NutrimentDTO.md)
 - [com.example.mmm_mobile.data.entities.Page](docs/Page.md)
 - [com.example.mmm_mobile.data.entities.PageRecipeListItem](docs/PageRecipeListItem.md)
 - [com.example.mmm_mobile.data.entities.Pageable](docs/Pageable.md)
 - [com.example.mmm_mobile.data.entities.PageableObject](docs/PageableObject.md)
 - [com.example.mmm_mobile.data.entities.Product](docs/Product.md)
 - [com.example.mmm_mobile.data.entities.ProductDTO](docs/ProductDTO.md)
 - [com.example.mmm_mobile.data.entities.ProductIngredient](docs/ProductIngredient.md)
 - [com.example.mmm_mobile.data.entities.ProductIngredientAnalysis](docs/ProductIngredientAnalysis.md)
 - [com.example.mmm_mobile.data.entities.ProductIngredientAnalysisDTO](docs/ProductIngredientAnalysisDTO.md)
 - [com.example.mmm_mobile.data.entities.ProductIngredientDTO](docs/ProductIngredientDTO.md)
 - [com.example.mmm_mobile.data.entities.Recipe](docs/Recipe.md)
 - [com.example.mmm_mobile.data.entities.RecipeIngredient](docs/RecipeIngredient.md)
 - [com.example.mmm_mobile.data.entities.RecipeIngredientForm](docs/RecipeIngredientForm.md)
 - [com.example.mmm_mobile.data.entities.RecipeListItem](docs/RecipeListItem.md)
 - [com.example.mmm_mobile.data.entities.RecipeReview](docs/RecipeReview.md)
 - [com.example.mmm_mobile.data.entities.RegisterRequest](docs/RegisterRequest.md)
 - [com.example.mmm_mobile.data.entities.SortObject](docs/SortObject.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

<a name="bearerAuth"></a>
### bearerAuth

- **Type**: HTTP basic authentication

