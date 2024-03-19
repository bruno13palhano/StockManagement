package navigation

abstract class NavType<T> {
    open val name: String = "nav_type"
    abstract fun get()
    abstract fun put()

    companion object {

        fun fromArgType(type: String): NavType<*> {
            return when {
                IntType.name == type -> IntType
                LongType.name == type -> LongType
                FloatType.name == type -> FloatType
                else -> StringType
            }
        }

        val IntType: NavType<Int> = object : NavType<Int>() {
            override val name: String
                get() = "integer"

            override fun get() {
                TODO("Not yet implemented")
            }

            override fun put() {
                TODO("Not yet implemented")
            }
        }

        val LongType: NavType<Long> = object : NavType<Long>() {
            override val name: String
                get() = "long"

            override fun get() {
                TODO("Not yet implemented")
            }

            override fun put() {
                TODO("Not yet implemented")
            }
        }

        val FloatType: NavType<Float> = object : NavType<Float>() {
            override val name: String
                get() = "float"

            override fun get() {
                TODO("Not yet implemented")
            }

            override fun put() {
                TODO("Not yet implemented")
            }
        }

        val StringType: NavType<String> = object : NavType<String>() {
            override val name: String
                get() = "string"

            override fun get() {
                TODO("Not yet implemented")
            }

            override fun put() {
                TODO("Not yet implemented")
            }
        }
    }
}