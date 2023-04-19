package firstproject.firstproject.test;

import firstproject.firstproject.dataClasses.OrowanOutputData;
import firstproject.firstproject.model.Orowan;

import java.util.ArrayList;

public class TestOrowanExecution {

    /**
     * Méthode pour tester le bon fonctionnement de la méthode de chargement d'Orowan.
     */
    public static void main(String[] args) {
        ArrayList<OrowanOutputData> outputData = Orowan.computeOrowanModel("1939351","F2");
    }


}
