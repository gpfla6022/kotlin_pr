class MemberController {
    fun join() {
        print("아이디: ")
        val memId = readLineTrim()

        val isJoinableIb = memberRepository.isJoinableId(memId)

        if(!isJoinableIb){
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

        val memIndex = memberRepository.addMember(memId, memPw, memName, memNick, memPh, memEmail)

        println("$memIndex 번 째 회원님 환영합니다!")
    }

    fun login() {
        print("아이디: ")
        val userId = readLineTrim()

        val memIndex = memberRepository.getMemberId(userId)

        if( memIndex == null){
            println("존재하지 않는 아이디 입니다.")
            return
        }

        print("비밀번호: ")
        val userPw = readLineTrim()

        if (memIndex.memPw != userPw){
            println("비밀번호를 다시 확인해 주세요.")
            return
        }

        logonMember = memIndex

        println("${memIndex.memNick}님 환영합니다.")

    }

    fun logout() {
        logonMember = null
        println("정상적으로 로그아웃이 되셨습니다,")
    }


}