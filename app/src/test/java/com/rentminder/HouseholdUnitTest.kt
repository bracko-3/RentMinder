package com.rentminder

import com.rentminder.dto.Household
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Test

class HouseholdUnitTest {
    @Test
    fun `given a household dto when name is Uptown and members are 5 then name is Uptown and members are five`(){
        var household = Household(1, "Uptown", 5)
        Assert.assertTrue(household.householdName.equals("Uptown"))
        Assert.assertTrue(household.householdMembers.equals(5))
    }

    @Test
    fun `given a country dto when name is Uptown and members are 5 then output is Uptown 5 Members`() {
        var household = Household(1, "Uptown", 5)
        assertTrue(household.toString().equals("Uptown 5 Members"))
    }
}