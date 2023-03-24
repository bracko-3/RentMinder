package com.rentminder
import com.rentminder.DTO.PaymentDto
import org.junit.Assert
import org.junit.Test

class PaymentUnitTest {
    @Test
    fun `given a payment dto when payment type is credit and payment num is 200 then payment type is credit and payment num is 200`(){
        var paymentDto = PaymentDto(0,1,"credit",200.0)
        Assert.assertTrue(paymentDto.paymentType.equals("credit"))
        Assert.assertTrue(paymentDto.paymentNum.equals(200.0))
    }

}