class ArticleController{
    fun write() {
        if(logonMember == null){
            println("로그인을 해주세요.")
        }
        // 제목, 내용, 글쓴이인덱스, 게시판인덱스

        //글을 누가쓰고 있는가를 판별해서 객체에 저장하기 위함
        //지금 현재 글을 쓰고 있는가
        // 현재 글을쓰고 있는자

        val memIndex = logonMember!!.memIndex

        //게시판인덱스는 사용자에게 받아야 한다.
        //그렇게 하기 위해서는 현재 어떤 게시판이 있는지 보여줘야함

        val boards = boardRepository.getBoards()
        // 게시판을 보여주기 위해서는 생성된 게시판 객체가 들어있는 리스트를 불러와야함
        // 그 안에 게시판들이 있으니까 정보를 확인해 볼 수 있기 때문
        // 모든 게시판을 나열해 보여줄 필요가 있다.

        var boardSelectStr = "" // 여기에 게시판 내용을 더해서 보여줄 것이기 때문에 var사용
        // for문으로 리스트를 출력하면 데이터 타입이 스트링으로 바뀜
        //우리가 게시판 모둠에서 하나씩꺼내 계속 더해주려면 스트링타입인 비어있는 변수가 필요

        for(board in boards){
            if(boardSelectStr.isNotEmpty()){ // 만약에 boardSelectStr가 비어있지 않다면
                boardSelectStr += ", " // boardSelectStr에  ,  를 더해줌 // 1, 공지, 2 , 자유
            }
            boardSelectStr += "${board.boardIndex} = ${board.name}"//1 = 공지, 2 = 자유
        } // boardSelectStr에 추가만 된 상태, 출력이 필요

        println("$boardSelectStr")
        //게시판 리스트가 출력

        //사용자가 게시판을 선택하게 해야함
        print("게시판을 선택해 주십시오(번호): ")
        val boardIndex = readLineTrim().toInt() //readLineTrim()을 하면 값이 스트링으로 바뀌어서 저장됨, 우리가 원하는 인덱스 값은 정수이기에 정수화 해줄 필요가 있음
        // boardIndex 에는 사용자가 선택한 번호가 저장됨

        print("제목: ")
        val title = readLineTrim()
        print("제목: ")
        val body = readLineTrim()

        //이제는 어떤사람이 글을 썻는지에 대한 정보를 얻어야함

        // val memberIndex = logonMember!!.memIndex // 게시글 작성은 로그인을 해야만 할 수 있는 기능이다.
        // 누가 쓴지는 로그인을 한 사람이기에 로그인한 사람의 정보를 저장함

        //객체 생성에 필요한 사용자에게 얻을 모든 정보는 다 얻음
        // 객체를 생성 해주어야 함 + 게시글 번호를 가져와 사용자에게 알려주면 됨
        //지금 필요한 것은 게시글 번호 및 객체 생성

        val id = articleRepository.addArticle(boardIndex, title, body, memIndex)
        //저장소에 addArticle(객체 생성을 담당함) 메소드(함수)에 정보를 넘김
        // 근데 이 메소드는 게시물 번호를 뱉어줌. 그래서 최종적으로 변수 아이디에는 게시물 번호가 들어감

        println("$id 번 게시물이 생성 되었습니다.")
    }

}