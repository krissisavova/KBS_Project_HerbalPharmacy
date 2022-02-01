package backend;
import java.util.Objects;

// class Component represents a product which has a name, shelf-life year, price and unit =>
// unit shows the information about the component in grams and per one piece
public class Component {
    private String name;      // name of component
    private Integer years;     // shelf-life in years
    private Double price;     // price per one component
    private Double unit;      // unit - grams or per piece

    // constructor which creates a component by given information
    public Component(String name, Integer years, Double price, Double unit) {
        setName(name);
        setUnit(unit);
        setYears(years);
        setPrice(price);
    }

    // constructor which creates a component by default values for the data
    public Component() {
        this("no name", 2022, 0.1, 0.1);
    }

    // two components differ from each other by the name
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Objects.equals(name, component.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // get method which returns the component name
    public String getName() {
        return name;
    }

    // set method which set the name of component
    public void setName(String name) {
        this.name = name == null ? "no name" : name;
    }

    // get method which get the expiry year of a component
    public Integer getYears() {
        return years;
    }

    // method for setting the years
    public void setYears(Integer years) {
        this.years = (years == null || years < 2022) ? 2022 : years;
    }

    // get method which returns the price
    public Double getPrice() {
        return price;
    }

    // set method which set the price => it is equal to price * unit else it is 0.1 by default
    public void setPrice(Double price) {
        this.price = (price == null || price <= 0) ? 0.1 : price * unit;
    }

    // get method for the unit
    public Double getUnit() {
        return unit;
    }

    // set method for the unit
    public void setUnit(Double unit) {
        this.unit = (unit == null || unit <= 0) ? 0.1 : unit;
    }

    // method toString for displaying the information about the component
    @Override
    public String toString() {
        return String.format(" Component: %s, Shelf-Life in Years: %d", name, years);
    }
} // end of class Component