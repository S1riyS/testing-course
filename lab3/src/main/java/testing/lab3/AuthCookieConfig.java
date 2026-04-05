package testing.lab3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public record AuthCookieConfig(String cookieName, String cookieDomain, String cookiePath, String tokenEnvVar) {
    public static AuthCookieConfig load() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream in = AuthCookieConfig.class.getResourceAsStream("/properties.yaml")) {
            if (in == null) {
                throw new IllegalStateException("Classpath resource /properties.yaml not found");
            }
            return mapper.readValue(in, AuthCookieConfig.class);
        }
    }

    public String cookieValue() {
        String name = Objects.requireNonNull(tokenEnvVar, "tokenEnvVar");
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Environment variable '" + name + "' is missing or empty (session token)");
        }
        return value;
    }

    public void applySessionCookie(WebDriver driver) {
        Cookie.Builder builder = new Cookie.Builder(
                Objects.requireNonNull(cookieName, "cookieName"),
                cookieValue())
                .path(cookiePath != null && !cookiePath.isEmpty() ? cookiePath : "/");
        if (cookieDomain != null && !cookieDomain.isBlank()) {
            builder.domain(cookieDomain);
        }
        driver.manage().addCookie(builder.build());
        driver.navigate().refresh();
    }
}
