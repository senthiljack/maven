package com.recodesolutions.itticket.helper;


import com.google.common.hash.Hashing;
import com.microsoft.aad.msal4j.*;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MailTokenGenerator {

    @Value("${security.oauth2.client.authority}")
    private String authority;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String secret;

    @Value("${aad.graphDefaultScope}")
    private String scope;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    private final CacheManager cacheManager;




    public String getAuthorizationTokenAsync(String token) {

        String authToken = token;
        String cacheKey = Hashing.sha256().hashString(authToken, StandardCharsets.UTF_8).toString();

        IAuthenticationResult authResult;
        ConfidentialClientApplication application;
        try {
            application = ConfidentialClientApplication
                    .builder(clientId, ClientCredentialFactory.createFromSecret(secret))
                    .authority(authority)
                    .build();

            String cachedTokens = cacheManager.getCache("tokens").get(cacheKey, String.class);
            if (cachedTokens != null) {
                application.tokenCache().deserialize(cachedTokens);
            }

            OnBehalfOfParameters parameters =
                    OnBehalfOfParameters.builder(Collections.singleton(scope),
                                    new UserAssertion(authToken))
                            .build();
            authResult = application.acquireToken(parameters).join();

        } catch (Exception ex) {
            throw new AuthException(String.format("Error acquiring token from AAD: %s", ex.getMessage()),
                    ex.getCause());
        }

        cacheManager.getCache("tokens").put(cacheKey, application.tokenCache().serialize());
        return authResult.accessToken();
    }



}
