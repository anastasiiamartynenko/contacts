package contacts.repositories

import contacts.entities.Organization
import contacts.utils.OrganizationField

class OrganizationRepository {
    fun edit(organization: Organization, key: String, value: String): Organization {
        when (OrganizationField.valueOf(key.uppercase())) {
            OrganizationField.NAME -> organization.name = value
            OrganizationField.ADDRESS -> organization.address = value
            OrganizationField.NUMBER -> organization.number = value
        }

        organization.updatedAt = ""
        return organization
    }
}