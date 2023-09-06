package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

interface IMeal {
    fun getId(): Long?
    fun getTitle(): String?
    fun getImage(): String?
    fun getDate(): String?
    fun getType(): String?
    fun getFullMeal(): FullMeal?
}