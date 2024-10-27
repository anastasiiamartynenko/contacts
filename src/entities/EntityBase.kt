package contacts.entities

import contacts.utils.numberRegex
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

abstract class EntityBase() {
    abstract val type: String
    abstract var _name: String
    abstract var _number: String

    @Transient
    protected val createdAtRaw = LocalDateTime.now()
    @Transient
    protected var updatedAtRaw = LocalDateTime.now()
    @Transient
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

    protected val createdAt = formatter.format(createdAtRaw)
    var updatedAt: String
        get() = formatter.format(updatedAtRaw)
        set(value) {
            updatedAtRaw = LocalDateTime.now()
        }

    var name: String
        get() = _name
        set(value) { _name = value }

    var number: String
        get() = _number
        set(value) {
            _number = if (isNumberValid(value)) {
                value
            } else {
                println("Wrong number format!")
                ""
            }
        }

    protected fun hasNumber(): Boolean = !number.isBlank()

    protected fun isNumberValid(value: String = ""): Boolean {
        return numberRegex.matches(if (value.isNotEmpty()) value else number)
    }
}
