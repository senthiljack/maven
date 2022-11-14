package com.recodesolutions.itticket.interpreter;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;


import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

@Getter
@Setter
public class AzureOAuthJwtToken {

    protected final String token;


    // Payload
    protected String issuer;
    protected String ipAddr;
    protected String lastName;
    protected String firstName;
    protected String name;
    protected String uniqueName;
    protected String userName;
    protected String appId;
    protected String email;
    protected List<String> roles;
    //
    
    public AzureOAuthJwtToken(String token) {
        this.token = token;

        String[] parts = token.split("\\.");
        String payloadStr = new String(Base64.getUrlDecoder().decode((parts[1])));

        JSONObject payload = new JSONObject(payloadStr);

        issuer = payload.getString("iss");
        if (!payload.isNull("ipaddr"))
        	ipAddr = payload.getString("ipaddr");
        
        if (!payload.isNull("family_name"))
        	lastName = payload.getString("family_name");
        
        if (!payload.isNull("given_name"))
        	firstName = payload.getString("given_name");
        
        if (!payload.isNull("unique_name"))
        	uniqueName = payload.getString("unique_name");

        if (!payload.isNull("email"))
            email = payload.getString("email");

        if (!payload.isNull("name"))
            name = payload.getString("name");

        if (!payload.isNull("roles"))
            roles = payload.getJSONArray("roles").toList().stream().map(r-> r.toString()).collect(Collectors.toList());
        
    }

}