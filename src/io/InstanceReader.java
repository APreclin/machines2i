package io;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * Classe permettant de lire une instance pour le projet Machines2i
 * 
 * Les instances sont au format ".txt".
 */

public class InstanceReader {
    /**
     * Le fichier contenant l'instance.
     */
    private File instanceFile;

    public InstanceReader() {
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);

        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        this.instanceFile = file;
    }

    public static void main(String[] args) {
        InstanceReader reader = new InstanceReader();
    }
}
