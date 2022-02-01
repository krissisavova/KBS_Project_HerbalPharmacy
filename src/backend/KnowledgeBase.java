package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InvalidObjectException;
import java.util.ArrayList;

// class KnowledgeBase which contains all components and prescriptions
public class KnowledgeBase {
    private ArrayList<Component> components;
    private ArrayList<Prescription> prescriptions;

    // constructor which reads the components and the prescriptions from the files
    public KnowledgeBase(String componentsFile, String prescriptionsFile) {
        components = new ArrayList<>();
        prescriptions = new ArrayList<>();
        readComponents(componentsFile);
        readPrescriptions(prescriptionsFile);
    }

    // helper method for reading the components for each prescription from the .csv file
    private ArrayList<Component> parseComponents(String input) throws InvalidObjectException {
        ArrayList<Component> result = new ArrayList<>();     // the result will be saved in this ArrayList
        Component component = null;                            // declare a component
        String[] parsed = input.split("[)][ ]");     // remove the ')' and the ' ' between the unit and the name of component

        String tmp = parsed[parsed.length - 1];           // take the last char from the input line (which is ')')
        tmp = tmp.substring(0, tmp.length() - 1);          // remove the last char - ')' from the input
        parsed[parsed.length - 1] = tmp;                   // replace the tmp which was ')' with ''

        // for each string from parsed
        for (String s : parsed) {
            String[] nameAndQuantity = s.split("[ ][(]");  // remove the ' ' and the '('
            boolean found = false;                                // boolean flag which shows if a component name is found
            // for each component from the ArrayList of components
            for (Component p : components) {
                // nameAndQuantity[0] -> the name of a component
                if (p.getName().equals(nameAndQuantity[0])) {
                    found = true; // we found a name of a component
                    // initialize the component with the values in the .csv file for prescriptions using its constructor
                    // nameAndQuantity[1] -> shows the unit
                    component = new Component(nameAndQuantity[0], p.getYears(), p.getPrice(),
                            Double.parseDouble(nameAndQuantity[1]));
                    break;
                }
            }
            // check if the component is entered correctly in the prescription file
            if (!found) {
                throw new InvalidObjectException("The component " + nameAndQuantity[0] + " was not found!\n");
            }
            // finally, add the components to the result
            result.add(component);
        }
        // and return result
        return result;
    }

    // method which reads the components from the .csv file
    private void readComponents(String filename) {
        BufferedReader openFile; // open the file for reading
        try {
            // get the file
            openFile = new BufferedReader(new FileReader(filename));
            //Read to skip the header
            openFile.readLine();

            String line = "";
            //Reading from the second line
            while ((line = openFile.readLine()) != null) {
                String[] lineData = line.split(",");

                if (lineData.length > 0) {
                    //Save the values in the training data
                    components.add(new Component(
                            lineData[0], // read the name of a component
                            Integer.parseInt(lineData[1]),  // read the years
                            Double.parseDouble(lineData[2]),  // read the price
                            Double.parseDouble(lineData[3])  // read the unit (quantity)
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method which reads the prescriptions from the .csv file
    private void readPrescriptions(String filename) {
        BufferedReader openFile; // open the file for reading
        try {
            // get the file
            openFile = new BufferedReader(new FileReader(filename));
            //Read to skip the header
            openFile.readLine();

            String line = "";
            //Reading from the second line
            while ((line = openFile.readLine()) != null) {
                String[] lineData = line.split(",");

                if (lineData.length > 0) {
                    prescriptions.add(new Prescription(
                            lineData[0], // read the name of a prescription
                            parseComponents(lineData[1]) // read the list of components
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // method which returns an ArrayList of prescriptions that can be made up of the given ArrayList with components
    public ArrayList<Prescription> getPrescriptions(ArrayList<Component> input) {
        ArrayList<Prescription> result = new ArrayList<>();
        // for each component from the ones which the user enter
        // search for it in the list of all components and set the years and the price for it
        for (Component p : components) {
            for (Component component : input) {
                if (p.getName().equals(component.getName())) {
                    component.setYears(p.getYears());
                    component.setPrice(p.getPrice());
                }
            }
        }
        // for each prescription in the list of all prescriptions
        // if there is enough quantity of the user components, calculate the max portions for the prescription
        // that can be made of these components and add it to the result
        for (Prescription r : prescriptions) {
            if (r.hasEnoughQuantity(input)) {
                r.calculateMaxPortions(input);
                result.add(r);
            }
        }
        // finally, return all prescriptions which are in the result
        return result;
    }

    // method which get all components
    public ArrayList<Component> getComponents() {
        return components;
    }

    // method toString which displays an information about the components and the prescriptions
    @Override
    public String toString() {
        StringBuilder componentsList = new StringBuilder();
        for (Component p : components) {
            componentsList.append(p.toString());
        }
        StringBuilder prescriptionsList = new StringBuilder();
        for (Prescription r : prescriptions) {
            prescriptionsList.append(r.toString());
        }
        return String.format("Components: %s\nPrescriptions: %s\n", components, prescriptions);
    }
} // end of class KnowledgeBase