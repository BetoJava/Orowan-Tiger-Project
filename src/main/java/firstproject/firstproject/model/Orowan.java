package firstproject.firstproject.model;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Orowan {

    /**
     * Execute le programme Orowan.exe et renvoie le temps d'execution.
     * @return Compute Time
     */
    public long executeOrowan(String stripID, String stand) {
        long startTime = System.currentTimeMillis();
        try {
            String folderPath = "./FichiersOrowan";
            // Création du process builder pour exécuter le fichier .exe
            ProcessBuilder pb = new ProcessBuilder(folderPath + "/Model/Orowan_x64.exe.exe");
            pb.redirectErrorStream(true); // Redirige la sortie d'erreur vers la sortie standard

            // Démarrage du processus
            Process process = pb.start();

            OutputStream outputStream = process.getOutputStream();
            String input = "i" +
                    "\nc" +
                    "\n" + folderPath + "/Krakov/input_" + stripID + "_" + stand + ".txt" +
                    "\n" + folderPath + "output" + stripID + "_" + stand + ".txt\n";
            outputStream.write(input.getBytes());
            outputStream.flush();

            // Attente de la fin du processus
            process.waitFor();
            process.destroy();

            return System.currentTimeMillis() - startTime;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return 0;

    }

    /**
     * Convertie des RawData en OrowanInputData
     * @param filename
     * @return
     */
    public static ArrayList<OrowanInputData> convertRawDataToInputData(String filename) {
        // Les RawData sont les données dans les 1939351_F2.txt
        // Les OrowanInputData sont les données dans inv_cst.txt
        ArrayList<RawData> rawData = RawData.loadRawDataFromFile(filename);
        ArrayList<OrowanInputData> inputData = new ArrayList<>();
        for(int i = 0; i < rawData.size(); i++) {
            OrowanInputData orowanInputData = new OrowanInputData();
            orowanInputData.setCas(1);
            orowanInputData.setHe(rawData.get(i).getEnThick());
            orowanInputData.setHs(rawData.get(i).getExThick());
            orowanInputData.setTe(rawData.get(i).getEnTens());
            orowanInputData.setTs(rawData.get(i).getExTens());
            orowanInputData.setDiam_WR(rawData.get(i).getDiameter());
            orowanInputData.setWRyoung(rawData.get(i).getYoungModulus());
            orowanInputData.setOffset_ini(rawData.get(i).getAverageSigma());
            orowanInputData.setMu_ini(rawData.get(i).getMu());
            orowanInputData.setForce(rawData.get(i).getRollForce());
            orowanInputData.setG(rawData.get(i).getFSlip());
            inputData.add(orowanInputData);
        }
        // Sauvegarder sous forme de input_1939351_F2.txt
        return inputData;
    }




}