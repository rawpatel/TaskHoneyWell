package com.surendra.honeywelltask.data.resporsitory;

import android.util.Log;

import com.surendra.honeywelltask.data.model.SNMPRequest;
import com.surendra.honeywelltask.domain.SnmpManager;
import com.surendra.honeywelltask.utils.Constants;
import com.surendra.honeywelltask.utils.ServiceExecutors;


public class SNMPRepository {

    private final ServiceExecutors executors = ServiceExecutors.getInstance();

    public interface ResultCallback {
        void onResult(String result);
    }

    public void fetch(SNMPRequest request, ResultCallback callback) {
        executors.networkIO().execute(() -> {
            try {
                SnmpManager manager = new SnmpManager(
                        request.ip, request.port, request.username,
                        request.authPassword, request.authProtocol,
                        request.privPassword, request.privProtocol
                );
                String response = manager.get(request.oid);
                manager.close();
                executors.mainThread().execute(() -> {
                    Log.d(Constants.TAG, "SNMP GET Response: " + response);
                    callback.onResult(response);
                });
            } catch (Exception e) {
                Log.e(Constants.TAG, "SNMPRepository Exception: " + e.getMessage());
                String error = "Exception: " + e.getMessage();
                executors.mainThread().execute(() -> callback.onResult(error));
            }
        });
    }

    public void clear() {
        // No-op for now; could shut down executors if needed
    }
}
