package com.first.store;

import org.springframework.stereotype.Service;

@Service   //Annotation (Component [FOR UTILITY CLASSES], Service [alias for component, FOR BUISNESS CLASSES], Repository, Controller)
public class OrderService {
    private  PaymentService paymentService;
    public OrderService(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    public void placeOrder(){
        paymentService.processPayment(10);
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
