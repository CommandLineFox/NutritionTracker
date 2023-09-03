package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

data class Ingredient(
    val idIngredient: String,
    val strDescription: String?,
    val strIngredient: String,
    val strType: String?
) : IFilter {
    override fun getTitle(): String {
        return strIngredient
    }
}