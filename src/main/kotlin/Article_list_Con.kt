fun list(rq: Rq) {
    if (logonMember == null) {
        println("로그인을 해주세요")
        return
    }
    // 고객이 입력하는 query
    // /article/list?boardCode=Notice&searchKeyword=제목&page=1
    //얻어야 하는 정보
    // 1. boardCode : 게시판을 찾기 위함
    // 2. searchKeyword: 제목에 해당되는 말이 있는지 찾기 위함
    // 3. page: 게시글이 많을때 나눠 보여주기 위함

    // 변수에다가 각각의 값을 저장해서 메소드로 필터링을 하면 깔끔하게 저장되어 출력됨.

    val boardCode = rq.getStringParam("boardCode", "")
    // boardCode=Notice 여기에 있는 값을 가져오기 위한 변수
    // getStringParam 이 있는 Rq 에서 paramMap 을 만들었는데 이것의 데이터 타입은 Map이다.
    // map은 key와 value로 저장이됨. 예) 2조: Map = {윤혜림 : 1번}
    // 윤혜림이 몇번인지 알고 싶으면
    // 2조[윤혜림] 이렇게 쓰면, 1번이 리턴됨. -> 누가 1번인지 알 수 있음
    // 리스트와 다른점
    // 2조 = [1번] -> 리스트는 누가 1번인지를 모름
    // 여기서 유념해야할 문제가 하나 있음.
    // 우리 게시물은 리스트에 저장되어있음.
    // 그럼 제목이 abcd인 게시물을 불러오는 방법은?
    // 찾기가 어렵기 때문에, 게시물에 id(index)를 붙여놨음
    // 나중에 리스트에서 찾으려고

    // getStringParam 은
    // boardCode=Notice 를
    // boardCode, Notice 찢어서 name 인자 값 옆에 값을 가져옴.

    //다음으로는 searchKeyword를 가져와야함
    val searchKeyword = rq.getStringParam("searchKeyword", "")

    //다음으로는 page를 가져와야함
    val page = rq.getIntParam("page", 0)

    // 다음은 사용자가 준 정보를 토대로 저장되어있는 게시물을 목록화(정렬) 해서 보여줘야함.

    val filteredArticles = articleRepository.getFilteredArticles(boardCode, searchKeyword, page, 10)
    //정렬이 완료된 게시물 리스트를 filteredArticles에 저장 할 것임
    //이것의 의미는 사용자가 입력한 boardCode, searchKey에 맞게 정렬한 다음
    // 그 정렬된 게시물 리스트를 리턴해주는 기능을 갖고 있음

    println(" 게시판 이름 / 번호 / 제목 / 작성자 / 갱신일")

    for (article in filteredArticles) {
        val board = boardRepository.getBoard(article.boardIndex)
        val boardName = board!!.name

        val writer = memberRepository.getMemberByIndex(article.memberIndex)
        val writerName = writer!!.memName

        println("$boardName / ${article.id} / ${article.title} / $writerName / ${article.updateDate}")
    }
}
}
