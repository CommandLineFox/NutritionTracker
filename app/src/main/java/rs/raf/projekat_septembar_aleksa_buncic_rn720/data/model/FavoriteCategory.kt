package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository

data class FavoriteCategory(var strCategory: String) : IFavorite {
    var uses = 0

    override fun equals(other: Any?): Boolean {
        if (!(other is FavoriteCategory)) {
            return false
        }

        return strCategory == other.strCategory
    }

    override fun getImage(): String? {
        return null
    }

    override fun getName(): String {
        return strCategory
    }

    override fun getTimesUsed(): Int {
        return uses
    }

    override fun getPercentage(): Double? {
        if (Repository.getInstance().databaseMeals.isEmpty()) {
            return null
        }

        return uses.toDouble() / Repository.getInstance().databaseMeals.size
    }
}