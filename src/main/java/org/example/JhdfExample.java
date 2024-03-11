package org.example;

import io.jhdf.HdfFile;
import io.jhdf.api.Dataset;
import io.jhdf.api.Group;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.IntSummaryStatistics;

public class JhdfExample {

    public static void main(String[] args) {
        System.out.println("Hello jHDF!");

        // Auto-closable
        try (HdfFile hdfFile = new HdfFile(Path.of("/home/james/git/jhdf-webinar/hdf5-example/webinar_demo.hdf5"))) {
            // Just check things look right
            System.out.println(hdfFile.getChildren());

            // Read the first dataset
            Dataset ints = hdfFile.getDatasetByPath("ints");
            System.out.println(ArrayUtils.toString(ints.getData()));

            // Use the loaded data for something
            int[] intsData = (int[]) ints.getData();
            IntSummaryStatistics intSummaryStatistics = Arrays.stream(intsData).summaryStatistics();
            System.out.println(intSummaryStatistics);

            // Read a more complex dataset
            Group someGroup = (Group) hdfFile.getChild("some_group");
            Dataset byteshuffleDataset = (Dataset) someGroup.getChild("byteshuffle");

            // Read some metadata about the dataset
            System.out.println(byteshuffleDataset.getFilters());
            System.out.println(ArrayUtils.toString(byteshuffleDataset.getDimensions()));
            System.out.println(byteshuffleDataset.getSizeInBytes());
            System.out.println("Data type size bits: " + byteshuffleDataset.getDataType().getSize() * 8);
            System.out.println("Compression Ratio: " + byteshuffleDataset.getSizeInBytes() * 1.0 / byteshuffleDataset.getStorageInBytes());

            Object data = byteshuffleDataset.getData();
            System.out.println(ArrayUtils.toString(data));
            // Or for potential integration
            Object flatData = byteshuffleDataset.getDataFlat();
            System.out.println(ArrayUtils.toString(flatData));

            // Slice some data
            Dataset dataset = (Dataset) someGroup.getChild("2d_data");
            Object slice = dataset.getData(new long[]{1, 0}, new int[]{2, 5});
            System.out.println(ArrayUtils.toString(slice));

            // Compound dataset example - I think maybe some API improvement here
            Dataset peopleDataset = hdfFile.getDatasetByPath("/people");
            Object peopleData = peopleDataset.getData();
            System.out.println(peopleData);

        }

    }
}