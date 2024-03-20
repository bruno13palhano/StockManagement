package navigation

abstract class NavType<T> {
    open val name: String = "nav_type"

    companion object {

        val IntType: NavType<Int> = object : NavType<Int>() {
            override val name: String
                get() = "integer"
        }

        val LongType: NavType<Long> = object : NavType<Long>() {
            override val name: String
                get() = "long"
        }

        val FloatType: NavType<Float> = object : NavType<Float>() {
            override val name: String
                get() = "float"
        }

        val StringType: NavType<String> = object : NavType<String>() {
            override val name: String
                get() = "string"
        }
    }
}