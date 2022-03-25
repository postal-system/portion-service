package io.codero.portionservice.exception

class CustomNotFoundException : Exception {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}
