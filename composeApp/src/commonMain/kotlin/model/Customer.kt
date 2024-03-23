package model

data class Customer(
    val id: Long,
    val name: String,
    val email: String,
    val address: String,
    val city: String,
    val phoneNumber: String,
    val gender: String,
    val age: Int
)
