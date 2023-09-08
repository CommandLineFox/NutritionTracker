package rs.raf.projekat_septembar_aleksa_buncic_rn720.data.model

import rs.raf.projekat_septembar_aleksa_buncic_rn720.data.Repository

class FavoriteMeal(var meal: IMeal) : IFavorite {
    var uses = 0

    override fun equals(other: Any?): Boolean {
        if (!(other is IMeal)) {
            return false
        }

        return meal.getId() == other.getId()
    }

    override fun getImage(): String? {
        return meal.getImage()
    }

    override fun getName(): String {
        return meal.getTitle()!!
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