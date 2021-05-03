class ArticleController {
    fun write() {

        if(logonMember == null) {
            println("로그인을 해주세요.")
        }

        print("제목: ")
        val title = readLineTrim()
        print("내용: ")
        val body = readLineTrim()

        val memIndex = logonMember!!.memIndex

        val articleIndex = articleRepository.addArticle(memIndex, title, body)

        println("$articleIndex 번 게시물이 생성되었습니다.")
    }

    fun delete(rq: Rq) {

        if(logonMember == null) {
            println("로그인을 해주세요.")
        }

        val id = rq.getIntParam("id", 0)

        val article = articleRepository.getArticleById(id)

        if( article == null){
            println("해당 게시물은 존재하지 않습니다.")
            return
        }

        if (article.memberId != logonMember!!.memIndex){
            println("권한이 없습니다")
            return
        }

        articleRepository.deleteArticle(article)

        println("삭제 되었습니다.")

    }

    fun modify(rq: Rq) {

        if(logonMember == null) {
            println("로그인을 해주세요.")
        }
        val id = rq.getIntParam("id", 0)

        val article = articleRepository.getArticleById(id)

        if( article == null){
            println("해당 게시물은 존재하지 않습니다.")
            return
        }

        if (article.memberId != logonMember!!.memIndex){
            println("권한이 없습니다")
            return
        }
        print("새 제목: ")
        article.title = readLineTrim()
        print("새 내용: ")
        article.body = readLineTrim()

        println("$id 번 게시물이 수정되었습니다.")
    }

    fun detail(rq: Rq) {

        if(logonMember == null) {
            println("로그인을 해주세요.")
        }
        val id = rq.getIntParam("id", 0)

        val article = articleRepository.getArticleById(id)

        if( article == null){
            println("해당 게시물은 존재하지 않습니다.")
            return
        }
        println("$id 번 게시물의 번호: ${article.id}")
        println("$id 번 게시물의 제목: ${article.title}")
        println("$id 번 게시물의 내용: ${article.body}")
        println("$id 번 게시물의 작성일: ${article.regDate}")
        println("$id 번 게시물의 갱신일: ${article.updateDate}")

    }

    fun list(rq: Rq) {

        if(logonMember == null) {
            println("로그인을 해주세요.")
        }

        val searchKeywords = rq.getStringParam("searchKeyword", "")
        val page = rq.getIntParam("page", 0)

        if(page ==0){
            println("해당 페이지가 존재하지 않습니다.")
            return
        }

        val filteredArticle = articleRepository.getFilteredArticle(searchKeywords, page, 10)

        println("번호 / 제목 / 작성일 / 수정일")

        for (article in filteredArticle){
            println("${article.id} / ${article.title} / ${article.regDate} / ${article.updateDate}")
        }
    }


}

