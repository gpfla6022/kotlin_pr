import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File

class ArticleRepository {

    var articles = mutableListOf<Article>()

    fun addArticle(boardIndex: Int, title: String, body: String, memIndex: Int): Int {

        var lastArticleIndex = (articles.lastIndex) + 1

        val id = ++lastArticleIndex

        val regDate = util.getNowDateStr()
        val updateDate = util.getNowDateStr()

        articles.add(Article(id, title, body, memIndex, boardIndex, regDate, updateDate))

        return id

    }

    fun getArticleByIndex(id: Int): Article? {

        for ( article in articles ) {
            if ( article.id == id ) {
                return article
            }
        }
        return null
    }

    fun delArticle(article: Article) {
        articles.remove(article)
        // 게시물삭제하면 해당 제이슨 파일을 제거하는 기능 구현
        File("data/Article/ArticlesData/${article.id}.json").delete()

    }

    fun modArticle(id: Int, title: String, body: String) {

        val article = getArticleByIndex(id)

        article!!.title = title
        article.body = body
        article.updateDate = util.getNowDateStr()

    }

    fun getFilteredArticles(boardCode: String, searchKeyword: String, page: Int, itemsInAPage: Int): List<Article> {

        val filtered1Articles = getCodeKeywordFilteredArticles(articles, boardCode,searchKeyword)
        val filtered2Articles = getPageFilteredArticles(filtered1Articles, page, itemsInAPage)

        return filtered2Articles

    }

    private fun getPageFilteredArticles(filtered1Articles: List<Article>, page: Int, itemsInAPage: Int): List<Article> {

        val filteredArticles = mutableListOf<Article>()

        val offsetCount = ( page - 1 ) * itemsInAPage
        val startIndex = filtered1Articles.lastIndex - offsetCount

        var endIndex = startIndex - ( page - 1)

        if( endIndex < 0 ) {
            endIndex = 0
        }

        for ( i in startIndex downTo endIndex ) {
            filteredArticles.add(filtered1Articles[i])
        }

        return filteredArticles

    }

    private fun getCodeKeywordFilteredArticles(articles: MutableList<Article>, boardCode: String, searchKeyword: String): List<Article> {

        if ( boardCode.isEmpty() && searchKeyword.isEmpty() ) {
            return articles
        }

        val filteredArticles = mutableListOf<Article>()

        val boardIndex = if ( boardCode.isEmpty() ) {
            0
        } else {
            boardRepository.geBoardByBoardCode(boardCode)!!.boardIndex
        }

        for ( article in articles ) {
            if ( boardIndex != 0 ) {
                if ( article.boardIndex != boardIndex){
                    continue
                }
            }

            if ( searchKeyword.isNotEmpty() ) {
                if(!article.title.contains(searchKeyword)) {
                    continue
                }
            }

            filteredArticles.add(article)
        }

        return filteredArticles

    }

    @JvmName("getArticles1")
    fun getArticles(): MutableList<Article> {
        return articles
    }

    fun loadArticleData() {

        val size = File("data/Article/ArticlesData").list().size

        println("Article Data load start !")


        // 게시물 삭제를 하면 로딩을 못하는 문제를 해결

        for ( i in 1 .. size ){

            if ( !File("data/Article/ArticlesData/${i}.json").exists() ) {
                continue
            }

            articles.add(mapper.readValue<Article>(File("data/Article/ArticlesData/${i}.json")))
        }

        println("Article Data load Complete !")

    }


}