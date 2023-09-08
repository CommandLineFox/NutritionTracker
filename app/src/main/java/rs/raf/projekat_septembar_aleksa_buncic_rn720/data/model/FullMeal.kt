package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

data class FullMeal(
    val dateModified: String?,
    val idMeal: Long,
    val strArea: String?,
    val strCategory: String?,
    val strCreativeCommonsConfirmed: String?,
    val strDrinkAlternate: String?,
    val strImageSource: String?,
    val strIngredient1: String?,
    val strIngredient10: String?,
    val strIngredient11: String?,
    val strIngredient12: String?,
    val strIngredient13: String?,
    val strIngredient14: String?,
    val strIngredient15: String?,
    val strIngredient16: String?,
    val strIngredient17: String?,
    val strIngredient18: String?,
    val strIngredient19: String?,
    val strIngredient2: String?,
    val strIngredient20: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strInstructions: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val strMeasure1: String?,
    val strMeasure10: String?,
    val strMeasure11: String?,
    val strMeasure12: String?,
    val strMeasure13: String?,
    val strMeasure14: String?,
    val strMeasure15: String?,
    val strMeasure16: String?,
    val strMeasure17: String?,
    val strMeasure18: String?,
    val strMeasure19: String?,
    val strMeasure2: String?,
    val strMeasure20: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    val strMeasure6: String?,
    val strMeasure7: String?,
    val strMeasure8: String?,
    val strMeasure9: String?,
    val strSource: String?,
    val strTags: String?,
    val strYoutube: String?
) : IMeal {
    override fun getId(): Long? {
        return idMeal
    }

    override fun getTitle(): String? {
        return strMeal
    }

    override fun getImage(): String? {
        return strMealThumb
    }

    override fun getDate(): String? {
        return null
    }

    override fun getType(): String? {
        return null
    }

    override fun getFullMeal(): FullMeal? {
        return this@FullMeal
    }

    override fun toString(): String {
        var meal = ""
        meal += "Title: $strMeal\n"
        meal += "Category: $strCategory\n"
        meal += "Area: $strArea\n"
        if (!strTags.isNullOrEmpty()) {
            meal += "Tags: $strTags\n"
        }

        meal += "Ingredients:\n"
        if (!strIngredient1.isNullOrEmpty()) {
            meal += "$strIngredient1 $strMeasure1\n"
        }
        if (!strIngredient2.isNullOrEmpty()) {
            meal += "$strIngredient2 $strMeasure2\n"
        }
        if (!strIngredient3.isNullOrEmpty()) {
            meal += "$strIngredient3 $strMeasure3\n"
        }
        if (!strIngredient4.isNullOrEmpty()) {
            meal += "$strIngredient4 $strMeasure4\n"
        }
        if (!strIngredient5.isNullOrEmpty()) {
            meal += "$strIngredient5 $strMeasure5\n"
        }
        if (!strIngredient6.isNullOrEmpty()) {
            meal += "$strIngredient6 $strMeasure6\n"
        }
        if (!strIngredient7.isNullOrEmpty()) {
            meal += "$strIngredient7 $strMeasure7\n"
        }
        if (!strIngredient8.isNullOrEmpty()) {
            meal += "$strIngredient8 $strMeasure8\n"
        }
        if (!strIngredient9.isNullOrEmpty()) {
            meal += "$strIngredient9 $strMeasure9\n"
        }
        if (!strIngredient10.isNullOrEmpty()) {
            meal += "$strIngredient10 $strMeasure10\n"
        }
        if (!strIngredient11.isNullOrEmpty()) {
            meal += "$strIngredient11 $strMeasure11\n"
        }
        if (!strIngredient12.isNullOrEmpty()) {
            meal += "$strIngredient12 $strMeasure12\n"
        }
        if (!strIngredient13.isNullOrEmpty()) {
            meal += "$strIngredient13 $strMeasure13\n"
        }
        if (!strIngredient14.isNullOrEmpty()) {
            meal += "$strIngredient14 $strMeasure14\n"
        }
        if (!strIngredient15.isNullOrEmpty()) {
            meal += "$strIngredient15 $strMeasure15\n"
        }
        if (!strIngredient16.isNullOrEmpty()) {
            meal += "$strIngredient16 $strMeasure16\n"
        }
        if (!strIngredient17.isNullOrEmpty()) {
            meal += "$strIngredient17 $strMeasure17\n"
        }
        if (!strIngredient18.isNullOrEmpty()) {
            meal += "$strIngredient18 $strMeasure18\n"
        }
        if (!strIngredient19.isNullOrEmpty()) {
            meal += "$strIngredient19 $strMeasure19\n"
        }
        if (!strIngredient20.isNullOrEmpty()) {
            meal += "$strIngredient20 $strMeasure20\n"
        }
        if (!strSource.isNullOrEmpty()) {
            meal += "Source: $strSource\n"
        }
        if (!strYoutube.isNullOrEmpty()) {
            meal += "Youtube: $strYoutube\n"
        }

        return meal
    }
}