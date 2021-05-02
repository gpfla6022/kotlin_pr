val membersRepository = MembersRepository()
val articleRepository = ArticleRepository()
var logonMember: Member? = null

fun main(){
    println("==SIMPLE SSG 시작 ==")

    val systemController = SystemController()
    val membersController = MembersController()
    val articleController = ArticleController()

    while (true){
        println("명령어) ")
        val command = readLineTrim()

        val rq = Rq(command)

        when(rq.actionPath){

            "/system/exit" -> {
                systemController.exit()
                break
            }
            "/member/join" -> {
                membersController.join()
            }
            "/member/login" -> {
                membersController.login()
            }
            "/member/logout" -> {
                membersController.logout()
            }
            "/article/write" -> {
                articleController.write()
            }
            "/article/delete" -> {
                articleController.delete(rq)
            }
            "/article/modify" -> {
                articleController.modify(rq)
            }
            "/article/detail" -> {
                articleController.detail(rq)
            }

            else -> {
                print("존재하지 않는 명령어 입니다.")
            }
        }
    }




    println("==SIMPLE SSG 끝 ==")
}


