package com.surendra.honeywelltask.domain;

import android.util.Log;

import com.surendra.honeywelltask.utils.Constants;

import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;

public class SnmpManager {
    private Snmp snmp;
    private UserTarget target;

    public SnmpManager(String ip, int port, String username,
                       String authPass, String authProto,
                       String privPass, String privProto) throws IOException {

        Address address = GenericAddress.parse("udp:" + ip + "/" + port);
        TransportMapping<?> transport = new DefaultUdpTransportMapping();
        snmp = new Snmp(transport);

        OID authOID = "SHA".equalsIgnoreCase(authProto) ? AuthSHA.ID : AuthMD5.ID;
        OID privOID = "AES".equalsIgnoreCase(privProto) ? PrivAES128.ID : PrivDES.ID;

        SecurityProtocols.getInstance().addDefaultProtocols();
        SecurityModels.getInstance().addSecurityModel(new USM(
                SecurityProtocols.getInstance(),
                new OctetString(MPv3.createLocalEngineID()), 0));

        snmp.getUSM().addUser(
                new OctetString(username),
                new UsmUser(new OctetString(username),
                        authOID, new OctetString(authPass),
                        privOID, new OctetString(privPass))
        );

        transport.listen();

        target = new UserTarget();
        target.setAddress(address);
        target.setRetries(Constants.RETRIES);
        target.setTimeout(Constants.TIMEOUT);
        target.setVersion(SnmpConstants.version3);
        target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
        target.setSecurityName(new OctetString(username));
    }

    public String get(String oidStr) {
        try {
            PDU pdu = new ScopedPDU();
            pdu.setType(PDU.GET);
            pdu.add(new VariableBinding(new OID(oidStr)));
            ResponseEvent event = snmp.send(pdu, target);
            if (event != null && event.getResponse() != null) {
                return event.getResponse().get(0).getVariable().toString();
            } else {
                return "No Response from agent.";
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "SNMP Get Exception: " + e.getMessage());
            return "SNMP Error: " + e.getMessage();
        }
    }

    public void close() throws IOException {
        snmp.close();
    }
}
