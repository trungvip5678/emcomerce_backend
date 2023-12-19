package com.springboot.ecommercewebsite.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentDetails {

    private String paymentMethod;
    private String status;

    private String paymentId;

    private String razorpayPaymentLinkId;

    private String razorpayPaymentReferenceId;

    private String razorpayPaymentLinkStatus;

    private String razorpayPaymentId;




}
