package navigation

class NavArgument internal constructor(type: NavType<Any>){
    private val type: NavType<Any>

    class Builder {
        private var type: NavType<Any>? = null

        fun <T> setType(type: NavType<T>): Builder {
            this.type = type as NavType<Any>
            return this
        }

        fun build(): NavArgument {
            val finalType = type ?: NavType.StringType as NavType<Any>
            return NavArgument(finalType)
        }
    }

    init {
        this.type = type
    }
}