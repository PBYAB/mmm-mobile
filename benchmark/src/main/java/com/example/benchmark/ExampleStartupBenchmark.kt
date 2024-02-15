package com.example.benchmark

import android.util.Log
import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

//    @Test
//    fun startupCompilationModeNone() = startup(CompilationMode.None())
//
//    @Test
//    fun startupCompilationModePartial() = startup(CompilationMode.Partial(
//        BaselineProfileMode.Require))

    @Test
    fun scrollAndNavigateCompilationModeNone() = scrollAndNavigate(CompilationMode.None())

    @Test
    fun scrollAndNavigateCompilationModePartial() = scrollAndNavigate(CompilationMode.Partial())

    fun startup(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.example.mmm_mobile",
        metrics = listOf(StartupTimingMetric(), FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()
    }

    fun scrollAndNavigate(mode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = "com.example.mmm_mobile",
        metrics = listOf(StartupTimingMetric(), FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD,
        compilationMode = mode
    ) {
        pressHome()
        startActivityAndWait()

        login()

        pressMoreOptions()
        pressBack()

        pressSearch()
        searchScreenTest()

        productListScroll()
        pressBack()

        pressFAB()
        addProductScreenTest()
        pressBack()

        navigateToRecipeList()
        recipeListScroll()
        pressBack()

        pressFAB()
        addRecipeScreenTest()
        pressBack()

        scrollUpRecipeList()

        addFavoriteRecipes()

        navigateToFavoriteRecipes()
        favoriteRecipeListScroll()
        pressBack()
    }
}

fun MacrobenchmarkScope.login() {
    Log.i("ExampleStartupBenchmark", "login")
    if(device.hasObject(By.textContains("Login"))) {

        val username = "admin@pb.pl"
        val password = "MocnoPbKocham"

        // Wait until the login screen is loaded
        device.wait(Until.hasObject(By.textContains("Login")), 5000)

        // Enter the username
        device.wait(Until.hasObject(By.res("email_field")), 5000)
        val usernameField = device.findObject(By.res("email_field"))
        usernameField.text = username

        // Enter the password
        device.wait(Until.hasObject(By.res("password_field")), 5000)
        val passwordField = device.findObject(By.res("password_field"))
        passwordField.text = password

        device.wait(Until.hasObject(By.res("login_button")), 5000)
        val loginButton = device.findObject(By.res("login_button"))
        loginButton.click()

        // Wait until the next screen is loaded
        device.wait(Until.hasObject(By.textContains("mmm_mobile")), 5000)
    }
    Log.i("ExampleStartupBenchmark", "login done")
}

fun MacrobenchmarkScope.scrollUpRecipeList() {
    Log.i("ExampleStartupBenchmark", "scrollUpRecipeList")
    device.wait(Until.hasObject(By.res("recipe_list")), 5000)
    val list = device.findObject(By.res("recipe_list"))

    while (!device.hasObject(By.textContains("Ciasto biszkoptowe"))) {
        list.setGestureMargin(device.displayWidth / 5)
        list.fling(Direction.UP, 1000)

        if(device.hasObject(By.textContains("Ciasto biszkoptowe"))){
            break
        }
    }
    Log.i("ExampleStartupBenchmark", "scrollUpRecipeList done")
}

fun MacrobenchmarkScope.productListScroll(){
    Log.i("ExampleStartupBenchmark", "productListScroll")
    device.wait(Until.hasObject(By.textContains("product_list")), 5000)
    val list = device.findObject(By.res("product_list"))

    device.wait(Until.hasObject(By.textContains("Red onions")), 5000)

    while (!device.hasObject(By.textContains("Lasagne"))) {
        list.setGestureMargin(device.displayWidth / 5)
        list.scroll(Direction.DOWN, 0.3f)

        if(device.hasObject(By.textContains("Lasagne"))){
            break
        }
    }
    Log.i("ExampleStartupBenchmark", "Lasagne found")
    device.findObject(By.textContains("Lasagne")).click()

    device.wait(Until.hasObject(By.res("product_detail_screen")), 5000)

    device.wait(Until.hasObject(By.textContains("milk")), 5000)
    Log.i("ExampleStartupBenchmark", "productListScroll done")
}

fun MacrobenchmarkScope.pressBack() {
    Log.i("ExampleStartupBenchmark", "pressBack")
    device.pressBack()
    Log.i("ExampleStartupBenchmark", "pressBack done")
}

fun MacrobenchmarkScope.navigateToRecipeList() {
    Log.i("ExampleStartupBenchmark", "navigateToRecipeList")
    device.wait(Until.hasObject(By.textContains("Recipes")), 5000)
    val recipes = device.findObject(By.textContains("Recipes"))
    if(recipes != null){
        recipes.click()
    }else
    {
        pressHome()
        startActivityAndWait()

        login()
    }
    device.wait(Until.hasObject(By.res("recipe_list")), 5000)
    Log.i("ExampleStartupBenchmark", "navigateToRecipeList done")
}
fun MacrobenchmarkScope.recipeListScroll(){

    Log.i("ExampleStartupBenchmark", "recipeListScroll")
    device.wait(Until.hasObject(By.res("recipe_list")), 5000)
    val list = device.findObject(By.res("recipe_list"))

    device.wait(Until.hasObject(By.textContains("Ciasto chałwowe")), 5000)

    while (!device.hasObject(By.textContains("Raffaello"))) {
        device.wait(Until.hasObject(By.res("recipe_list")), 5000)
        val list2 = device.findObject(By.res("recipe_list"))

        list2.setGestureMargin(device.displayWidth / 5)
        list2.scroll(Direction.DOWN, 0.5f)

        if(device.hasObject(By.textContains("Raffaello"))){
            break
        }
    }
    Log.i("ExampleStartupBenchmark", "Raffaello found")

    device.findObject(By.textContains("Raffaello")).click()

    device.wait(Until.hasObject(By.res("recipe_detail_screen")), 5000)

    device.wait(Until.hasObject(By.textContains("Servings: 5")), 5000)
    Log.i("ExampleStartupBenchmark", "recipeListScroll done")
}

fun MacrobenchmarkScope.addFavoriteRecipes() {
    Log.i("ExampleStartupBenchmark", "addFavoriteRecipes")

    device.wait(Until.hasObject(By.textContains("Ciasto chałwowe")), 5000)

    val recipesToSave = listOf(
        "Tort truskawkowy z mascarpone",
        "Ciasto biszkoptowe",
        "Ciasto chałwowe",
        "Tort czekoladowy",
        "Tort czekoladowy z kremem z białej czekolady",
        "Raffaello",
        "Rolada bananowa",
        "Miodowiec z orzechami",
    )

    for (recipe in recipesToSave) {
        while (!device.hasObject(By.textContains(recipe))) {
            device.wait(Until.hasObject(By.res("recipe_list")), 5000)
            val list = device.findObject(By.res("recipe_list"))
            list.setGestureMargin(device.displayWidth / 5)
            list.scroll(Direction.DOWN, 0.3f)
        }
        Log.i("ExampleStartupBenchmark", "recipe: $recipe found")
        device.wait(Until.hasObject(By.textContains(recipe)), 5000)
        device.findObject(By.textContains(recipe)).click()

        device.wait(Until.hasObject(By.res("favourite_button")), 5000)

        if(device.hasObject(By.res("remove_favourite_button"))) {
            pressBack()
            continue
        }

        if(device.hasObject(By.res("favourite_button"))){
            device.findObject(By.res("favourite_button")).click()
            Log.i("ExampleStartupBenchmark", "recipe: $recipe added to favorites")
        }
        pressBack()

        device.wait(Until.hasObject(By.res("recipe_list")), 5000)
        Log.i("ExampleStartupBenchmark", "recipe: $recipe back to list")
    }
}

fun MacrobenchmarkScope.navigateToFavoriteRecipes() {
    Log.i("ExampleStartupBenchmark", "navigateToFavoriteRecipes")
    device.findObject(By.textContains("Favourite")).click()
    device.wait(Until.hasObject(By.res("favourite_recipes_list")), 5000)
    Log.i("ExampleStartupBenchmark", "navigateToFavoriteRecipes done")
}

fun MacrobenchmarkScope.favoriteRecipeListScroll(){
    Log.i("ExampleStartupBenchmark", "favoriteRecipeListScroll")
    device.wait(Until.hasObject(By.textContains("favourite_recipes_list")), 5000)
    val list = device.findObject(By.res("favourite_recipes_list"))

    device.wait(Until.hasObject(By.textContains("Ciasto chałwowe")), 5000)

    while (!device.hasObject(By.textContains("Miodowiec z orzechami"))) {
        list.setGestureMargin(device.displayWidth / 5)
        list.scroll(Direction.DOWN, 0.1f)
    }

    device.findObject(By.textContains("Miodowiec z orzechami")).click()

    device.wait(Until.hasObject(By.res("recipe_detail_screen")), 5000)

    device.wait(Until.hasObject(By.textContains("Servings: 10")), 5000)
    Log.i("ExampleStartupBenchmark", "favoriteRecipeListScroll done")
}

fun MacrobenchmarkScope.pressMoreOptions() {
    Log.i("ExampleStartupBenchmark", "pressMoreOptions")
    device.wait(Until.hasObject(By.desc("More")), 5000)
    device.findObject(By.desc("More")).click()
    Log.i("ExampleStartupBenchmark", "pressMoreOptions done")
}

fun MacrobenchmarkScope.pressFAB() {
    Log.i("ExampleStartupBenchmark", "pressFAB")
    device.wait(Until.hasObject(By.desc("Add")), 5000)
    device.findObject(By.desc("Add")).click()
    Log.i("ExampleStartupBenchmark", "pressFAB done")
}

fun MacrobenchmarkScope.addProductScreenTest(){
    Log.i("ExampleStartupBenchmark", "addProductScreenTest")
    device.wait(Until.hasObject(By.res("add_product_screen")), 5000)

    device.wait(Until.hasObject(By.res("product_brand_dropdown")), 5000)
    while (!device.hasObject(By.res("product_brand_dropdown"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.res("product_brand_dropdown")).click()
    Log.i("ExampleStartupBenchmark", "product_brand_dropdown clicked")
    device.wait(Until.hasObject(By.textContains("Dr.Oetker")), 5000)
    device.findObject(By.textContains("Dr.Oetker")).click()

    device.wait(Until.hasObject(By.res("product_allergen_dropdown")), 5000)
    while (!device.hasObject(By.res("product_allergen_dropdown"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.res("product_allergen_dropdown")).click()
    Log.i("ExampleStartupBenchmark", "product_allergen_dropdown clicked")
    device.wait(Until.hasObject(By.textContains("pb")), 5000)
    device.findObject(By.textContains("pb")).click()

    device.wait(Until.hasObject(By.res("product_category_dropdown")), 5000)
    while (!device.hasObject(By.res("product_category_dropdown"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.res("product_category_dropdown")).click()
    Log.i("ExampleStartupBenchmark", "product_category_dropdown clicked")
    device.wait(Until.hasObject(By.textContains("pb")), 5000)
    device.findObject(By.textContains("pb")).click()

    device.wait(Until.hasObject(By.res("product_country_dropdown")), 5000)
    while (!device.hasObject(By.res("product_country_dropdown"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.res("product_country_dropdown")).click()
    Log.i("ExampleStartupBenchmark", "product_country_dropdown clicked")
    device.wait(Until.hasObject(By.textContains("USA")), 5000)
    device.findObject(By.textContains("USA")).click()

    device.wait(Until.hasObject(By.textContains("Nutri Score")), 5000)
    while (!device.hasObject(By.textContains("Nutri Score"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.textContains("Nutri Score")).click()
    Log.i("ExampleStartupBenchmark", "Nutri Score clicked")
    device.wait(Until.hasObject(By.textContains("A")), 5000)
    device.findObject(By.textContains("A")).click()

    device.wait(Until.hasObject(By.textContains("Nova Group")), 5000)
    while (!device.hasObject(By.textContains("Nova Group"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.textContains("Nova Group")).click()
    Log.i("ExampleStartupBenchmark", "Nova Group clicked")
    device.wait(Until.hasObject(By.textContains("1")), 5000)
    device.findObject(By.textContains("1")).click()

    device.wait(Until.hasObject(By.res("product_ingredients")), 5000)
    while (!device.hasObject(By.res("product_ingredients"))) {
        device.findObject(By.res("add_product_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.res("product_ingredients")).click()
    Log.i("ExampleStartupBenchmark", "product_ingredients clicked")

    device.wait(Until.hasObject(By.textContains("jajka")), 5000)
    if(device.hasObject(By.textContains("jajka"))){
        device.findObject(By.textContains("jajka")).click()
        Log.i("ExampleStartupBenchmark", "jajka clicked")
    }
    Log.i("ExampleStartupBenchmark", "addProductScreenTest done")
}

fun MacrobenchmarkScope.addRecipeScreenTest(){
    Log.i("ExampleStartupBenchmark", "addRecipeScreenTest")
    device.wait(Until.hasObject(By.res("add_recipe_screen")), 5000)

    while (!device.hasObject(By.res("search_ingredient"))) {
        device.findObject(By.res("add_recipe_screen")).scroll(Direction.DOWN, 0.5f)
    }
    device.findObject(By.res("search_ingredient")).click()
    device.wait(Until.hasObject(By.textContains("ser mascarpone")), 5000)
    device.findObject(By.textContains("ser mascarpone")).click()
    Log.i("ExampleStartupBenchmark", "ser mascarpone clicked")
    Log.i("ExampleStartupBenchmark", "addRecipeScreenTest done")
}

fun MacrobenchmarkScope.pressSearch() {
    Log.i("ExampleStartupBenchmark", "pressSearch")
    device.wait(Until.hasObject(By.desc("Search")), 5000)
    device.findObject(By.desc("Search")).click()
    Log.i("ExampleStartupBenchmark", "pressSearch done")
}
fun MacrobenchmarkScope.searchScreenTest(){
    Log.i("ExampleStartupBenchmark", "searchScreenTest")
    device.wait(Until.hasObject(By.desc("Search")), 5000)
    device.findObject(By.desc("Search")).click()

    Thread.sleep(200)
    device.pressEnter()
    Log.i("ExampleStartupBenchmark", "searchScreenTest done")
}