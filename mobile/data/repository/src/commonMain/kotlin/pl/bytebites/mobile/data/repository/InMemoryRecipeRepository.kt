package pl.bytebites.mobile.data.repository

import pl.bytebites.mobile.domain.model.Recipe
import pl.bytebites.mobile.domain.repository.RecipeRepository

class InMemoryRecipeRepository : RecipeRepository {

    private val recipes = initialRecipesList

    override suspend fun getRecipes(): Result<List<Recipe>> {
        return Result.success(recipes)
    }

    override suspend fun getRecipeById(id: String): Result<Recipe?> {
        return Result.success(recipes.find { it.id == id })
    }

    companion object {

        private val initialRecipesList = listOf(
            Recipe(
                id = "1",
                title = "Kremowa Zupa Dyniowa z Imbirem",
                prepTime = "30 min",
                difficulty = "Łatwe",
                rating = 4.9,
                servings = 4,
                description = "Aromatyczna, rozgrzewająca zupa krem z pieczonej dyni z dodatkiem świeżego imbiru, mleczka kokosowego i prażonych pestek.",
                ingredients = listOf(
                    "1 kg dyni hokkaido",
                    "400 ml mleczka kokosowego",
                    "1 duża cebula & 2 ząbki czosnku",
                    "1 łyżeczka świeżego startego imbiru",
                    "500 ml bulionu warzywnego",
                    "Pestki dyni i słonecznika do dekoracji"
                ),
                steps = listOf(
                    "Pokrój dynię na kawałki i piecz w 200°C przez 25 minut.",
                    "Zeszklij cebulę, czosnek oraz imbir w garnku na oliwie.",
                    "Dodaj upieczoną dynię, zalej bulionem i gotuj 10 minut.",
                    "Zblenduj całość na gładki krem z mleczkiem kokosowym.",
                    "Podawaj na gorąco z prażonymi pestkami."
                )
            ),
            Recipe(
                id = "2",
                title = "Klasyczna Szakszuka z Fetą",
                prepTime = "20 min",
                difficulty = "Łatwe",
                rating = 4.8,
                servings = 2,
                description = "Tradycyjne danie z jajek zapiekanych w aromatycznym sosie pomidorowym z papryką, kminem rzymskim i serem feta.",
                ingredients = listOf(
                    "3 jajka z wolnego wybiegu",
                    "1 puszka pomidorów krojonych",
                    "1 czerwona papryka",
                    "1/2 cebuli i 1 ząbek czosnku",
                    "50g sera feta",
                    "Kmin rzymski, słodka papryka, świeża kolendra"
                ),
                steps = listOf(
                    "Podsmaż pokrojoną cebulę i paprykę na patelni.",
                    "Dodaj czosnek i przyprawy, podsmażaj przez minutę.",
                    "Wlej pomidory z puszki i dusić sos 10 minut do zgęstnienia.",
                    "Zrób wgłębienia w sosie i wbij jajka.",
                    "Przykryj patelnię i gotuj na wolnym ogniu około 5 minut, aż białka ściągną się.",
                    "Posyp pokruszoną fetą i kolendrą."
                )
            ),
            Recipe(
                id = "3",
                title = "Tarta z Malinami i Mascarpone",
                prepTime = "45 min",
                difficulty = "Średnie",
                rating = 5.0,
                servings = 8,
                description = "Kruche maślane ciasto wypełnione aksamitnym kremem z mascarpone i białą czekoladą, zwieńczone świeżymi malinami.",
                ingredients = listOf(
                    "200g mąki pszennej & 100g zimnego masła",
                    "1 żółtko & 2 łyżki cukru pudru",
                    "250g sera mascarpone",
                    "100g białej czekolady",
                    "200g świeżych malin",
                    "Listki świeżej miety do dekoracji"
                ),
                steps = listOf(
                    "Zagnieć kruche ciasto z mąki, masła, żółtka i cukru. Schłodź w lodówce przez 30 minut.",
                    "Wyłóż formę do tarty i piecz w 180°C przez 20 minut na złoty kolor.",
                    "Rozpuść białą czekoladę i zmiksuj z serem mascarpone na gładką masę.",
                    "Wyłóż krem na ostudzony spód tarty.",
                    "Ułóż gęsto maliny i udekoruj listkami mięty."
                )
            )
        )

    }

}
