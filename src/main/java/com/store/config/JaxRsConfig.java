package com.store.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class JaxRsConfig extends Application {
    // La configuration est maintenant dans web.xml
}