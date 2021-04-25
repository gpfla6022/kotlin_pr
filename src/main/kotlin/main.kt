// !! 코틀린 텍스트 게시판 복습 !!
import java.text.SimpleDateFormat

fun main() {

    println(" ==  게시판 관리 프로그램 시작 == ")

    testArticleMake()

    while( true ) {

        print("명령어)")
        val command = readLineTrim()

        when{
            command == "system exit" ->{
                println(" 프로그램을 종료합니다. ")
                break
            }

            command == "article write" ->{

                print("제목 : ")
                val title = readLineTrim()
                print("내용 : ")
                val body = readLineTrim()

                val id = addArticle(title, body)

                println("$id 번 게시물이 생성 되었습니다.")
            }

            command.startsWith("article list ") ->{

                val commandBits = command.trim().split(" ")

                var page = 1
                var searchKeyword = ""

                // article list 검색어 1
                // article list 1

                if (commandBits.size == 4){
                    searchKeyword = commandBits[2]
                    page = commandBits[3].toInt()
                } else if (commandBits.size == 3){
                    page = commandBits[2].toInt()
                }

                val itemsInAPage = 5

                val offsetCount = (page - 1) * itemsInAPage

                val filteredArticles = getFilteredArticle(searchKeyword, offsetCount, itemsInAPage)


                println("  번호  /  작성일  /  제목  ")

                for (article in filteredArticles){
                    println("${article.id}  /  ${article.regDate}  /  ${article.title}")

                }
            }

            command.startsWith("article delete ")->{

                val id = command.trim().split(" ")[2].toInt()

                val delArticle = getArticleById(id)

                if ( delArticle == null ){
                    println("해당 게시물은 존재하지 않습니다. ")
                    continue
                }

                articles.remove(delArticle)

                println("$id 번 게시물은 삭제되었습니다.")
            }

            command.startsWith("article modify ")-> {

                val id = command.trim().split(" ")[2].toInt()

                val modArticle = getArticleById(id)

                if (modArticle == null) {
                    println("해당 게시물은 존재하지 않습니다. ")
                    continue
                }

                print("새 제목 : ")
                modArticle.title = readLineTrim()
                print("새 내용 : ")
                modArticle.body = readLineTrim()
                modArticle.updateDate = Util.getNowDateStr()

                println("$id 번 게시물이 수정되었습니다.")

            }

            command.startsWith("article detail ")-> {

                val id = command.trim().split(" ")[2].toInt()

                val detArticle = getArticleById(id)

                if (detArticle == null) {
                    println("해당 게시물은 존재하지 않습니다. ")
                    continue
                }

                println("$id 번 게시물 : ${detArticle.id}")
                println("$id 번 게시물 제목 : ${detArticle.title} ")
                println("$id 번 게시물 내용 : ${detArticle.body} ")
                println("$id 번 게시물 작성일 : ${detArticle.regDate} ")
                println("$id 번 게시물 갱신일 : ${detArticle.updateDate} ")

            }



        }
    }


    println(" ==  게시판 관리 프로그램 끝 == ")

}

//게시판 관리

fun readLineTrim() = readLine()!!.trim()

var lastArticleId = 0

val articles = mutableListOf<Article>()

data class Article(
    val id:Int,
    val regDate:String,
    var updateDate:String,
    var title: String,
    var body:String
)

fun getArticleById(id:Int): Article?{

    for(article in articles){
        if(article.id == id){
            return article
        }
    }

    return null
}

fun addArticle(title:String, body:String):Int{
    val id = lastArticleId + 1

    val regDate = Util.getNowDateStr()
    val updateDate = Util.getNowDateStr()

    val article = Article(id, regDate, updateDate, title, body)

    articles.add(article)

    lastArticleId = id

    return id
}

fun testArticleMake(){

    for ( i in 1 .. 1000){
        val title = "제목_$i"
        val body = "내용_$i"

        addArticle(title, body)
    }
}

fun getFilteredArticle(searchKeyword:String, offsetCount:Int, ItemsInAPage:Int): List<Article> {

    var originArticles = articles

    if (searchKeyword.isNotEmpty()){
        originArticles = mutableListOf()

        for( article in articles){
            if(article.title.contains(searchKeyword)){
                originArticles.add(article)
            }
        }
    }

    val startIndex =  originArticles.lastIndex - offsetCount
    var endIndex = startIndex - ItemsInAPage + 1

    if (endIndex < 0 ){
        endIndex = 0
    }

    val divideArticles = mutableListOf<Article>()

    for (i in startIndex downTo endIndex){
        divideArticles.add(originArticles[i])
    }

    return divideArticles
}


// 유틸기능

object Util{
    fun getNowDateStr():String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return dateFormat.format(System.currentTimeMillis())
    }

}