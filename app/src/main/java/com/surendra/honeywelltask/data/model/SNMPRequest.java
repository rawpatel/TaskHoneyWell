package com.surendra.honeywelltask.data.model;

public class SNMPRequest {
    public String ip;
    public int port;
    public String username;
    public String authPassword;
    public String authProtocol;
    public String privPassword;
    public String privProtocol;
    public String oid;

    public SNMPRequest(String ip, int port, String username, String authPassword,
                       String authProtocol, String privPassword, String privProtocol, String oid) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.authPassword = authPassword;
        this.authProtocol = authProtocol;
        this.privPassword = privPassword;
        this.privProtocol = privProtocol;
        this.oid = oid;
    }
}
