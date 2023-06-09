package firstproject.firstproject.model;

import firstproject.firstproject.dataClasses.OrowanInputData;
import firstproject.firstproject.dataClasses.OrowanOutputData;
import firstproject.firstproject.dataClasses.ProcessedOutputData;
import firstproject.firstproject.dataClasses.RawData;

import java.io.*;
import java.util.ArrayList;

public class Orowan {

    public static long lastComputeTime = 0;

    /**
     * Execute Orowan.exe afin de récupérer les données de sortie,
     * à partir de l'identifiant de la bande et du nom du stand étudié.
     *<br><br>
     * <strong>Les différentes étapes sont les suiantes :</strong><br>
     *      - Il faut convertir les données brutes des fichiers 1939YYY_FX.txt (RawData) en fichier inv_cst.txt (OrowanInputData)<br>
     *      - On lance Orowan.exe :<br>
     * 	       > Orowan.exe(inv_cst.txt) -> output.txt<br>
     *      - Et on obtient les données de sortie (OrowanOutputData)
     * @param stripID Identifiant de la bande étudiée.
     * @param stand Nom du stand étudié.
     * @return Les sortie d'Orowan.exe sous forme d'une ArrayList d'objets OrowanOutputData.
     */
    public static ArrayList<OrowanOutputData> computeOrowanModel(String stripID, String stand) {
        String currentDirectory = System.getProperty("user.dir");
        String fileName = currentDirectory + "/FichiersOrowan/";

        try {
            ArrayList<OrowanInputData> inputDataList = convertRawDataToInputData(getRawData(stripID, stand), stripID, stand);
            saveInputDataToTxt(inputDataList, fileName + "/Model/", stripID, stand);
            lastComputeTime = executeOrowan(fileName + "/Model/", stripID, stand);
            ArrayList<OrowanOutputData> outputDataList = loadOutputDataFromFile(fileName + "/Model/", Integer.parseInt(stripID), stand);
            // Adding Roll speed information to OrowanOutputData
            for(int i = 0; i < inputDataList.size(); i++) {
                outputDataList.get(i).setRollSpeed(inputDataList.get(i).getRollSpeed());
            }
            return outputDataList;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * From Orowan Output Data, return Processed output data.
     * @param orowanOutputData Les sortie d'Orowan.exe sous forme d'une ArrayList d'objets OrowanOutputData.
     * @param stripID Identifiant de la bande étudiée.
     * @param stand Nom du stand étudié.
     * @return Processed Orowan output data.
     */
    public static ArrayList<ProcessedOutputData> getProcessedOutputData(ArrayList<OrowanOutputData> orowanOutputData, String stripID, String stand) {
        ArrayList<ProcessedOutputData> processedOutputData = new ArrayList<>();
        int k = 0;
        double avgFriction = 0;
        double avgRollingSpeed = 0;
        double avgSigma = 0;
        for(int i = 0; i < orowanOutputData.size(); i++) {
            k++;
            avgFriction += orowanOutputData.get(i).getFriction();
            avgRollingSpeed += orowanOutputData.get(i).getRollSpeed();
            avgSigma += orowanOutputData.get(i).getSigmaMoy();
            if(k >= 5) {
                avgFriction /= 5d;
                avgRollingSpeed /= 5d;
                avgSigma /= 5d;

                processedOutputData.add(new ProcessedOutputData(avgRollingSpeed, avgSigma, avgFriction, stand, Integer.parseInt(stripID)));
                k = 0;
                avgFriction = 0;
                avgRollingSpeed = 0;
                avgSigma = 0;
            }
        }

        return processedOutputData;
    }

    /**
     * Renvoie les RawData du stand et stripID selectionné.
     */
    public static ArrayList<RawData> getRawData(String stripID, String stand) {

        String currentDirectory = System.getProperty("user.dir");
        String fileName = currentDirectory + "/FichiersOrowan";
        return RawData.loadRawDataFromFile(fileName + "/Krakov/" + stripID + "_" + stand + ".txt");
    }

    /**
     * Ouvre le fichier des données brutes à partir du stripID et du stand et convertie ces RawData
     * en OrowanInputData. Renvoie la liste d'objets OrowanInputData correspondante.
     */
    public static ArrayList<OrowanInputData> convertRawDataToInputData(ArrayList<RawData> rawData, String stripID, String stand) {
        // Les RawData sont les données dans les 1939351_F2.txt
        // Les OrowanInputData sont les données dans inv_cst.txt
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
            orowanInputData.setRollSpeed(rawData.get(i).getWorkRollSpeed());
            inputData.add(orowanInputData);
        }
        return inputData;
    }

    /**
     * Créer et sauvegarde un fichier txt lisible par Orowan.exe en entrée, à partir d'une liste d'objets
     * OrowanInputData.
     */
    public static void saveInputDataToTxt(ArrayList<OrowanInputData> inputDataList, String fileName, String stripID, String stand) throws IOException {
        // Sauvegarder sous forme de input_1939351_F2.txt
        // Créer un BufferedWriter pour écrire les données dans le fichier
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "input_" + stripID + "_" + stand + ".txt"));

        // Entêtes
        writer.write("Cas\tHe\tHs\tTe\tTs\tDiam_WR\tWRyoung\toffset ini\tmu_ini\tForce\tG\n");

        // Valeurs
        for (OrowanInputData inputData : inputDataList) {
            writer.write(inputData.getCas() + "\t" + inputData.getHe() + "\t" + inputData.getHs() + "\t"
                    + inputData.getTe() + "\t" + inputData.getTs() + "\t" + inputData.getDiam_WR() + "\t"
                    + inputData.getWRyoung() + "\t" + inputData.getOffset_ini() + "\t" + inputData.getMu_ini()
                    + "\t" + inputData.getForce() + "\t" + inputData.getG() + "\n");
        }

        writer.close();
    }

    /**
     * Execute le programme Orowan.exe et renvoie le temps d'execution.
     * @return Compute Time
     */
    public static long executeOrowan(String fileName, String stripID, String stand) {
        long startTime = System.currentTimeMillis();
        try {
            // Création du process builder pour exécuter le fichier .exe
            ProcessBuilder pb = new ProcessBuilder(fileName + "Orowan_x64.exe.exe");
            pb.redirectErrorStream(true); // Redirige la sortie d'erreur vers la sortie standard

            // Démarrage du processus
            Process process = pb.start();

            OutputStream outputStream = process.getOutputStream();
            String input = "i" +
                    "\nc" +
                    "\n" + fileName + "input_" + stripID + "_" + stand + ".txt" +
                    "\n" + fileName + "output_" + stripID + "_" + stand + ".txt\n";
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
     * Charge les données de sortie du programme Orowan.exe et revoie la liste d'objets OrowanOutputData
     * correspondante.
     */
    public static ArrayList<OrowanOutputData> loadOutputDataFromFile(String filename, int stripID, String stand) throws IOException {
        ArrayList<OrowanOutputData> dataList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename + "output_" + stripID + "_" + stand + ".txt"));

        String line;

        reader.readLine(); // skip first line (headers)
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\t");
            int matId = stripID;
            int caseId = Integer.parseInt(parts[0]);
            String errors = parts[1];
            double offsetYield = Double.parseDouble(parts[2]);
            double friction = Double.parseDouble(parts[3]);
            double rollingTorque = Double.parseDouble(parts[4]);
            double sigmaMoy = Double.parseDouble(parts[5]);
            double sigmaIni = Double.parseDouble(parts[6]);
            double sigmaOut = Double.parseDouble(parts[7]);
            double sigmaMax = Double.parseDouble(parts[8]);
            double forceError = Double.parseDouble(parts[9]);
            double slipError = Double.parseDouble(parts[10]);
            boolean hasConverged = parts[11].equals("YES");
            dataList.add(new OrowanOutputData(stand, matId, caseId, errors, offsetYield, friction, rollingTorque, sigmaMoy, sigmaIni, sigmaOut, sigmaMax,
                    forceError, slipError, hasConverged));
        }

        reader.close();
        return dataList;
    }



}