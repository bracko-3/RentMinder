package com.rentminder
import com.rentminder.dto.Payment
import org.junit.Assert
import org.junit.Test

class PaymentUnitTest {
    @Test
    fun `given a payment dto when payment type is credit and payment num is 200 then payment type is credit and payment num is 200`(){
        var payment = Payment(0,1,"credit",200.0)
        Assert.assertTrue(payment.paymentType.equals("credit"))
        Assert.assertTrue(payment.paymentNum.equals(200.0))
    }
}