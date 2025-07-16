package org.modal;

import org.json.JSONObject;

import java.time.Instant;

public class HealingRecord {
    public String testName;
    public String original;
    public String healed;
    public double confidence;
    public String status;
    public String timestamp;

    public HealingRecord(String testName, String original, String healed, double confidence, String status) {
        this.testName = testName;
        this.original = original;
        this.healed = healed;
        this.confidence = confidence;
        this.status = status;
        this.timestamp = Instant.now().toString();
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("testName", testName);
        obj.put("original", original);
        obj.put("healed", healed != null ? healed : JSONObject.NULL);
        obj.put("confidence", confidence);
        obj.put("status", status);
        obj.put("timestamp", timestamp);
        return obj;
    }
}

