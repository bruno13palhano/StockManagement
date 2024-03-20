package navigation

class NavBackStackEntry(
    val navStack: ArrayDeque<String>,
    val destinationsStack: MutableList<Destination>,
    val argsValues: HashMap<String, String>
) {

    fun getInt(name: String): Int? {
        return try { argsValues[name]?.toInt() } catch (e: Exception) { null }
    }

    fun getLong(name: String): Long? {
        return try { argsValues[name]?.toLong() } catch (e: Exception) { null }
    }

    fun getFloat(name: String): Float? {
        return try { argsValues[name]?.toFloat() } catch (e: Exception) { null }
    }

    fun getString(name: String): String? {
        return argsValues[name]
    }
}