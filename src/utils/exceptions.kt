package contacts.utils

class BackToMenuException(): Exception()

fun throwBackToMenuException(): Nothing {
    throw BackToMenuException()
}
