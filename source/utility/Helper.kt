package utility

fun safe(block: () -> Unit): Boolean {
    return try { block(); true } catch(_: Throwable) { false }
}
