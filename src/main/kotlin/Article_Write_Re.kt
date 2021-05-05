class ArticleRepository {

    var lastId = 0 //마지막으로 생성된 게시물의 값을 나타냄, 하나의 게시물이 생성되면 값이 1로 바뀜
    val articles = mutableListOf<Article>() // Article 객체가 들어갈 리스트를 만듬

    fun addArticle(boardIndex: Int, title: String, body: String, memIndex: Int): Int {//왜 인트인가, 우리가 이 함수를 통해 내 뱉을 값은 게시물 번호이다.
        // 우리가 객체를 만들기 위해선 클래스에서 요구하는 모든 정보를 가지고 있어야 함.
        // id , regDate, updateDate가 필요
        val id = ++lastId //lastId에 1을 더한 후 다시 할당해줌
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()
        //이제 정보를 만들기 위해 필요한 모든 정보가 생김
        //객체를 만들자 마자 객체 상자에 집에 넣는 것이 효율적임
        articles.add(Article(id, memIndex, boardIndex, title, body, regDate, updateDate))
        //Article(id, memIndex, boardIndex, title, body, regDate, updateDate) 이것이 객체가 됨
        //articles.add() 이것이 객체 담을 리스트에 추가된다는 뜻
        //이제 게시글 객체는 객체꾸러미에 추가가 되었음
        return id // 게시물 번호가 id였음으로 id 을 return 함
    }

}
