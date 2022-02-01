package backend;

import java.util.ArrayList;

// testing class for the class Prescription
public class PrescriptionTest {
    public static void main(String[] args) {
        // including some components in an ArrayList to test our program
        ArrayList<Component> components =  new ArrayList<>(){
            {
                add(new Component("lavender", null, null, 3.0));
                add(new Component("camomile", null, null, 30.0));
            }
        };
        Prescription prescription = new Prescription("Acne", new ArrayList<>(){
            {
                add(new Component("lavender", 2024, null, 1.0));
                add(new Component("camomile", 2022, null, 10.0));
            }
        });
        System.out.println(prescription); // display the prescription
        System.out.print("Is there enough quantity of herbs to make the medicine? ");
        // test hasEnoughQuantity
        if(prescription.hasEnoughQuantity(components)) {
            System.out.print("Yes");
        } else {
            System.out.print("No");
        }
        // test calculateMaxPortions
        prescription.calculateMaxPortions(components);
        System.out.printf("\nMax Medicine Portions: %s", prescription.getServing());
    }
}