package com.angel.models.constants;

public class TopicConstants {

    public static final String CREATE_ORDER_COMMAND = "createOrderCommand";
    public static final String RESERVE_PRODUCT_COMMAND = "reserveProductCommand";
    public static final String PROCESS_PAYMENT_COMMAND = "processPaymentCommand";
    public static final String APPROVE_ORDER_COMMAND = "approveOrderCommand";
    public static final String CANCEL_PRODUCT_RESERVATION_COMMAND = "productReservationCancelCommand";
    public static final String CANCEL_PAYMENT_COMMAND = "paymentCancelCommand";
    public static final String REJECT_ORDER_COMMAND_PAYMENT= "orderRejectCommandPayment";
    public static final String REJECT_ORDER_COMMAND_PRODUCT= "orderRejectCommandProduct";


    public static final String ORDER_CREATED_EVENT = "createOrderEvent";
    public static final String PRODUCT_RESERVED_EVENT = "reserveProductEvent";
    public static final String PAYMENT_PROCESSED_EVENT = "processPaymentEvent";
    public static final String ORDER_APPROVED_EVENT = "approveOrderEvent";
    public static final String PRODUCT_RESERVATION_CANCELED_EVENT = "productReservationCanceledEvent";
    public static final String PAYMENT_CANCELED_EVENT = "paymentCanceledEvent";
    public static final String ORDER_REJECTED_EVENT = "orderRejectedEvent";

    public static final String GROUP_ID = "my77";

    public static final String SERVER = "localhost:9092";

}
