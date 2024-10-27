package contacts.controllers

import contacts.entities.Contact
import contacts.repositories.Repository

class ContactController(private val repository: Repository) {
    fun add() {
        print("Enter the name: ")
        val name = readln()
        print("Enter the surname: ")
        val surname = readln()
        val contact = Contact(name, surname)

        print("Enter the birth date: ")
        val birthday = readln()
        contact.birthday = birthday

        print("Enter the gender (M, F): ")
        val gender = readln()
        contact.gender = gender

        print("Enter the number: ")
        val number = readln()
        contact.number = number

        repository.add(contact)
    }

    fun edit(index: Int) {
        print("Select a field (name, surname, birth, gender, number): ")
        val field = readln()
        print("Enter $field: ")
        val value = readln()

        repository.edit(index, field, value)
    }
}