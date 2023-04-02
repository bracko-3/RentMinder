package com.rentminder

import com.rentminder.dto.Members
import org.junit.Assert
import org.junit.Test


class MembersUnitTest {

    @Test
    fun `Given a members dto when membersID is 1 and memberName is Bob then memberName is Bob`(){
        var members = Members(1, "Bob")
        Assert.assertTrue(members.memberName.equals("Bob"))
    }

    @Test
    fun `Given a members dto when membersID is 1 and memberName is Bob then output is 1 Bob`() {
        var members = Members(1, "Bob")
        Assert.assertTrue(members.toString().equals("1 Bob"))
    }
}


