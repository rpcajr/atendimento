import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.sql.ResultSet
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*


fun Timestamp?.localTime(): LocalDateTime? {
    return this?.toLocalDateTime()
}

fun Int.zeroNull(): Int? {
    if (this == 0) {
        return null
    }
    return this
}

fun getDias(date: Date?): String {
    if (date == null) {
        return "nunca"
    }
    val diff: Long = Date().time - date.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return (hours / 24).toString()
}

fun getData(date: Date?): String {
    if (date == null) {
        return ""
    }
    return SimpleDateFormat("dd/MM/yyyy").format(date)
}

fun getURLImage(foto: String?): String {
    if (foto.isNullOrEmpty()) {
        return ""
    }
    return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/file/")
            .path(foto)
            .toUriString()
}

fun ResultSet.getListString(campo: String): List<String> {
    val array = this.getArray(campo) ?: return emptyList()

    val c = array.array
    if (c is Array<*>) {
        return c.toList() as List<String>
    }
    return emptyList()
}






