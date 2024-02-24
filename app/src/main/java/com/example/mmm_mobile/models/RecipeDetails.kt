package com.example.mmm_mobile.models

import com.example.mmm_mobile.data.entity.RecipeWithIngredients
import org.openapitools.client.models.RecipeDTO
import org.openapitools.client.models.RecipeIngredientDTO
import org.openapitools.client.models.RecipeReviewDTO

class RecipeDetails(
    val id: Long = 0,
    val name: String = "",
    val servings: Int? = null,
    val totalTime: Int? = null,
    val kcalPerServing: Double? = null,
    val instructions: String = "",
    val image: Any? = null,
    val ingredients: List<RecipeIngredientDTO> = emptyList(),
    val rating: Double? = null,
    val reviews: List<RecipeReviewDTO> = emptyList()
){
    fun mapFromAny(recipe: Any): RecipeDetails {
        return when(recipe){
            is RecipeDTO -> mapFromDTO(recipe)
            is RecipeWithIngredients -> mapFromDB(recipe)
            else -> RecipeDetails()
        }
    }

    fun mapFromDTO(recipeDTO: RecipeDTO): RecipeDetails {
        return RecipeDetails(
            id = recipeDTO.id,
            name = recipeDTO.name,
            servings = recipeDTO.servings,
            totalTime = recipeDTO.totalTime,
            kcalPerServing = recipeDTO.kcalPerServing,
            instructions = recipeDTO.instructions ?: "",
            image = recipeDTO.coverImageUrl ?: "",
            ingredients = recipeDTO.ingredients ?: emptyList(),
            rating = recipeDTO.averageRating,
            reviews = recipeDTO.reviews?.toList() ?: emptyList()
        )
    }

    fun mapFromDB(recipeDB: RecipeWithIngredients): RecipeDetails {
        return RecipeDetails(
            id = recipeDB.recipe.id,
            name = recipeDB.recipe.name,
            servings = recipeDB.recipe.servings,
            totalTime = recipeDB.recipe.totalTime,
            kcalPerServing = recipeDB.recipe.kcalPerServing,
            instructions = recipeDB.recipe.instructions,
            image = recipeDB.recipe.image,
            ingredients = recipeDB.ingredients.map {
                RecipeIngredientDTO(
                    name = it.ingredient.name,
                    amount = it.crossRef.amount,
                    unit = RecipeIngredientDTO.Unit.valueOf(it.crossRef.unit.name),
                    id = it.ingredient.id
                )

            },
            rating = recipeDB.recipe.rating
        )
    }

    fun mapToDto(): RecipeDTO {
        return RecipeDTO(
            id = id,
            name = name,
            servings = servings,
            totalTime = totalTime,
            kcalPerServing = kcalPerServing,
            instructions = instructions,
            coverImageUrl = image.toString(),
            ingredients = ingredients,
            averageRating = rating,
            reviews = reviews.toSet()
        )
    }
}