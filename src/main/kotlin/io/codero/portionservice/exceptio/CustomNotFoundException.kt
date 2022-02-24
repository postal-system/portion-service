package io.codero.portionservice.exceptio

class CustomNotFoundException : Exception {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}
