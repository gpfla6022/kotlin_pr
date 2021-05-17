import java.text.SimpleDateFormat

// 함수로 만들 수 있지만 다른 파일에서도 자주 쓰일 가능성이 있는 함수들은
// 다음과 같이 object 안에 모아서 구현해놓으면 쓰기 편리하다.
object Util {
    fun getNowDateStr(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(System.currentTimeMillis())
    }
}