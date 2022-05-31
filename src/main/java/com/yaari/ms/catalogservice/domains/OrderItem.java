package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "uc_order_id")
	private String ucOrderId;

	@Column(name = "product_sku_id")
	private Long productSkuId;

	@Column(name = "quantity")
	private Long quantity;

	@Column(name = "margin")
	private double margin;

	@Column(name = "final_item_price")
	private double finalItemPrice;

	@Column(name = "total_customer_price")
	private double totalCustomerPrice;

	@Column(name = "item_tax")
	private double itemTax;

	@Column(name = "created_time")
	@CreatedDate
	private Date createdTime;

	@Column(name = "updated_time")
	@LastModifiedDate
	private Date updatedTime;

	@Column(name = "shipped_time")
	private long shippedTime;

	@Column(name = "status")
	private String status;

	@Column(name = "return_type")
	private String returnType;

	@Column(name = "box_id")
	private Long boxId;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	@Column(name = "invoice_date")
	private String invoiceDate;

	@Column(name = "tax_rate")
	private double taxRate;

	@Column(name = "central_gst_percentage")
	private double centralGstPercentage;

	@Column(name = "compensation_cess_percentage")
	private double compensationCessPercentage;

	@Column(name = "integrated_gst_percentage")
	private double integratedGstPercentage;

	@Column(name = "state_gst_percentage")
	private double stateGstPercentage;

	@Column(name = "union_territory_gst_percentage")
	private double unionTerritoryGstPercentage;

	@Column(name = "partner")
	private String partner;

	@Column(name = "awb_number")
	private String awbNumber;

	@Column(name = "seller_shipping_id")
	private Long sellerShippingId;

	@Column(name = "shipment_remarks")
	private String shipmentRemarks;

	@Column(name = "delivery_time")
	private long deliveryTime;

	@Column(name = "order_item_exchange")
	private Long orderItemExchange;

	@Column(name = "payment_qr_code_url")
	private String paymentQrCodeUrl;

	@Column(name = "shiprocket_shipment_id")
	private String shiprocketShipmentId;

	@Column(name = "shiprocket_courier_partner")
	private String shiprocketCourierPartner;

	@Column(name = "applied_shipping_charge")
	private double appliedShippingCharge;


}
