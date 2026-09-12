package data;

import config.AppConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.prefs.Preferences;

/**
 * HTTP client for the Indian Rail API.
 *
 * RailSync accepts the complete HTTP API URL supplied by the provider.
 * If that URL contains a real value in /apikey/<value>/, RailSync can
 * derive the API credential and construct the other documented endpoints.
 * A documentation template containing <apikey> cannot make live requests
 * until the provider supplies a real credential.
 */
public class IndianRailApiClient {

    private static final String PREF_NODE = "RailSync";
    private static final String API_URL_PREF = "indianRailApiUrl";

    private static final Pattern API_KEY_PATTERN = Pattern.compile(
            "/apikey/([^/]+)(?:/|$)",
            Pattern.CASE_INSENSITIVE);

    private String apiHttpUrl;

    public IndianRailApiClient() {
        this.apiHttpUrl = loadStoredApiUrl();
        if (isBlank(this.apiHttpUrl)) {
            this.apiHttpUrl = AppConfig.INDIAN_RAIL_API_HTTP_URL;
        }
    }

    public IndianRailApiClient(String apiHttpUrl) {
        setApiHttpUrl(apiHttpUrl);
    }

    public void setApiHttpUrl(String apiHttpUrl) {
        String value = normalizeApiUrl(apiHttpUrl);
        validateApiUrlFormat(value);
        this.apiHttpUrl = value;
        saveStoredApiUrl(value);
    }

    public void clearApiHttpUrl() {
        this.apiHttpUrl = "";
        Preferences.userRoot().node(PREF_NODE).remove(API_URL_PREF);
    }

    public String getApiHttpUrl() {
        String stored = loadStoredApiUrl();
        if (!isBlank(stored)) {
            this.apiHttpUrl = stored;
        }
        return apiHttpUrl;
    }

    /**
     * True when a valid HTTP API URL has been stored. A URL containing
     * <apikey> is deliberately reported as not ready for live requests.
     */
    public boolean isConfigured() {
        String url = getApiHttpUrl();
        if (isBlank(url)) {
            return false;
        }
        if (url.contains("<apikey>") || url.contains("{apikey}")) {
            return false;
        }
        return isIndianRailApiUrl(url);
    }

    /**
     * Searches a station using the exact StationCodeOrName URL template
     * when supplied, or reconstructs it from the real key embedded in the
     * stored HTTP API URL.
     */
    public String searchStation(String searchText) throws Exception {
        validateConfigured();
        validateSearchText(searchText);

        String configuredUrl = getApiHttpUrl();
        String encodedSearch = encodePathSegment(searchText.trim());

        String endpoint;

        if (configuredUrl.contains("StationCodeOrName")) {
            endpoint = replaceSearchPlaceholder(
                    configuredUrl,
                    encodedSearch,
                    "SearchText");
        } else {
            endpoint = buildUrlFromKey(
                    "StationCodeOrName",
                    "apikey",
                    extractApiKey(configuredUrl),
                    "SearchText",
                    searchText.trim());
        }

        return get(endpoint);
    }

    public String autocompleteStation(String searchText) throws Exception {
        validateConfigured();
        validateSearchText(searchText);

        String configuredUrl = getApiHttpUrl();
        String endpoint;

        if (configuredUrl.contains("AutoCompleteStation")) {
            endpoint = replaceSearchPlaceholder(
                    configuredUrl,
                    encodePathSegment(searchText.trim()),
                    "StationCodeOrName");
        } else {
            endpoint = buildUrlFromKey(
                    "AutoCompleteStation",
                    "apikey",
                    extractApiKey(configuredUrl),
                    "StationCodeOrName",
                    searchText.trim());
        }

        return get(endpoint);
    }

    public String getTrainsAtStation(String stationCode) throws Exception {
        validateConfigured();
        validateSearchText(stationCode);

        return buildAndGet(
                "AllTrainOnStation",
                "apikey",
                extractApiKey(getApiHttpUrl()),
                "StationCode",
                stationCode.trim().toUpperCase());
    }

    public String getTrainInformation(String trainNumber) throws Exception {
        validateConfigured();
        validateSearchText(trainNumber);

        return buildAndGet(
                "TrainInformation",
                "apikey",
                extractApiKey(getApiHttpUrl()),
                "TrainNumber",
                trainNumber.trim());
    }

    public String getTrainSchedule(String trainNumber) throws Exception {
        validateConfigured();
        validateSearchText(trainNumber);

        return buildAndGet(
                "TrainSchedule",
                "apikey",
                extractApiKey(getApiHttpUrl()),
                "TrainNumber",
                trainNumber.trim());
    }

    public String getTrainsBetweenStations(
            String sourceCode,
            String destinationCode) throws Exception {

        validateConfigured();
        validateSearchText(sourceCode);
        validateSearchText(destinationCode);

        return buildAndGet(
                "TrainBetweenStation",
                "apikey",
                extractApiKey(getApiHttpUrl()),
                "From",
                sourceCode.trim().toUpperCase(),
                "To",
                destinationCode.trim().toUpperCase());
    }

    private String buildAndGet(String... parts) throws Exception {
        return get(buildUrlFromKey(parts));
    }

    private String buildUrlFromKey(String... parts) {
        if (parts == null || parts.length == 0) {
            throw new IllegalArgumentException("API endpoint cannot be empty.");
        }

        StringBuilder path = new StringBuilder();

        for (String part : parts) {
            if (part == null) {
                continue;
            }

            String value = part.trim();
            if (value.isEmpty()) {
                continue;
            }

            if (path.length() > 0) {
                path.append('/');
            }

            path.append(encodePathSegment(value));
        }

        path.append('/');

        return AppConfig.INDIAN_RAIL_API_BASE + path;
    }

    /**
     * Replaces the documented placeholder or the final value after a
     * parameter name in a supplied HTTP API URL.
     */
    private String replaceSearchPlaceholder(
            String template,
            String encodedValue,
            String parameterName) {

        String result = template.trim();

        result = result.replace(
                "<SearchText>", encodedValue);
        result = result.replace(
                "<StationCodeOrName>", encodedValue);

        if (result.contains("{" + parameterName + "}")) {
            result = result.replace(
                    "{" + parameterName + "}", encodedValue);
        }

        if (result.contains("<apikey>")) {
            throw new IllegalStateException(
                    "The supplied HTTP API URL still contains <apikey>. "
                    + "It is a documentation template, not a live credentialed URL.");
        }

        if (result.endsWith("/")) {
            return result;
        }

        return result + "/";
    }

    private String extractApiKey(String url) {
        if (isBlank(url)) {
            throw new IllegalStateException(
                    "Indian Rail HTTP API URL is not configured.");
        }

        Matcher matcher = API_KEY_PATTERN.matcher(url);
        if (!matcher.find()) {
            throw new IllegalStateException(
                    "The configured HTTP API URL does not contain "
                    + "a usable /apikey/<value>/ segment.");
        }

        String key = matcher.group(1).trim();

        if (key.isEmpty()
                || key.startsWith("<")
                || key.startsWith("{")) {
            throw new IllegalStateException(
                    "The configured HTTP API URL contains an API-key placeholder. "
                    + "A live API credential is required for requests.");
        }

        return key;
    }

    private String get(String endpoint) throws Exception {
        HttpURLConnection connection = null;

        try {
            URI uri = URI.create(endpoint);
            URL url = uri.toURL();

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(
                    AppConfig.API_TIMEOUT_SECONDS * 1000);
            connection.setReadTimeout(
                    AppConfig.API_TIMEOUT_SECONDS * 1000);
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty(
                    "User-Agent", "RailSync/1.0");
            connection.setRequestProperty("Connection", "close");

            int responseCode = connection.getResponseCode();

            InputStream stream =
                    responseCode >= 200 && responseCode < 300
                            ? connection.getInputStream()
                            : connection.getErrorStream();

            String responseBody = readBody(stream);

            if (responseCode >= 200 && responseCode < 300) {
                return responseBody;
            }

            throw new IllegalStateException(
                    buildHttpError(responseCode, responseBody));

        } catch (IllegalStateException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new IllegalStateException(
                    "Unable to connect to Indian Rail API. "
                    + "Please check the HTTP API URL and internet connection.",
                    ex);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private String readBody(InputStream stream) throws Exception {
        if (stream == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();
    }

    private String buildHttpError(
            int responseCode,
            String responseBody) {

        String body = responseBody == null
                ? ""
                : responseBody.trim();

        if (responseCode == 400) {
            return "Indian Rail API returned HTTP 400 (Bad Request). "
                    + "Check that the supplied HTTP API URL is valid and "
                    + "contains a real API credential.";
        }

        if (responseCode == 401 || responseCode == 403) {
            return "Indian Rail API rejected the request (HTTP "
                    + responseCode + "). Check that the credential in "
                    + "the supplied HTTP API URL is valid and active.";
        }

        if (responseCode == 404) {
            return "Indian Rail API endpoint was not found (HTTP 404).";
        }

        if (responseCode >= 500) {
            return "Indian Rail API server error (HTTP "
                    + responseCode + "). Please try again later.";
        }

        if (body.length() > 500) {
            body = body.substring(0, 500) + "...";
        }

        return "Indian Rail API error: HTTP "
                + responseCode
                + (body.isEmpty() ? "" : " - " + body);
    }

    private void validateConfigured() {
        String url = getApiHttpUrl();

        if (isBlank(url)) {
            throw new IllegalStateException(
                    "Indian Rail HTTP API URL is not configured. "
                    + "Open Settings and save the HTTP API URL first.");
        }

        if (url.contains("<apikey>") || url.contains("{apikey}")) {
            throw new IllegalStateException(
                    "The supplied HTTP API URL still contains <apikey>. "
                    + "It is only a documentation template. "
                    + "A real API credential is required for live requests.");
        }

        validateApiUrlFormat(url);
    }

    private void validateApiUrlFormat(String url) {
        if (isBlank(url)) {
            throw new IllegalArgumentException(
                    "HTTP API URL cannot be empty.");
        }

        if (!isIndianRailApiUrl(url)) {
            throw new IllegalArgumentException(
                    "Enter the Indian Rail API HTTP URL beginning with "
                    + "http://indianrailapi.com/api/v2/ or "
                    + "https://indianrailapi.com/api/v2/." );
        }
    }

    private boolean isIndianRailApiUrl(String url) {
        String value = url.trim().toLowerCase();
        return value.startsWith("http://indianrailapi.com/api/v2/")
                || value.startsWith("https://indianrailapi.com/api/v2/");
    }

    private void validateSearchText(String value) {
        if (isBlank(value)) {
            throw new IllegalArgumentException("Search value cannot be empty.");
        }
    }

    private static String normalizeApiUrl(String value) {
        if (value == null) {
            return "";
        }
        return value.trim();
    }

    private static String encodePathSegment(String value) {
        return URLEncoder
                .encode(value, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }

    private static String loadStoredApiUrl() {
        return Preferences.userRoot()
                .node(PREF_NODE)
                .get(API_URL_PREF, "");
    }

    private static void saveStoredApiUrl(String url) {
        Preferences.userRoot()
                .node(PREF_NODE)
                .put(API_URL_PREF, url);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
