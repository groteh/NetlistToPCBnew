/**
 * Write a description of class NetlistToPCBnew here.
 *
 * @author Henry Grote
 * @version (a version number or a date)
 */
//import packages
import java.util.*;
import java.lang.*;
import java.util.concurrent.TimeUnit;

public class NetlistToPCBnew
{
    //private static instance variables, to be used throughout the programme
    public static final String lineSep = System.getProperty("line.separator");
    public NetlistToPCBnew() {

    }

    public static int netsCounter(String input) {
        //counts number of nets
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            if (i > input.length() - 8) break;
            if (input.substring(i, i+8).equals("*SIGNAL*")) result++;
        }
        return result;
    }

    public static String[] compValues(String input) {
        int numParts = partsCounter(input);
        String[] result = new String[numParts];
        int startNet = 0;
        int[] partStart = new int[numParts];
        for (int i = 0; i < numParts; i++) {
            partStart[i] = partBegin(input)[i];
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i+ 5).equals("*NET*")) {
                startNet = i;
                break;
            }
        }

        for (int i = 0; i < numParts - 1; i++) {
            result[i] = input.substring(partStart[i] + 2, partStart[i + 1]);
            if (result[i].equals("")) result[i] = "null";
        }
        result[numParts - 1] = input.substring(partStart[numParts - 1] + 2 ,startNet);
        if (result[numParts - 1].equals("")) result[numParts - 1] = "null";
        return result;
    }

    public static int partsCounter(String input) { //counts number of parts in input
        int numNets = netsCounter(input);
        ArrayList<String> parts = new ArrayList<String>();
        ArrayList<String> manyParts = new ArrayList<String>();
        ArrayList<String>[] nodesInNets = new ArrayList[numNets];
        for (int i = 0; i < numNets; i++) { //innitializes nodesInNets
            nodesInNets[i] = new ArrayList<String>();
        }
        nodesInNets = outputNodesInNets(input);

        for (int i = 0; i < numNets; i++) { //adds parts to manyParts
            for (int j = 1; j < nodesInNets[i].size(); j++) {
                manyParts.add(nodesInNets[i].get(j).substring(0, 2));
            }
        }

        for (int i = 0; i < manyParts.size(); i++) {
            if (!parts.contains(manyParts.get(i))) {
                parts.add(manyParts.get(i));
            }
        }
        return parts.size();
    }

    public static int[] partBegin(String input) { //returns index of the first char of part name for each part
        int result[] = new int[partsCounter(input)];
        int numNets = netsCounter(input);
        ArrayList<String> parts = new ArrayList<String>();
        ArrayList<String> manyParts = new ArrayList<String>();
        ArrayList<String>[] nodesInNets = new ArrayList[numNets];
        int resultCounter = 0;
        int q = 0;
        for (int i = 0; i < numNets; i++) { //innitializes nodesInNets
            nodesInNets[i] = new ArrayList<String>();
        }
        nodesInNets = outputNodesInNets(input);

        for (int i = 0; i < numNets; i++) { //adds parts to manyParts
            for (int j = 1; j < nodesInNets[i].size(); j++) {
                manyParts.add(nodesInNets[i].get(j).substring(0, 2));
            }
        }

        for (int i = 0; i < manyParts.size(); i++) {
            if (!parts.contains(manyParts.get(i))) {
                parts.add(manyParts.get(i));
            }
        }

        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i+ 5).equals("*NET*")) {
                q = i;
                break;
            }
        }
        input = input.substring(0, q);

        for (int i = 0; i < input.length(); i++) {
            for (int j = 0; j < parts.size(); j++) {
                if (input.substring(i).length() < 3) break;
                if (input.substring(i, i + 2).equals(parts.get(j))) {
                    result[resultCounter] = i;
                    resultCounter++;
                }
            }
        }
        return result;
    }

    public static ArrayList<String>[] outputNodesInNets (String input) {
        //takes user input, creates array of array lists, array of nets, array lists of components attatched to the node, first element is name of node
        int l = netsCounter(input); // l = number of nets
        ArrayList<String>[] result = new ArrayList[l]; //creates result
        ArrayList<Integer> startOfNet = new ArrayList<Integer>(); //array of indicies of the start of each net
        ArrayList<Integer> endOfNetName = new ArrayList<Integer>(); //array of indicies of the end of the net name
        int netCounter123 = 0;

        for (int i = 0; i < l; i++) { //innitializes the array, result, with ArrayLists inside of each index of the array
            result[i] = new ArrayList<String>();
        }

        for (int i = 0; i < input.length(); i++) { //fills out startOfNet with first index of NET 
            if (i > input.length() - 8) break;
            if (input.substring(i, i+8).equals("*SIGNAL*")) {
                startOfNet.add(i);
            }
        }

        for (int i = 0; i < input.length(); i++) { //fills out endOfNet ArrayList with the last index of the net name
            boolean netAlreadyCounted = false;
            if (i > input.length() - 3) break;
            if (netCounter123 > startOfNet.size() - 1) break;
            if (input.substring(i, i+1).equals(".") && i > startOfNet.get(netCounter123) && netAlreadyCounted == false) {
                endOfNetName.add(i-3);
                netCounter123++;
                netAlreadyCounted = true;
                i++;
            }
            if (input.substring(i, i+1).equals(".") && i > startOfNet.get(netCounter123) && netAlreadyCounted == true) {
                netAlreadyCounted = false;
                i = i - 1;
            }
        }

        for (int i = 0; i < l - 1; i++) { //assembles result
            int placeInResultCounter = 1;
            result[i].add(input.substring(startOfNet.get(i) + 9, endOfNetName.get(i) + 1)); //adds first index as name of node
            for (int j = startOfNet.get(i); j < startOfNet.get(i+1); j++) {
                if (input.substring(j, j + 1).equals(".")) {
                    result[i].add(input.substring(j - 2, j + 2));
                    placeInResultCounter++;
                }
            }
            placeInResultCounter = 1;
        }
        result[l - 1].add(input.substring(startOfNet.get(l - 1) + 9, endOfNetName.get(l - 1) + 1));

        for (int j = endOfNetName.get(l - 1); j < endOfNetName.get(l - 1) + input.substring(endOfNetName.get(l - 1)).length(); j++) {
            if (input.substring(j, j+1).equals(".")) {
                result[l - 1].add(input.substring(j - 2, j + 2));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        //recieve input
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter the text for the netlist (PADS) you wish to convert.");
        String input = userInput.nextLine(); //sets String input to netlist entered

        //instancing variables
        Libraries library = new Libraries();
        Components component = new Components();
        libpartLibary libpart = new libpartLibary();
        Random rand = new Random();

        int numberOfNets = netsCounter(input); //instanciating my variables
        ArrayList<String>[] netsArray = new ArrayList[numberOfNets]; //creates array of arraylits nets -> netname, nodes
        netsArray = outputNodesInNets(input);
        int numberOfParts = partsCounter(input);
        int[] partStart = new int[numberOfParts];
        for (int i = 0; i < numberOfParts; i++) {
            partStart[i] = partBegin(input)[i];
        }
        String[] compValue = new String[numberOfParts];
        for (int i = 0; i < numberOfParts; i++) {
            compValue[i] = compValues(input)[i];
        }     
        
        char temp;
        int tempint = 0;
        String tempstr;

        /**build PCBnew header */
        System.out.println("(export (version D)");
        //System.out.println("Done Header");
        /**build PCBnew components */
        //System.out.println("Start Components");
        System.out.println("  (components");
        for (int i = 0; i < numberOfParts; i++) {
            if (!compValue[i].equals("V")) {
                System.out.println("    (comp (ref " + input.substring(partStart[i], partStart[i] + 2) + ")");
            }
            if (!compValue[i].equals("V")) {
                System.out.println("      (value " + compValue[i] + ")");
            }
            if (input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("R")  && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                System.out.println(component.componentTree.get("resistor"));
            }
            else if (input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("C") && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                System.out.println(component.componentTree.get("capacitor"));
            }
            else if (input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("L") && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                System.out.println(component.componentTree.get("inductor"));
            }
            else if (input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("V") && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                temp = input.charAt(partStart[i] + 1);
                //System.out.println("    (comp (ref V" + temp + ")" + lineSep + "      (value Battery)");
                System.out.println(component.componentTree.get("battery"));
            }
            else {
                System.out.println(component.componentTree.get(compValue[i]));
            }
            tempint = rand.nextInt(90000) + 10000;
            if (i == numberOfParts - 1) {
                System.out.println("      (sheetpath (names /) (tstamps /))" + lineSep + "      (tstamp 5F3" + tempint + ")))");
                break;
            }
            else {
                System.out.println("      (sheetpath (names /) (tstamps /))" + lineSep + "      (tstamp 5F3" + tempint + "))");
            }
        }
        //System.out.println("Done Components");
        
        /**build PCBnew libparts */
        System.out.println("  (libparts");
        /*for (int i = 0; i < numberOfParts - 1; i++) {
            if (i > 0) System.out.println();
            if (libpart.alreadyPrinted.get("resistor") == false && input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("R")  && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                libpart.alreadyPrinted.put("resistor", false);
                System.out.print(libpart.libparts.get("resistor"));
            }
            else if (libpart.alreadyPrinted.get("capacitor") == false && input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("C") && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                libpart.alreadyPrinted.put("capacitor", false);
                System.out.print(libpart.libparts.get("capacitor"));
            }
            else if (libpart.alreadyPrinted.get("inductor") == false && input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("L") && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                libpart.alreadyPrinted.put("inductor", false);
                System.out.print(libpart.libparts.get("inductor"));
            }
            else if (libpart.alreadyPrinted.get("battery") == false && input.substring(partStart[i], partStart[i] + 1).toUpperCase().equals("V") && input.substring(partStart[i] + 1, partStart[i] + 2).matches("[0-9]+")) {
                libpart.alreadyPrinted.put("battery", false);
                System.out.print(libpart.libparts.get("battery"));
            }
            else if (libpart.alreadyPrinted.get(input.substring(partStart[i] + 2, partStart[i+1])) == false) {
                libpart.alreadyPrinted.put(input.substring(partStart[i] + 2, partStart[i+1]), true);
                System.out.print(libpart.libparts.get(input.substring(partStart[i] + 2, partStart[i+1])));
            }
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i+ 5).equals("*NET*")) {
                tempint = i;
                break;
            }
        }
        tempstr = input.substring(partStart[numberOfParts - 1] + 2, tempint);
        System.out.println();
        if (libpart.alreadyPrinted.get("resistor") == false && input.substring(partStart[numberOfParts - 1], partStart[numberOfParts - 1] + 1).toUpperCase().equals("R")  && input.substring(partStart[numberOfParts - 1] + 1, partStart[numberOfParts - 1] + 2).matches("[0-9]+")) {
            if (libpart.alreadyPrinted.get("resistor") == false) {
                System.out.print(libpart.libparts.get("resistor"));
            }
        }
        else if (libpart.alreadyPrinted.get("capacitor") == false && input.substring(partStart[numberOfParts - 1], partStart[numberOfParts - 1] + 1).toUpperCase().equals("C")  && input.substring(partStart[numberOfParts - 1] + 1, partStart[numberOfParts - 1] + 2).matches("[0-9]+")) {
            if (libpart.alreadyPrinted.get("capacitor") == false) {
                System.out.print(libpart.libparts.get("capacitor"));
            }
        }
        else if (libpart.alreadyPrinted.get("inductor") == false && input.substring(partStart[numberOfParts - 1], partStart[numberOfParts - 1] + 1).toUpperCase().equals("L")  && input.substring(partStart[numberOfParts - 1] + 1, partStart[numberOfParts - 1] + 2).matches("[0-9]+")) {
            if (libpart.alreadyPrinted.get("inductor") == false) {
                System.out.print(libpart.libparts.get("inductor"));
            }
        }
        else if (tempstr.equals("battery")) {
            if (libpart.alreadyPrinted.get("battery") == false) {
                System.out.print(libpart.libparts.get("battery"));
            }
        }
        else if (!tempstr.equals("resistor") && !tempstr.equals("capacitor") && !tempstr.equals("inductor") && !tempstr.equals("battery")) {
            if (false == false) {
                System.out.println();
                libpart.alreadyPrinted.put(input.substring(partStart[numberOfParts - 1] + 2, tempint), true);
                System.out.print(libpart.libparts.get(input.substring(partStart[numberOfParts - 1] + 2, tempint)));
            }
        }*/
        System.out.println(libpart.libparts.get("resistor"));
        System.out.println(libpart.libparts.get("capacitor"));
        System.out.println(libpart.libparts.get("inductor"));
        System.out.println(libpart.libparts.get("LM324"));
        //System.out.println(libpart.libparts.get("LM2901"));
        //System.out.println(libpart.libparts.get("LM319"));
        //System.out.println(libpart.libparts.get("NE555"));
        //System.out.println(libpart.libparts.get("BS107"));
        //System.out.println(libpart.libparts.get("BSS138"));
        System.out.print(libpart.libparts.get("battery"));
        

        System.out.println(")");

        /**build PCBnew libraries */
        System.out.println("  (libraries");
        System.out.println(library.opAmp);
        System.out.println(library.comparator);
        System.out.println(library.device);
        System.out.println(library.timer);
        System.out.print(library.transistor);
        System.out.println(")");

        /**build PCBnew nets */
        System.out.println("  (nets");
        for (int i = 0; i < numberOfNets; i++) {
            int q = i + 1;
            if (i != 0) System.out.println();
            System.out.println("    (net (code " + q + ") (name \"Net-(" + netsArray[i].get(0) + ")\")");
            for (int j = 1; j < netsArray[i].size(); j++) {
                System.out.print("      (node (ref " + netsArray[i].get(j).substring(0, 2) + ") (pin " + netsArray[i].get(j).substring(3) + "))");
                if (j != netsArray[i].size() - 1)System.out.println();
            }
            System.out.print(")");
        }
        System.out.print(")");

        //Finishes
        System.out.print(")");
    }
}