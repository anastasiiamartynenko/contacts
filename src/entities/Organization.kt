package contacts.entities

data class Organization(
    @Transient
    override var _name: String = "",
    @Transient
    private var _address: String = "",
    @Transient
    override var _number: String = "",
): EntityBase() {
    override val type = "organization"
    var address: String
        get() = _address
        set(value) { _address = value }

    override fun toString(): String {
        return "Organization name: $name\nAddress: $address\nNumber: $number\nTime created: $createdAt\nTime last edit: $updatedAt"
    }
}
