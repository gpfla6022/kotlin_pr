fun getFilteredArticles(boardCode: String, searchKeyword: String, page: Int, itemsInAPage: Int): List<Article> {
    //정렬된 게시물 리스트가 나오기 위해서는 정렬을 해줄 필요가 있음
    //사용자가 입력한 기준대로 게시물을 정렬해야함

    val filteredOneArticles = getStringFilteredArticle(articles, boardCode, searchKeyword)
    //필터링을 하기 위해 첫번째로 게시물들이 보관되어 있는 articles를 인자로 줌
    //사용자가 보기를 원하는, 사용자가 입력한 게시판 코드
    //다음으로는 사용자가 검색한 키워드
    //이걸 토대로 필터링을 해야함

    //첫번째 필터링이 끝난 후 10개씩 잘라 사용자에게 넘겨줘야함
    //그거 할 필터링을 만들어야함

    val filteredTwoArticles = getIntFilteredArticle(filteredOneArticles, page, itemsInAPage)

    return filteredTwoArticles

}

private fun getIntFilteredArticle(filteredOneArticles: List<Article>, page: Int, itemsInAPage: Int): List<Article> {
    val filteresArticles = mutableListOf<Article>()

    val offsetCount = (page-1) * itemsInAPage
    val startIndex = filteredOneArticles.lastIndex - offsetCount
    var endIndex = startIndex - (itemsInAPage - 1)

    if(endIndex < 0){
        endIndex = 0
    }

    for(i in startIndex downTo endIndex){
        filteresArticles.add(filteredOneArticles[i])
    }
    return filteresArticles
}

private fun getStringFilteredArticle(
    articles: List<Article>,
    boardCode: String,
    searchKeyword: String
): List<Article> {
    //우선 첫번째로 사용자가 검색어랑 게시판 번호를 둘다 입력하지 않았을 경우가 있음
    //그것을 대비해야함

    if (boardCode.isEmpty() && searchKeyword.isEmpty()) { //&&는 그리고의 의미로 둘다 맞아야지만 실행됨
        return articles // 이렇게 되면 전체게시글이 들어있는 원본 articles가 리턴되고 , 리턴되면 함수가 종료됨.
    }
    val filteredArticles = mutableListOf<Article>()
    //검색어나 게시판 번호가 들어갔을때 원하는 조건대로 필터링해 담아놓을 수 있는
    //비어있는 리스트 하나를 만들어 놓아야 함

    //이제는 본격적으로 필터링을 시작해야함
    //필터링에 앞서 확인해야 할 것이 있음
    //Article객체로는 코드로 게시판 정보를 찾을 수가 없음, 왜?
    //Article객체에는 게시판 번호만 저장되어 있으니까
    //코드로 게시판 인덱스를 찾아 게시판을 찾아와야함

    //그럼 지금 우리가 필요한 것은 boardId를 만들어 담아줄 그릇(변수)을 가져와야함
    // boardCode=
    val boardId = if (boardCode.isEmpty()) {
        0 //boardCode가 입력이 안되었으면 boardCode에 할당하라
    } else { // 그렇지 않으면(boardCode가 입력이 되었으면) boardCode로 해당되는 board객체를 가지고 와야함
        boardRepository.getBoardByBoardCode(boardCode)!!.boardIndex
    }

    //우리가 게시판에 관련된 정보는 얻었음.
    //이제 게시물 리스트에서 해당되는 게시판에 작성된 게시물만 뽑아와야함

    for (article in articles) {
        if (boardId != 0) {
            if (article.boardIndex != boardId) {
                continue // break는 while문을 죽임, continue는 for&if문을 죽임
            }
        }
        //searchKeyword=
        if (searchKeyword.isNotEmpty()) { // searchKeyword=asdf
            if (!article.title.contains(searchKeyword)) { //// searchKeyword=asdf에서 asdf가 없는 것
                continue
            }
        }
        filteredArticles.add(article)
    }
    return filteredArticles
}

fun testArticleMake() {
    for(i in 1..100){
        addArticle(i%2+1, "제목$i", "내용$i" , i%10+1)
    }
}
}
