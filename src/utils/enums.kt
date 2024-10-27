package contacts.utils

enum class MenuActionType {
    ADD, LIST, SEARCH, COUNT, EXIT;
}

enum class SearchActionType {
    BACK, AGAIN; // [number]

    companion object {
        val searchActionTypes by lazy { SearchActionType.values().map { it.name } }
    }
}

enum class ListActionType {
    BACK; // [number]
    companion object {
        val listActionTypes by lazy { SearchActionType.values().map { it.name } }
    }
}

enum class RecordActionType {
    EDIT, DELETE, MENU;
}

enum class ContactField {
    NAME, SURNAME, BIRTH, GENDER, NUMBER;
}

enum class OrganizationField {
    NAME, ADDRESS, NUMBER;
}

enum class EntityType {
    PERSON, ORGANIZATION;
}