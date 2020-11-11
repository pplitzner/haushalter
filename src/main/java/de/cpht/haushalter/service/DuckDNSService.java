package de.cpht.haushalter.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

@Service
@Profile("pi")
@ConfigurationProperties(prefix = "duckdns")
public class DuckDNSService {

    private String domain;
    private String token;

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Scheduled(fixedRate = 300000) // 5min
    public void updateDns() throws IOException {
        final String URL_STRING = new StringBuilder
                ("https://www.duckdns.org/update?domains=")
                .append(domain)
                .append("&token=")
                .append(token)
                .append("&ip=")
                .toString();
        URL url = new URL(URL_STRING);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            // TODO verbose log DDNS update
        }
        br.close();
    }
}
