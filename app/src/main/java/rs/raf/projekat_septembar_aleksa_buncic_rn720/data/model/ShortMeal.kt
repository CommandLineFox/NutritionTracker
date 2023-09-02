package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

data class ShortMeal(
    val idMeal: Long,
    val strMeal: String,
    val strMealThumb: String
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
}