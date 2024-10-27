package contacts.controllers

import contacts.entities.Organization
import contacts.repositories.Repository

class OrganizationController(private val repository: Repository) {
    fun add() {
        print("Enter the organization name: ")
        val name = readln()
        print("Enter the address: ")
        val address = readln()
        print("Enter the number: ")
        val number = readln()


        val organization = Organization(name, address, "")
        organization.number = number

        repository.add(organization)
    }

    fun edit(index: Int) {
        print("Select a field (name, address, number): ")
        val field = readln()
        print("Enter $field: ")
        val value = readln()

        repository.edit(index, field, value)
    }
}
