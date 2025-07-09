package com.surendra.honeywelltask.data.resporsitory;

import android.util.Log;

import com.surendra.honeywelltask.data.model.SNMPRequest;
import com.surendra.honeywelltask.domain.SnmpManager;
import com.surendra.honeywelltask.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SNMPRepository {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public interface ResultCallback {
        void onResult(String result);
    }

    public void fetch(SNMPRequest request, ResultCallback callback) {
        executorService.execute(() -> {
            try {
                SnmpManager manager = new SnmpManager(
                        request.ip, request.port, request.username,
                        request.authPassword, request.authProtocol,
                        request.privPassword, request.privProtocol
                );
                String response = manager.get(request.oid);
                manager.close();
                Log.d(Constants.TAG, "SNMP GET Response: " + response);
                callback.onResult(response);
            } catch (Exception e) {
                Log.e(Constants.TAG, "SNMPRepository Exception: " + e.getMessage());
                callback.onResult("Exception: " + e.getMessage());
            }
        });
    }

    public void clear() {
        executorService.shutdown();
    }
}
