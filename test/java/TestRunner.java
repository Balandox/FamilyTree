import com.suai.familyTree.io.FamilyReader;
import com.suai.familyTree.io.FamilyReaderTest;
import com.suai.familyTree.model.FamilyTreeTest;

public class TestRunner {

    public static void main(String[] args) {
        FamilyReaderTest familyReaderTest = new FamilyReaderTest();
        FamilyTreeTest familyTreeTest = new FamilyTreeTest();

        familyReaderTest.readFromTxt();

        familyTreeTest.init();
        familyTreeTest.addNewPerson();
        familyTreeTest.init();
        familyTreeTest.clear();
        familyTreeTest.init();
        familyTreeTest.createNodes();
        familyTreeTest.init();
        familyTreeTest.findNodeById();
        familyTreeTest.init();
        familyTreeTest.getFemalePersonsArray();
        familyTreeTest.init();
        familyTreeTest.getFurtherPoint();
        familyTreeTest.init();
        familyTreeTest.getMalePersonsArray();
        familyTreeTest.init();
        familyTreeTest.getPersonsArray();
        familyTreeTest.init();
        familyTreeTest.getRelativeTypeArray();
        familyTreeTest.init();
        familyTreeTest.removeAndFixNodes();
        familyTreeTest.init();
        familyTreeTest.sort();
        System.out.println("All ok!");
    }

}
