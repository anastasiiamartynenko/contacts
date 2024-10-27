package contacts.repositories

import contacts.entities.Contact
import contacts.entities.EntityBase
import contacts.entities.Organization
import contacts.utils.EntityType

class Repository(val allRecords: MutableList<EntityBase>) {
    private val contactRepository = ContactRepository()
    private val organizationRepository = OrganizationRepository()

    fun add(entry: EntityBase) {
        allRecords.add(entry)
    }

    fun search(query: String): List<EntityBase> = allRecords.filter {
        it.name.lowercase().contains(query.lowercase())  || it.number.lowercase().contains(query.lowercase()) || (it is Contact && it.surname.lowercase().contains(query.lowercase()))
    }

    fun remove(index: Int) {
        if (index >= allRecords.size)
            throw Exception("Index out of range: [0, ${allRecords.size - 1}]")

        allRecords.removeAt(index)
    }

    fun edit(index: Int, key: String, value: String) {
        var record = allRecords[index]

        when (record) {
            is Contact -> { record = contactRepository.edit(record, key, value) }
            is Organization -> { record = organizationRepository.edit(record, key, value) }
        }

        allRecords[index] = record
    }

    fun hasRecords(): Boolean = allRecords.isNotEmpty()

    fun list(data: List<EntityBase>? = null): String {
        val records = if (data != null) data else allRecords
        var result = ""

        for ((index, record) in records.withIndex()) {
            when (record) {
                is Contact -> {
                    result += "${index + 1}. ${record.name} ${record.surname}"
                }
                is Organization -> {
                    result += "${index + 1}. ${record.name}"
                }
            }
            if (index != allRecords.size - 1) {
                result += "\n"
            }
        }

        return result
    }

    fun getDbIndex(entity: EntityBase): Int = allRecords.indexOf(entity)

    fun info(index: Int): String = allRecords[index].toString()

    fun count() = allRecords.size

    fun getEntityTypeByIndex(index: Int): EntityType {
        val record = allRecords[index]


        return when (record) {
            is Organization -> EntityType.ORGANIZATION
            else -> EntityType.PERSON
        }
    }
}
