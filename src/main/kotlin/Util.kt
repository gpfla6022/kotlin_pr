import java.text.SimpleDateFormat
fun readLineTrim() = readLine()!!.trim()



object Util {
    fun getNowDateStr(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return dateFormat.format(System.currentTimeMillis())
    }
}