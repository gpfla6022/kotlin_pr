class MemberRepository {

    var lastId = 0
    val members = mutableListOf<Member>()

    fun addMember(memId: String, memPw: String, memName: String, memNick: String, memPh: String, memEmail: String): Int {
        val memIndex = ++ lastId
        val regDate = Util.getNowDateStr()
        val updateDate = Util.getNowDateStr()

        members.add(Member(memIndex, memId, memPw, memName, memNick, memPh, memEmail))
        return memIndex

    }

    fun getMemberId(userId: String): Member? {

        for(member in members){
            if(member.memId == userId){
                return member
            }
        }
        return null
    }

    fun isJoinableId(memId: String): Boolean {
        val member = getMemberId(memId)

        return member == null
    }


}