package navigation

class NavArgValues(val values: HashMap<String, NamedNavArgument>) {

    fun getInt(name: String): Int? {
        return try {
            if (values[name]?.argument?.type?.name == NavType.IntType.name ) {
                return values[name]?.name?.toInt()
            }
            null
        } catch (e: Exception) { null }
    }

    fun getLong(name: String): Long? {
        return try {
            if (values[name]?.argument?.type?.name == NavType.LongType.name ) {
                return values[name]?.name?.toLong()
            }
            null
        } catch (e: Exception) { null }
    }

    fun getFloat(name: String): Float? {
        return try {
            if (values[name]?.argument?.type?.name == NavType.FloatType.name ) {
                return values[name]?.name?.toFloat()
            }
            null
        } catch (e: Exception) { null }
    }

    fun getString(name: String): String? {
        return values[name]?.name
    }
}