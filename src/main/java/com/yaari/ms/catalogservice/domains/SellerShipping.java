package com.yaari.ms.catalogservice.domains;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seller_shipping")
@NoArgsConstructor
@Getter
@Setter
public class SellerShipping {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "delivery_partner")
    private String deliveryPartner;

    @Column(name = "dispatch_date")
    private String dispatchDate;

    @Column(name = "invoice_date")
    private String invoiceDate;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "tentative_delivery_date")
    private String tentativeDeliveryDate;

    @Column(name = "tracking_id")
    private String trackingId;

    

    

    

    

    

    

    

    

    

    

    

    

    

    
}
