package contacts.entities

import contacts.utils.isBirthdayValid
import contacts.utils.isGenderValid

data class Contact (
    @Transient
    override var _name: String = "",
    @Transient
    private var _surname: String = "",
    @Transient
    private var _birthday: String = "",
    @Transient
    private var _gender: String = "",
    @Transient
    override var _number: String = "",
): EntityBase() {
    override val type = "person"
    var surname: String
        get() = _surname
        set(value) { _surname = value }
    var birthday: String
        get() = _birthday
        set(value) {
            _birthday = if (isBirthdayValid(value)) {
                value
            } else {
                println("Bad birth date!")
                ""
            }
        }
    var gender: String
        get() = _gender
        set(value) {
            _gender = if (isGenderValid(value.trim().uppercase())) {
                value.trim().uppercase()
            } else {
                println("Bad gender!")
                ""
            }
        }

    override fun toString(): String {
        return  "Name: $name\n" +
                "Surname: $surname\n" +
                "Birth date: ${if (birthday.isNotEmpty()) birthday else "[no data]"}\n" +
                "Gender: ${if (gender.isNotEmpty()) gender else "[no data]"}\n" +
                "Number: ${if (hasNumber()) number else "[no number]"}\n" +
                "Time created: $createdAt\n" +
                "Time last edit: $updatedAt"
    }

    fun isBirthdayValid(value: String = ""): Boolean = value.isBirthdayValid()
    fun isGenderValid(value: String = ""): Boolean = value.isGenderValid()
}
