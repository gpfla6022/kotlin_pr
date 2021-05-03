class ArticleRepository {

    var lastId = 0
    private val articles = mutableListOf<Article>()

    fun addArticle(memIndex: Int, title: String, body: String): Int {
        val articleIndex = ++ lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        articles.add(Article(memIndex, title, body, regDate, updateDate, memIndex))

        return articleIndex
    }

    fun getArticleById(id: Int): Article? {

        for ( article in articles ) {
            if ( article.id == id ) {
                return article
            }
        }
        return null
    }

    fun deleteArticle(article: Article) {
        articles.remove(article)
    }

    fun getFilteredArticle(searchKeywords: String, page: Int, itemsInAPage: Int): List<Article> {

        val filteredOneArticle = getStringArticle(articles ,searchKeywords)
        val filteredTwoArticle = getIntArticle(filteredOneArticle, page, itemsInAPage)

        return filteredTwoArticle
    }

    private fun getStringArticle(articles: List<Article>, searchKeywords: String): List<Article> {
        val filteredArticle = mutableListOf<Article>()

        for(article in articles){
            if(article.title.contains(searchKeywords)){
                filteredArticle.add(article)
            }
        }
        return filteredArticle

    }

    private fun getIntArticle(filteredOneArticle: List<Article>, page: Int, itemsInAPage: Int): List<Article> {
        val filteredArticle = mutableListOf<Article>()

        val offsetCount = (page - 1) * itemsInAPage
        val startIndex = filteredOneArticle.lastIndex - offsetCount
        var endIndex = startIndex - (itemsInAPage - 1)

        if(endIndex < 0){
            endIndex = 0
        }

        for( i in startIndex downTo endIndex){
            filteredArticle.add(filteredOneArticle[i])
        }
        return filteredArticle

    }



}