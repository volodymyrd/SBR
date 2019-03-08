package com.gmail.volodymyrdotsenko.sbr;

import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.AccessToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Volodymyr Dotsenko on 2019-03-08.
 */
@RestController
@RequestMapping("/fe")
public class MainRestController {

  public enum GcpScope {
    /**
     * scope for Pub/Sub.
     **/
    PUBSUB("https://www.googleapis.com/auth/pubsub"),

    /**
     * scope for Spanner database admin functions.
     **/
    SPANNER_ADMIN("https://www.googleapis.com/auth/spanner.admin"),

    /**
     * scope for Spanner data read and write.
     **/
    SPANNER_DATA("https://www.googleapis.com/auth/spanner.data"),

    /**
     * scope for Datastore.
     **/
    DATASTORE("https://www.googleapis.com/auth/datastore"),

    /**
     * scope for SQL Admin.
     **/
    SQLADMIN("https://www.googleapis.com/auth/sqlservice.admin"),

    /**
     * scope for Storage read-only.
     **/
    STORAGE_READ_ONLY("https://www.googleapis.com/auth/devstorage.read_only"),

    /**
     * scope for Storage read-write.
     **/
    STORAGE_READ_WRITE("https://www.googleapis.com/auth/devstorage.read_write"),

    /**
     * scope for Runtime Configurator.
     **/
    RUNTIME_CONFIG_SCOPE("https://www.googleapis.com/auth/cloudruntimeconfig"),

    /**
     * scope for Trace.
     **/
    TRACE_APPEND("https://www.googleapis.com/auth/trace.append"),

    /**
     * scope for GCP general operations.
     **/
    CLOUD_PLATFORM("https://www.googleapis.com/auth/cloud-platform"),

    /**
     * scope for Vision.
     **/
    CLOUD_VISION("https://www.googleapis.com/auth/cloud-vision");

    private String url;

    GcpScope(String url) {
      this.url = url;
    }

    public String getUrl() {
      return this.url;
    }
  }

  private static final Logger LOG = LoggerFactory.getLogger(MainRestController.class);

  private static final List<String> CREDENTIALS_SCOPES_LIST = Collections.unmodifiableList(
      Arrays.stream(GcpScope.values())
          .map(GcpScope::getUrl)
          .collect(Collectors.toList()));

  @RequestMapping("/token1")
  public ResponseEntity<?> token1() {
    LOG.info("token1 run...");
    try {
      ArrayList<String> scopes = new ArrayList<String>();
      scopes.add("https://www.googleapis.com/auth/sql");
      final AppIdentityService appIdentity = AppIdentityServiceFactory.getAppIdentityService();
      final AppIdentityService.GetAccessTokenResult accessToken = appIdentity
          .getAccessToken(scopes);
      LOG.info("token:{}", accessToken.getAccessToken());
      return ResponseEntity.ok(accessToken);
    } catch (Exception ex) {
      LOG.error("Error obtained the token:", ex);
      return ResponseEntity.ok("Error obtained the token:" + ex.getMessage());
    }
  }

  @RequestMapping("/token2")
  public ResponseEntity<?> token2() {
    LOG.info("token2 run...");
    try {
//      ArrayList<String> scopes = new ArrayList<String>();
//      scopes.add("https://www.googleapis.com/auth/sql");
//      GoogleCredentialsProvider.newBuilder()
//          .setScopesToApply(scopes)
//          .build()
//          .getCredentials();
          //.getRequestMetadata();
      AppEngineCredentials.getApplicationDefault().getRequestMetadata();
      AccessToken accessToken = AppEngineCredentials.getApplicationDefault().getAccessToken();
      if (accessToken != null) {
        return ResponseEntity.ok(accessToken.getTokenValue());
      } else {
        LOG.error("Error obtaining a token");
        return ResponseEntity.ok("Error obtaining a token");
      }
    } catch (Exception e) {
      LOG.error("Error obtaining a token", e);
      return ResponseEntity.ok("Error obtaining a token" + e.getMessage());
    }
  }
}
