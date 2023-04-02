package com.rentminder

import com.rentminder.dto.Household
import org.junit.Assert.assertTrue
import org.junit.Test

class HouseholdUnitTest {
    @Test
    fun `given a household dto when name is Uptown and members are 5 then name is Uptown and members are five`(){
        val household = Household(1, "Uptown", 5)
        assertTrue(household.houseName == "Uptown")
        assertTrue(household.houseMembers == 5)
    }

    @Test
    fun `given a country dto when name is Uptown and members are 5 then output is Uptown 5 Members`() {
        val household = Household(1, "Uptown", 5)
        assertTrue(household.toString() == "Uptown 5 Members")
    }
}