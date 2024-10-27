package contacts.utils

val numberRegex = "^[+]?([\\da-zA-Z]{1,}|[(][\\da-zA-Z]{1,}[)])(?:[\\s-]([\\da-zA-Z]{2,}|[(][\\da-zA-Z]{2,}[)]))?(?:[\\s-][\\da-zA-Z]{2,})*".toRegex()
var birthdayRegex = "^\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$".toRegex()
val genderRegex = "^[MF]$".toRegex()

fun String.isGenderValid(): Boolean = genderRegex.matches(this)
fun String.isBirthdayValid(): Boolean = birthdayRegex.matches(this)
