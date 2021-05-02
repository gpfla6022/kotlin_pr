class MembersController {
    fun join() {

        print("아이디: ")
        val memId = readLineTrim()

        val isJoinableId = membersRepository.isJoinableId(memId)

        if(isJoinableId == false){
            println("이미 존재하는 아이디 입니다.")
            return
        }

        print("비밀번호: ")
        val memPw = readLineTrim()
        print("이름: ")
        val memName = readLineTrim()
        print("별명: ")
        val memNick = readLineTrim()
        print("전화번호: ")
        val memPh = readLineTrim()
        print("이메일: ")
        val memEmail = readLineTrim()

        val memIndex = membersRepository.addMember(memId, memPw, memName, memNick, memPh, memEmail)

    }

    fun login(){

        print("아이디: ")
        val userId = readLineTrim()

        val compareId = membersRepository.getMemberId(userId)

        if(compareId == null){
            println("해당 아이디는 존재하지 않습니다.")
            return
        }

        print("비밀번호: ")
        val userPw = readLineTrim()

        if(compareId.memPw != userPw){
            println("비밀번호가 틀렸습니다.")
            return
        }

        logonMember = compareId

        println("${compareId.memNick}님 환영합니다.")

    }

    fun logout() {
        logonMember = null
        println("로그아웃이 되었습니다.")
    }


}