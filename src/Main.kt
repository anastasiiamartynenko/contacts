package contacts

import contacts.controllers.Controller
import contacts.db.ConnectionManager
import contacts.utils.MenuActionType
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val connectionManager = ConnectionManager(if (args != null && args.size != 0) args[0] else null)

    val controller = Controller(connectionManager)

    var counter = 0
    while (true) {
        if (counter != 0) {
            println()
        }

        counter++
        print("[menu] Enter action (add, list, search, count, exit): ")
        val actionType = readln().trim().uppercase()

        try {
            MenuActionType.valueOf(actionType)
        } catch (e: Exception) {
            println("Invalid action or entity type").also { exitProcess(1)}
        }

        controller.performAction(actionType)
    }
}