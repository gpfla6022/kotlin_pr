class BoardRepository {

    var lastId = 0
    private val boards = mutableListOf<Board>()

    fun addboard(name: String, code: String, memId: Int): Int {
        val boardIndex = ++ lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        boards.add(Board(boardIndex, name, code, memId, regDate, updateDate))

        return boardIndex
    }

    fun getBoardByName(name: String): Board? {
        for(board in boards){
            if(board.name == name){
                return board
            }
        }
        return null
    }

    fun getBoardByCode(code: String): Board? {
        for (board in boards){
            if(board.code == code){
                return board
            }
        }
        return null
    }

    fun getBoardId(id: Int): Board? {
        for(board in boards){
            if(board.boardIndex == id){
                return board
            }
        }
        return null
    }

    fun deleBoard(board: Board){
        boards.remove(board)
    }

    fun getBoard(): List<Board> {
        return boards
    }

    fun testBoardMake(){
        addboard("공지", "notice", 1)
        addboard("자유", "free", 1)
    }

}