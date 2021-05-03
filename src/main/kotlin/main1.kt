val memberRepository = MemberRepository()
val articleRepository = ArticleRepository()
var logonMember: Member? = null

fun main(){

    val systemController = SystemController()
    val memberController = MemberController()
    val articleController = ArticleController()


    println("==SIMPLE SSG 시작 ==")

    while (true){

        val prompt = if(logonMember == null){
            "prompt) "
        } else {
            "${logonMember!!.memNick}) "
        }

        print(prompt)
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath){
            "/system/exit" -> {
                systemController.exit()
                break
            }
            "/member/join" -> {
                memberController.join()
            }
            "/member/login" -> {
                memberController.login()
            }
            "/member/logout" -> {
                memberController.logout()
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
            "/article/list" -> {
                articleController.list(rq)
            }
            else ->{
                println("존재하지 않는 명령어 입니다.")
            }

        }
    }



    println("==SIMPLE SSG 끝 ==")
}val memberRepository = MemberRepository()
val articleRepository = ArticleRepository()
var logonMember: Member? = null

fun main(){

    val systemController = SystemController()
    val memberController = MemberController()
    val articleController = ArticleController()


    println("==SIMPLE SSG 시작 ==")

    while (true){

        val prompt = if(logonMember == null){
            "prompt) "
        } else {
            "${logonMember!!.memNick}) "
        }

        print(prompt)
        val command = readLineTrim()

        val rq = Rq(command)

        when (rq.actionPath){
            "/system/exit" -> {
                systemController.exit()
                break
            }
            "/member/join" -> {
                memberController.join()
            }
            "/member/login" -> {
                memberController.login()
            }
            "/member/logout" -> {
                memberController.logout()
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
            "/article/list" -> {
                articleController.list(rq)
            }
            else ->{
                println("존재하지 않는 명령어 입니다.")
            }

        }
    }



    println("==SIMPLE SSG 끝 ==")
}