package org.example;

import io.jhdf.HdfFile;
import io.jhdf.WritableHdfFile;
import io.jhdf.api.WritableGroup;

import java.nio.file.Path;

public class JhdfWritingExample {

    public static void main(String[] args) {
        System.out.println("jHDF Writing Example ");

        // Auto-closable
        try (WritableHdfFile writableHdfFile = HdfFile.write(Path.of("/home/james/git/jhdf-webinar/hdf5-example/webinar_writing.hdf5"))) {
            // Add a group in the root
            WritableGroup intGroup = writableHdfFile.putGroup("intGroup");

            // Write a dataset into the group
            int[] intData1 = new int[]{-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5};
            intGroup.putDataset("intData1", intData1);

            // Write a multi dimensional dataset
            int[][] intData3 = new int[][]{
                    {-500, -412, -399, -211},
                    {-54, 7, 23, -34245},
                    {412, 5656575, 23, 9909}};
            intGroup.putDataset("intData3", intData3);

            // Write another group with a double dataset
            WritableGroup doubleGroup = writableHdfFile.putGroup("doubleGroup");
            double[] doubleData1 = new double[]{-3300000.0, 44000.0, 3.0, 10.0, 20.0};
            doubleGroup.putDataset("doubleData1", doubleData1);
        }
    }
}