package com.yaari.ms.catalogservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class OrderItemDto {
	private Long id;

	private Long orderId;

	private String ucOrderId;

	private Long productSkuId;

	private Long quantity;

	private double margin;

	private double finalItemPrice;

	private double totalCustomerPrice;

	private double itemTax;


	private long shippedTime;

	private String status;

	private String returnType;

	private Long boxId;

	private String invoiceNumber;

	private String invoiceDate;

	private double taxRate;

	private double centralGstPercentage;

	private double compensationCessPercentage;

	private double integratedGstPercentage;

	private double stateGstPercentage;

	private double unionTerritoryGstPercentage;

	private String partner;

	private String awbNumber;

	private Long sellerShippingId;

	private String shipmentRemarks;

	private long deliveryTime;

	private Long orderItemExchange;

	private String paymentQrCodeUrl;

	private String shiprocketShipmentId;

	private String shiprocketCourierPartner;

	private double appliedShippingCharge;


}
