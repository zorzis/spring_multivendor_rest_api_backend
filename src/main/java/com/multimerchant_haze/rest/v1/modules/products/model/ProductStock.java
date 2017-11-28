package com.multimerchant_haze.rest.v1.modules.products.model;

import com.multimerchant_haze.rest.v1.modules.users.producer.model.Producer;
import com.multimerchant_haze.rest.v1.modules.products.dto.ProductStockDTO;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zorzis on 6/14/2017.
 */
@Entity
@Table(name = "products_stock")
public class ProductStock implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "product_stock_ID", nullable = false)
    private Long productStockID;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "productID", referencedColumnName = "productID")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "producerID", referencedColumnName = "producerID")
    private Producer producer = new Producer();


    public ProductStock()
    {

    }

    public ProductStock(ProductStockDTO productStockDTO)
    {

    }

    public Long getProductStockID()
    {
        return productStockID;
    }

    public void setProductStockID(Long productStockID)
    {
        this.productStockID = productStockID;
    }

    public Float getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Float quantity)
    {
        this.quantity = quantity;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Producer getProducer()
    {
        return producer;
    }

    public void setProducer(Producer producer)
    {
        this.producer = producer;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Product Stock Entity");
        sb.append("[");
        sb.append("ProductStockID: ");
        sb.append(this.productStockID);
        sb.append(" | ");
        sb.append("Product Stock Quantity: ");
        sb.append(this.quantity);
        sb.append("]");
        return sb.toString();
    }

}
