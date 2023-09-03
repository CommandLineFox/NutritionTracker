package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

data class Area(
    val strArea: String
) : IFilter {
    override fun getTitle(): String {
        return strArea
    }
}