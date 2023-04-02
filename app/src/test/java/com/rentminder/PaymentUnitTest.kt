package com.rentminder
import com.rentminder.dto.Payment
import org.junit.Assert
import org.junit.Test

class PaymentUnitTest {
    @Test
    fun `Given a payment when I select credit and input a value of 200 I should receive a payment of type credit with a value of 200`(){
        var payment = Payment(0,1,"credit",200.0)
        Assert.assertTrue(payment.paymentType.equals("credit"))
        Assert.assertTrue(payment.paymentNum.equals(200.0))
    }
}