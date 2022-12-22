package utility

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.PrintStream

fun toFile(filepath: String, block: PrintStream.() -> Unit): Boolean {
    return safe { PrintStream(FileOutputStream(filepath)).block() }
}

fun fromFile(filepath: String, block: (String) -> Unit): Boolean {
    return safe { block(String(FileInputStream(filepath).readAllBytes())) }
}
