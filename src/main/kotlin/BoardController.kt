class BoardController {
    //제목, 코드, 번호, 작성자, 갱신일, 생성일
    fun add() {

        if (logonMember == null) {
            println("로그인을 해주세요")
            return
        }

        print("게시판 이름: ")
        val name = readLineTrim()

        val boardByName = boardRepository.getBoardByName(name)

        if (boardByName != null) {
            println("중복된 게시판 이름 입니다.")
            return
        }

        print("게시판 코드: ")
        val code = readLineTrim()

        val boardByCode = boardRepository.getBoardByCode(code)

        if (boardByCode != null) {
            println("중복된 게시판 코드 입니다.")
            return
        }

        val memId = logonMember!!.memIndex

        val boardId = boardRepository.addboard(name, code, memId)

        println("$boardId 번 게시판이 작성되었습니다.")

    }

    fun delete(rq: Rq) {

        if (logonMember == null) {
            println("로그인을 해주세요")
            return
        }
        // /board/delete?id=1

        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("게시판 번호를 확인해 주세요.")
            return
        }
        val board = boardRepository.getBoardId(id)

        if (board == null) {
            println("존재하지 않는 게시판입니다.")
            return
        }
        if (board.memId != logonMember!!.memIndex) {
            println("권한이 없습니다.")
            return
        }
        boardRepository.deleBoard(board)

        println("$id 번 게시판이 삭제되었습니다.")
    }

    fun modify(rq: Rq) {
        if (logonMember == null) {
            println("로그인을 해주세요")
            return
        }
        val id = rq.getIntParam("id", 0)

        if (id == 0) {
            println("게시판 번호를 확인해 주세요.")
            return
        }
        val board = boardRepository.getBoardId(id)

        if (board == null) {
            println("존재하지 않는 게시판입니다.")
            return
        }
        if (board.memId != logonMember!!.memIndex) {
            println("권한이 없습니다.")
            return
        }
        print("새 이름: ")
        board.name = readLineTrim()
        print("새 코드: ")
        board.code = readLineTrim()
        board.updateDate = Util.getNowDateStr()

        println("$id 번 게시판을 수정하였습니다.")
    }

    // /board/list?page=1
    fun list(rq: Rq) {
        if (logonMember == null) {
            println("로그인을 해주세요")
            return
        }

        val page = rq.getIntParam("page", 0)

        if (page == 0) {
            println("페이지 번호를 다시 입력해 주세요.")
            return
        }

        val boards = boardRepository.getBoard()

        println("번호 / 이름 / 코드 ")


        for (board in boards) {
            println("${board.boardIndex} / ${board.name} / ${board.code}")
        }

    }
}