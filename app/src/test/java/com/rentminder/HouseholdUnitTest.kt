package com.rentminder

import com.rentminder.dto.Household
import org.junit.Assert
import org.junit.Test

class HouseholdUnitTest {
    @Test
    fun `given a household dto when name is Uptown and members are 5 then name is Uptown and members are five`(){
        var household = Household(1, "Uptown", 5)
        Assert.assertTrue(household.houseName.equals("Uptown"))
        Assert.assertTrue(household.houseMembers.equals(5))
    }
}