package com.cloudbees.jenkins.plugins.bitbucket.api.credentials;

import com.cloudbees.jenkins.plugins.bitbucket.api.BitbucketAuthenticator;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import hudson.util.Secret;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

/**
 * Authenticator that uses a username and password (probably the default)
 */
public class BitbucketUsernamePasswordAuthenticator extends BitbucketAuthenticator {

    private UsernamePasswordCredentials httpCredentials;

    /**
     * Constructor.
     * @param credentials the username/password that will be used
     */
    public BitbucketUsernamePasswordAuthenticator(StandardUsernamePasswordCredentials credentials) {
        super(credentials);
        httpCredentials = new UsernamePasswordCredentials(credentials.getUsername(),
                Secret.toString(credentials.getPassword()));
    }

    /**
     * Sets up HTTP Basic Auth with the provided username/password
     *
     * @param context The connection context
     * @param host host being connected to
     */
    @Override
    public void configureContext(HttpClientContext context, HttpHost host) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, httpCredentials);
        AuthCache authCache = new BasicAuthCache();
        authCache.put(host, new BasicScheme());
        context.setCredentialsProvider(credentialsProvider);
        context.setAuthCache(authCache);
    }
}
