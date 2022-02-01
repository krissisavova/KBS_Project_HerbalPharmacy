package backend;
import java.util.ArrayList;
// class KnowledgeBaseTest which tests the functionality of the class KnowledgeBase
public class KnowledgeBaseTest {
    public static void main(String[] args) {
        // read the information from the files
        KnowledgeBase knowledgeBase = new KnowledgeBase("components.csv", "prescriptions.csv");
        //System.out.println(knowledgeBase); // display all components and all prescriptions
        // randomly entered components from the user with which can create prescriptions
        ArrayList<Component> components = new ArrayList<>() {
            {
                add(new Component("lavender", null, null, 600.0));
                add(new Component("camomile", null, null, 80.0));
                add(new Component("mint", null, null, 40.0));
                add(new Component("hawthorn", null, null, 90.0));
                add(new Component("valerian", null, null, 30.0));
                add(new Component("balm", null, null, 450.0));
                add(new Component("tutsan", null, null, 100.0));
                add(new Component("thyme", null, null, 400.0));
                add(new Component("basil", null, null, 65.0));
                add(new Component("agrimony", null, null, 200.0));
                add(new Component("yarrow", null, null, 85.0));
                add(new Component("milkvetch", null, null, 100.0));
                add(new Component("linden", null, null, 850.0));
                add(new Component("hop cones", null, null, 260.0));
                add(new Component("white comfrey", null, null, 170.0));
                add(new Component("coltsfoot", null, null, 55.0));
                add(new Component("juniper", null, null, 190.0));
                add(new Component("mustard seed", null, null, 5.0));
                add(new Component("raspberry", null, null, 15.0));
                add(new Component("marigold", null, null, 15.0));
                add(new Component("oregano", null, null, 520.0));
            }
        };

        // get all prescriptions that contain the components listed above
        ArrayList<Prescription> prescriptions = knowledgeBase.getPrescriptions(components);
        System.out.println(prescriptions); // display these prescriptions
    }
} // end of class KnowledgeBaseTest