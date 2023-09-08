package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

interface IFavorite {
    fun getImage(): String?
    fun getName(): String
    fun getTimesUsed(): Int
    fun getPercentage(): Double?
}