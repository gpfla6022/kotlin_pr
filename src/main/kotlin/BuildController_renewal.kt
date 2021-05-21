import java.io.File

class BuildController {


    fun ssg() {

        buildRepository.createMemberListPage()
        buildRepository.createBoardListPage()

        val boards = boardRepository.getBoards()

        for ( board in boards ) {
            buildRepository.createBoardDetailPage(board)
        }

        val articles = articleRepository.getArticles()

        for ( article in articles ) {
            buildRepository.createArticleDetailPage(article)
        }


    }



}