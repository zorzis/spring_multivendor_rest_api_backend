package com.multimerchant_haze.rest.v1.modules.orders.model;

import com.multimerchant_haze.rest.v1.modules.products.dto.ProductDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zorzis on 7/13/2017.
 */
@Entity
@Table(name = "order_products")
public class OrderProduct
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id", nullable = false)
    private Long id;

    @Column(name = "productID", nullable = false)
    private String productID;

    @Column(name = "order_product_name", nullable = false)
    private String orderProductName;

    @Column(name = "order_product_description")
    private String orderProductDescription;

    @Column(name = "order_product_price")
    private Float orderProductPrice;

    @Column(name = "order_product_stock_quantity_at_moment_order_placed")
    private Float orderProductStockQuantityAtMommentOrderPlaced;

    @Column(name = "order_product_quantity", nullable = false)
    private Float orderProductQuantity;

    @Column(name = "order_product_category_id")
    private String orderProductCategoryID;

    @Column(name = "order_product_category_name")
    private String orderProductCategoryName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderProduct")
    private Set<OrderHasProducts> orderHasProducts = new HashSet<>(0);

    @Column(name ="order_product_has_aniseed")
    private Boolean hasAniseed;


    @Column(name = "order_product_alcohol_volume")
    private Float alcoholVolume;

    @Column(name = "order_product_date_distilled")
    private Date dateDistilled;

    @Column(name = "order_product_government_distill_approval_ID")
    private String governmentDistillApprovalID;

    public OrderProduct()
    {

    }


    // constructor merging a product To OrderProduct
    public OrderProduct(ProductDTO productDTO)
    {
        this.productID = productDTO.getProductID();
        this.orderProductName = productDTO.getProductName();
        this.orderProductDescription = productDTO.getProductDetails().getProductDescription();
        this.orderProductPrice = productDTO.getProductDetails().getPrice();
        this.orderProductStockQuantityAtMommentOrderPlaced = productDTO.getQuantity();
        this.orderProductCategoryID = productDTO.getProductCategory().getCategoryID();
        this.orderProductCategoryName = productDTO.getProductCategory().getCategoryName();
        this.hasAniseed = productDTO.getProductDetails().isHasAniseed();
        this.dateDistilled = productDTO.getProductDetails().getDateDistilled();
        this.alcoholVolume = productDTO.getProductDetails().getAlcoholVolume();
        this.governmentDistillApprovalID = productDTO.getProductDetails().getGovernmentDistillApprovalID();
    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }


    public String getProductID()
    {
        return productID;
    }

    public void setProductID(String productID)
    {
        this.productID = productID;
    }

    public String getOrderProductName()
    {
        return orderProductName;
    }

    public void setOrderProductName(String orderProductName)
    {
        this.orderProductName = orderProductName;
    }

    public String getOrderProductDescription()
    {
        return orderProductDescription;
    }

    public void setOrderProductDescription(String orderProductDescription)
    {
        this.orderProductDescription = orderProductDescription;
    }

    public Float getOrderProductPrice()
    {
        return orderProductPrice;
    }

    public void setOrderProductPrice(Float orderProductPrice)
    {
        this.orderProductPrice = orderProductPrice;
    }

    public Float getOrderProductStockQuantityAtMommentOrderPlaced()
    {
        return orderProductStockQuantityAtMommentOrderPlaced;
    }

    public void setOrderProductStockQuantityAtMommentOrderPlaced(Float orderProductStockQuantityAtMommentOrderPlaced)
    {
        this.orderProductStockQuantityAtMommentOrderPlaced = orderProductStockQuantityAtMommentOrderPlaced;
    }

    public Float getOrderProductQuantity()
    {
        return orderProductQuantity;
    }

    public void setOrderProductQuantity(Float orderProductQuantity)
    {
        this.orderProductQuantity = orderProductQuantity;
    }

    public String getOrderProductCategoryID()
    {
        return orderProductCategoryID;
    }

    public void setOrderProductCategoryID(String orderProductCategoryID)
    {
        this.orderProductCategoryID = orderProductCategoryID;
    }

    public String getOrderProductCategoryName()
    {
        return orderProductCategoryName;
    }

    public void setOrderProductCategoryName(String orderProductCategoryName)
    {
        this.orderProductCategoryName = orderProductCategoryName;
    }

    public Set<OrderHasProducts> getOrderHasProducts()
    {
        return orderHasProducts;
    }

    public void setOrderHasProducts(Set<OrderHasProducts> orderHasProducts)
    {
        this.orderHasProducts = orderHasProducts;
    }


    public Boolean getHasAniseed()
    {
        return hasAniseed;
    }

    public void setHasAniseed(Boolean hasAniseed)
    {
        this.hasAniseed = hasAniseed;
    }

    public Float getAlcoholVolume()
    {
        return alcoholVolume;
    }

    public void setAlcoholVolume(Float alcoholVolume)
    {
        this.alcoholVolume = alcoholVolume;
    }

    public Date getDateDistilled()
    {
        return dateDistilled;
    }

    public void setDateDistilled(Date dateDistilled)
    {
        this.dateDistilled = dateDistilled;
    }

    public String getGovernmentDistillApprovalID()
    {
        return governmentDistillApprovalID;
    }

    public void setGovernmentDistillApprovalID(String governmentDistillApprovalID)
    {
        this.governmentDistillApprovalID = governmentDistillApprovalID;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Order Product Entity");
        sb.append("[");
        sb.append("Order ProductID: ");
        sb.append(this.productID);
        sb.append(" | ");
        sb.append("Order ProductName: ");
        sb.append(this.orderProductName);
        sb.append(" | ");
        sb.append("Order Product Description: ");
        sb.append(this.orderProductDescription);
        sb.append(" | ");
        sb.append("Order Product Price: ");
        sb.append(this.orderProductPrice);
        sb.append(" | ");
        sb.append("Order Product Quantity: ");
        sb.append(this.orderProductQuantity);
        sb.append(" | ");
        sb.append("Order Product Stock Quantity At Moment Order Placed: ");
        sb.append(this.orderProductStockQuantityAtMommentOrderPlaced);
        sb.append(" | ");
        sb.append("Order Product CategoryID: ");
        sb.append(this.orderProductCategoryID);
        sb.append(" | ");
        sb.append("Order Product Category Name: ");
        sb.append(this.orderProductCategoryName);
        sb.append("]");
        return sb.toString();
    }

}
