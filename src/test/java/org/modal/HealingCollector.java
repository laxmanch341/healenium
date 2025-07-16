package org.modal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.modal.HealingRecord;

import java.io.FileWriter;

public class HealingCollector {

    private static final HealingCollector INSTANCE = new HealingCollector();
    private final List<HealingRecord> records = new ArrayList<>();
    private int healedCount = 0;

    private HealingCollector() {}

    public static HealingCollector getInstance() {
        return INSTANCE;
    }

    public void addRecord(HealingRecord record) {
        records.add(record);
        if ("success".equals(record.status)) {
            healedCount++; // Increment the global count if the healing was successful
        }
    }

    public int getHealedCount() {
        return healedCount;
    }

    public void saveToJson(String path) {
        JSONArray array = new JSONArray();
        for (HealingRecord r : records) {
            array.put(r.toJson());
        }

        try (FileWriter fw = new FileWriter(path)) {
            fw.write(array.toString(2));
            System.out.println("Saved healing data to: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        records.clear();
    }

    public void clearRecordsForTest(String testName) {
        records.removeIf(r -> r.testName.equals(testName));
    }

    public List<HealingRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }
}

