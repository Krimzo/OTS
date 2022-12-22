package utility

fun safe(block: () -> Unit): Boolean {
    return try { block(); true } catch(ignored: Throwable) { false }
}

fun <T> T.use(block: (T) -> Unit): T {
    block(this)
    return this
}
