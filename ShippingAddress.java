/**
 * <h1>Shipping Address</h1> Represents a shipping address
 *
 * @author Jackson Oriez and Alexmaxfield
 * @version 12/4/2018
 */
public class ShippingAddress {
    String name;
    String address;
    String city;
    String state;
    int zipCode;

    public ShippingAddress() {
        this.name = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zipCode = 0;
    }

    public ShippingAddress(String name, String address, String city, String state, int zipCode) {
        this.name = name;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}