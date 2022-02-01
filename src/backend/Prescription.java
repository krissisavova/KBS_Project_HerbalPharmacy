package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

// class Prescription represents one prescription which consists of components
public class Prescription {
    private String name;        // name of prescription;
    private Integer serving;    // number of max servings which can be made from the components
    private Double price;       // price per portion
    private Integer years;    // prescription year of shelf-life
    private ArrayList<Component> components; // each prescription consists of list with components

    // constructor which creates a prescription by given: name of prescription, serving and some components
    public Prescription(String name, ArrayList<Component> components) {
        setName(name);
        setServing(1);
        setComponents(components);
        setYears();
        setPrice();
    }

    // constructor which creates a prescription with default values
    public Prescription() {
        this(null, null);
    }

    // get method for the name of a prescription
    public String getName() {
        return name;
    }

    // set method for the name => if it is null, it receives a default value - "no name"
    public void setName(String name) {
        this.name = name == null ? "no name" : name;
    }

    // get method for the serving
    public Integer getServing() {
        return serving;
    }

    // set method for the serving
    public void setServing(Integer serving) {
        this.serving = (serving == null || serving <= 0) ? 1 : serving;
    }

    // get method for the price
    public Double getPrice() {
        return price;
    }

    // set method for the price => the price of a prescription is formed from the price of all components which are in the prescription
    public void setPrice() {
        this.price = 0.0;

        for (Component p : components) {
            this.price += p.getPrice();
        }
        this.price += 2.5; // base value
    }

    // get method for the year
    public Integer getYears() {
        return years;
    }

    // set method for the year of shelf-life of a prescription =>
    public void setYears() {
        this.years = (years == null || years < 2022) ? 2022 : years;
    }

    // get method for the components in a prescription
    public ArrayList<Component> getComponents() {
        return components;
    }

    // set method for the components in a prescription
    public void setComponents(ArrayList<Component> components) {
        this.components = new ArrayList<>();
        this.components.addAll(components);
    }

    // method which checks if there is enough quantity of the components, so a prescription can be made up of them
    public boolean hasEnoughQuantity(ArrayList<Component> userComponents) {
        // intersection is an ArrayList which contains only the components entered by the user which are available in the .csv file
        ArrayList<Component> intersection = (ArrayList<Component>) userComponents.stream().filter((x) -> components.contains(x)).collect(Collectors.toList());

        // if there are not enough components to make one prescription, we return false
        if (intersection.size() != this.components.size()) {
            return false;
        }

        // check if there is the minimal quantity for each component
        for (Component userComponent : intersection) {
            for (Component p : components) {
                if (p.getName().equals(userComponent.getName())) {
                    // if there is a component entered by the user which unit is smaller than the unit of the component in the file, return false
                    if (userComponent.getUnit() < p.getUnit()) {
                        return false;
                    }
                }
            }
        }
        // else, there is enough quantity to make a prescription, so we return true
        return true;
    }

    // method which calculates the max portions that can be made up of the user components
    public void calculateMaxPortions(ArrayList<Component> input) {
        // this list contains the minimal quantity /unit/ of a component
        // it shows the maximum servings that can be made up of the given components
        ArrayList<Integer> maxQuantity = new ArrayList<>();

        // for each entered component from the user list -> input, the minimal quantity can be work out as userUnit / prescriptionUnit
        for (Component userComponent : input) {
            for (Component prescriptionComponent : components) {
                if (userComponent.getName().equals(prescriptionComponent.getName())) {
                    Double userUnit = userComponent.getUnit();        // this is the unit which is entered by the user for a component
                    Double prescriptionUnit = prescriptionComponent.getUnit();    // this is the unit which is entered for the component in the .csv file
                    // add the minimal quantity to the ArrayList of integers
                    maxQuantity.add((int) (userUnit / prescriptionUnit));
                }
            }
        }
        // the number of servings is actually the minimal quantity from the ArrayList maxQuantity
        serving = Collections.min(maxQuantity);
    }

    // method toString which displays the information about the prescriptions
    @Override
    public String toString() {
        StringBuilder componentsList = new StringBuilder();
        for (Component p : components) {
            componentsList.append(p.toString());
        }

        return String.format("Prescription name: %s, Maximum portions: %d, Price per portion: %.2f lv., " +
                "Shelf-Life in Years: %d\nComponents:\n%s\n", name, serving, price, years, componentsList);
    }
}