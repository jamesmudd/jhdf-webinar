package org.example;

import io.jhdf.HdfFile;
import io.jhdf.api.Dataset;
import io.jhdf.api.Group;
import org.apache.commons.lang3.ArrayUtils;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

public class JhdfExample {

    public static void main(String[] args) {
        System.out.println("Hello jHDF!\n");

        // Auto-closable
        try (HdfFile hdfFile = new HdfFile(Path.of("/home/james/git/jhdf-webinar/hdf5-example/webinar_demo.hdf5"))) {
            // Just check things look right
            System.out.println(hdfFile.getChildren().entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue().getType())
                    .collect(Collectors.joining("\n")) + "\n");

            // Read the first dataset
            Dataset ints = hdfFile.getDatasetByPath("ints");
            System.out.println(ints.getPath() + " data: " + ArrayUtils.toString(ints.getData()));

            // Use the loaded data for something
            int[] intsData = (int[]) ints.getData();
            IntSummaryStatistics intSummaryStatistics = Arrays.stream(intsData).summaryStatistics();
            System.out.println("Summary stats: " + intSummaryStatistics);

            // Read attributes
            System.out.println("ints attributes: " +
                    ints.getAttributes().entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue().getData())
                            .findFirst().orElseThrow()
            + "\n");

            // Read a more complex dataset
            Group someGroup = (Group) hdfFile.getChild("some_group");
            Dataset byteshuffleDataset = (Dataset) someGroup.getChild("byteshuffle");

            // Read some metadata about the dataset
            System.out.println("byteshuffle filters: " + byteshuffleDataset.getFilters());
            System.out.println("byteshuffle dims: " + ArrayUtils.toString(byteshuffleDataset.getDimensions()));
            System.out.println("bbyteshuffle bytes: " + byteshuffleDataset.getSizeInBytes());
            System.out.println("Data type size bits: " + byteshuffleDataset.getDataType().getSize() * 8);
            System.out.println("Compression Ratio: " + byteshuffleDataset.getSizeInBytes() * 1.0 / byteshuffleDataset.getStorageInBytes());

            Object data = byteshuffleDataset.getData();
            System.out.println("byteshuffle: " + ArrayUtils.toString(data));
            // Or for potential integration
            Object flatData = byteshuffleDataset.getDataFlat();
            System.out.println("Flat data: " + ArrayUtils.toString(flatData));

            // Slice some data
            Dataset dataset = (Dataset) someGroup.getChild("2d_data");
            Object slice = dataset.getData(new long[]{1, 0}, new int[]{2, 5});
            System.out.println("\n2d_data slice 2x5 from (1, 0): " + ArrayUtils.toString(slice));

            // Compound dataset example - I think maybe some API improvement here
            Dataset peopleDataset = hdfFile.getDatasetByPath("/people");
            System.out.println("\npeople compound dataset: " + peopleDataset.getDataType());
            Map<String, Object> peopleData = (Map<String, Object>) peopleDataset.getData();
            System.out.println("people keys: " + peopleData.keySet());
            System.out.println("people firstName: " + ArrayUtils.toString(peopleData.get("firstName")));
            System.out.println("people age: " + ArrayUtils.toString(peopleData.get("age")));
            System.out.println("people vector: " + ArrayUtils.toString(peopleData.get("vector")));
        }
    }
}