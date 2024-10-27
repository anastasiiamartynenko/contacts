package contacts.controllers

import contacts.db.ConnectionManager
import contacts.entities.EntityBase
import contacts.repositories.Repository
import contacts.utils.BackToMenuException
import contacts.utils.EntityType
import contacts.utils.ListActionType.Companion.listActionTypes
import contacts.utils.RecordActionType
import contacts.utils.MenuActionType
import contacts.utils.SearchActionType
import contacts.utils.SearchActionType.Companion.searchActionTypes
import contacts.utils.throwBackToMenuException
import kotlin.system.exitProcess

class Controller(private val connectionManager: ConnectionManager) {
    private val repository = Repository(connectionManager.load())

    private val contactController = ContactController(repository)
    private val organizationController = OrganizationController(repository)

    private fun add() {
        print("Enter the type (person, organization): ")
        val entityType = readln()

        when (EntityType.valueOf(entityType.trim().uppercase())) {
            EntityType.PERSON -> { contactController.add() }
            EntityType.ORGANIZATION -> { organizationController.add() }
        }
        println("The record added.")

        connectionManager.persist(repository.allRecords)
        println("Saved.")
    }

    private fun list() {
        println(repository.list())

        while (true) {
            println()
            print("[list] Enter action ([number], back): ")
            val option = readln().trim().uppercase()

            if (listActionTypes.contains(option)) {
                return
            } else {
                val index = option.toInt()
                loadRecord(index - 1)
            }
        }
    }
    private fun list(data: List<EntityBase>): String = repository.list(data)

    private fun info(index: Int): String = repository.info(index)

    private fun loadRecord(index: Int) {
        println(info(index))

        while (true) {
            println()
            print("[record] Enter action (edit, delete, menu): ")
            val option = readln().trim().uppercase()

            when (RecordActionType.valueOf(option)) {
                RecordActionType.EDIT -> { edit(index) }
                RecordActionType.DELETE -> { remove(index) }
                RecordActionType.MENU -> {
                    throwBackToMenuException()
                }
            }
        }
    }

    private fun search() {
        print("Enter search query: ")
        val query = readln()

        val foundRecords = repository.search(query)
        println("Found ${foundRecords.size} results:")
        println(list(foundRecords))

        while (true) {
            print("[search] Enter action ([number], back, again): ")
            val option = readln().trim().uppercase()

            if (searchActionTypes.contains(option)) {
                when (SearchActionType.valueOf(option)) {
                    SearchActionType.BACK -> { return }
                    SearchActionType.AGAIN -> {
                        search()
                    }
                }
            } else {
                val index = option.toInt()
                val dbIndex = repository.getDbIndex(foundRecords[index - 1])
                loadRecord(dbIndex)
            }
        }
    }

    private fun remove(index: Int) {
        if (!repository.hasRecords())
            println("No records to remove!")
        else {
            repository.remove(index - 1)
            println("The record removed!")
        }

        connectionManager.persist(repository.allRecords)
        println("Saved.")
    }

    private fun edit(index: Int) {
        if (!repository.hasRecords())
            println("No records to edit!")
        else {
            when (repository.getEntityTypeByIndex(index)) {
                EntityType.PERSON -> {
                    contactController.edit(index)
                }

                EntityType.ORGANIZATION -> {
                    organizationController.edit(index)
                }
            }
        }

        connectionManager.persist(repository.allRecords)
        println("Saved")
        println(info(index))
    }

    private fun count() {
        println("The Phone Book has ${repository.count()} records.")
    }

    fun performAction(actionType: String) {
        try {
            when (MenuActionType.valueOf(actionType.trim().uppercase())) {
                MenuActionType.ADD -> { add() }
                MenuActionType.LIST -> { list() }
                MenuActionType.SEARCH -> { search() }
                MenuActionType.COUNT -> { count() }
                MenuActionType.EXIT -> {
                    connectionManager.closeConnection()
                    exitProcess(0)
                }
            }
        } catch (e: BackToMenuException) {
            return
        }
    }
}