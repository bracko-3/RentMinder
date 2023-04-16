package com.rentminder

import com.rentminder.dto.Bill
import org.junit.Assert
import org.junit.Test

class BillUnitTest {
    @Test
    fun `given a bill dto when month is April and rent bill is 3000 then month is April and rent bill is 3000`() {
        var bill = Bill(
            "",
            "April",
            "",
            3000,
            100,
            80,
            45,
            25,
            true,
            false,
            false,
            true,
            true,
            812.5,
            3250.0
        )
        Assert.assertTrue(bill.month.equals("April"))
        Assert.assertTrue(bill.rentBill.equals(3000))
    }

    @Test
    fun `given a bill total 3250 and 4 members then total per person should be 812,5`() {
        var bill = Bill(
            "",
            "April",
            "",
            3000,
            100,
            80,
            45,
            25,
            true,
            false,
            false,
            true,
            true,
            812.5,
            3250.0
        )
        var totalPerPerson = bill.total / 4

        Assert.assertTrue(bill.totalPerson.equals(totalPerPerson))
    }
}