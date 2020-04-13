import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    String productName;
    BigDecimal quantity;
    BigDecimal price;


    public Product(String productName, BigDecimal quantity, BigDecimal price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public String toString() {
        return "Product: " + productName + "\t" +
                "Quantity: " + quantity + "\t" +
                "Price: " + price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price.multiply(quantity) ;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}
