import java.lang.NumberFormatException
import java.text.SimpleDateFormat

fun main() {
    println("== SIMPLE SSG 시작 ==")

    var logonMember: Member? = null

    while (true) {

        val prompt = if (logonMember == null) {
            "명령어) "
        }else{
            "${logonMember.memNick}) "
        }

        print( prompt )
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath) {
            "/system/exit" -> {
                print("프로그램이 종료되었습니다.")
                break
            }
            "/member/join" -> {

                print("아이디: ")
                val memId = readLineTrim()
                //중복

                val isJoinableId = membersrepository.getJoinableId(memId)

                if(isJoinableId){
                    println("이미 존재하는 아이디 입니다.")
                    continue
                }

                print("비밀번호: ")
                val memPw = readLineTrim()
                print("별명: ")
                val memNick = readLineTrim()
                print("이름: ")
                val memName = readLineTrim()
                print("핸드폰번호: ")
                val memPh = readLineTrim()
                print("이메일: ")
                val memEmail = readLineTrim()

                val memberIndex = membersrepository.addMember(memId, memPw, memNick, memName, memPh, memEmail)

                println("${memberIndex}번 째 회원님 환영합니다.")
            }
            "/member/login" -> {

                print("아이디: ")
                val userId = readLineTrim()

                val member = membersrepository.getMemberById(userId)

                if(member == null){
                    println("존재하지 않는 아이디 입니다.")
                    continue
                }
                print("비밀번호: ")
                val userPw = readLineTrim()

                if(member.memPw != userPw){
                    println("비밀번호가 일치하지 않습니다.")
                    continue
                }
                logonMember = member
                println("${member.memNick}님 환영합니다.")
            }
            "/member/logout" ->{
                logonMember = null
                println("로그아웃 되셨습니다.")
            }
            "/article/write" ->{
                /* 로그인이 되었을때만 글을 작성할 수 있음 */
                if(logonMember == null){
                    println("로그인 후 이용하여 주십시오")
                    continue
                }

                print("제목: ")
                val title = readLineTrim()
                print("내용: ")
                val body = readLineTrim()

                val id = articleRepository.addArticle(logonMember!!.memIndex, title, body)

                println("$id 번 게시물이 작성되었습니다.")

            }  // /article/detail?id=1
            "/article/detail" -> {
                /* 로그인이 되었을때만 글을 작성할 수 있음 */
                if (logonMember == null) {
                    println("로그인 후 이용하여 주십시오")
                    continue
                }
                val id = rq.getIntParam("id", 0)
                if (id == 0) {
                    println("아이디를 확인해 주세요.")
                }
                val article = articleRepository.getArticleById(id)

                if (article == null) {
                    println("해당 게시물은 존재하지 않습니다.")
                    continue
                }

                println("번호: ${article.id}")
                println("제목: ${article.title}")
                println("작성일: ${article.regDate}")
                println("수정일: ${article.updateDate}")
            }

        }
    }



    println("== SIMPLE SSG 끝 ==")
}

fun readLineTrim() = readLine()!!.trim()

//request 시작

class Rq(command: String) {
    val actionPath: String
    val paramMap: Map<String, String>

    init {
        val commandBits = command.split("?", limit = 2)
        actionPath = commandBits[0].trim()
        val queryStr = if (commandBits.lastIndex == 1 && commandBits[1].isNotEmpty()) {
            commandBits[1].trim()
        } else {
            ""
        }
        paramMap = if (queryStr.isEmpty()) {
            mapOf()
        } else {
            val paramMapTemp = mutableMapOf<String, String>()
            val queryStrBits = queryStr.split("&")

            for (queryStrBit in queryStrBits) {
                val queryStrBitBits = queryStrBit.split("=", limit = 2)
                val paramName = queryStrBitBits[0]
                val paramValue = if (queryStrBitBits.lastIndex == 1 && queryStrBitBits[1].isNotEmpty()) {
                    queryStrBitBits[1].trim()
                } else {
                    ""
                }
                if (paramValue.isNotEmpty()) {
                    paramMapTemp[paramName] = paramValue
                }
            }
            paramMapTemp.toMap()
        }
    }

    fun getStringParam(name: String, default: String): String {
        return paramMap[name] ?: default
    }

    fun getIntParam(name: String, default: Int): Int {
        return if (paramMap[name] != null) {
            try {
                paramMap[name]!!.toInt()
            } catch (e: NumberFormatException) {
                default
            }
        } else {
            default
        }
    }
}

//request 끝

//member 시작
data class Member(
    val memIndex: Int,
    val memId: String,
    val memPw: String,
    val memNick: String,
    val memName: String,
    val memPh: String,
    val memEmail: String,
    val regDate: String,
    val updateDate: String
)

val lastMember = 0
val members = mutableListOf<Member>()

object membersrepository {
    fun addMember(
        memId: String,
        memPw: String,
        memNick: String,
        memName: String,
        memPh: String,
        memEmail: String
    ): Int {

        val memIndex = lastMember + 1
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        members.add(Member(memIndex, memId, memPw, memNick, memName, memPh, memEmail, regDate, updateDate))

        return memIndex

    }

    fun getMemberById(userId: String): Member? {
        for (member in members) {
            if (member.memId == userId) {
                return member
            }
        }
        return null
    }

    fun getJoinableId(memId: String): Boolean {
        for (member in members){
            if(member.memId == memId ){
                return true
            }
        }
        return false
    }

}
//member 끝

//Util
object Util {
    fun getNowDateStr(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return dateFormat.format(System.currentTimeMillis())
    }
}
//Util

//Article start

data class Article(
    val id: Int,
    val regDate: String,
    val updateDate: String,
    val title: String,
    val body: String,
    val memIndex: Int
)
val articleId = 0
val articles = mutableListOf<Article>()

object articleRepository {
    fun addArticle(memIndex: Int, title: String, body: String): Int {

        val id = articleId + 1
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        articles.add(Article(id, regDate, updateDate, title, body, memIndex))

        return id
    }

    fun getArticleById(id: Int): Article? {
        for (article in articles ){
            if (article.id == id){
                return article
            }
        }
        return null
    }
}


