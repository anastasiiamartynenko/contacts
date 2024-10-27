package contacts.repositories

import contacts.entities.Contact
import contacts.utils.ContactField

class ContactRepository {
    fun edit(contact: Contact, key: String, value: String): Contact {
        when (ContactField.valueOf(key.uppercase())) {
            ContactField.NAME -> contact.name = value
            ContactField.SURNAME -> contact.surname = value
            ContactField.BIRTH -> contact.birthday = value
            ContactField.GENDER -> contact.gender = value
            ContactField.NUMBER -> contact.number = value
        }

        contact.updatedAt = ""
        return contact
    }
}