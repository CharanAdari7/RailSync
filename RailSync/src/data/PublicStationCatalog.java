package data;

import domain.StationSearchResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Public station master-data source used when the paid/provider API is not configured.
 * The application downloads the public dataset once and caches it locally.
 */
public class PublicStationCatalog {

    private static final String DATA_URL =
            "https://raw.githubusercontent.com/IamYVJ/Indian_Railway_Stations_JSON/master/railwayStationsList.json";

    private static final Pattern STATION_PATTERN = Pattern.compile(
            "\\{\\s*\\\"stnCode\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"\\s*,\\s*"
          + "\\\"stnName\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"\\s*,\\s*"
          + "\\\"stnCity\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"\\s*\\}",
            Pattern.CASE_INSENSITIVE);

    private final File cacheFile;
    private volatile List<StationSearchResult> stations;

    public PublicStationCatalog() {
        File directory = new File(
                System.getProperty("user.home"), ".railsync");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        cacheFile = new File(directory, "station-catalog.json");
    }

    public List<StationSearchResult> search(String query) throws Exception {
        String q = query == null ? "" : query.trim();
        if (q.isEmpty()) {
            return new ArrayList<>();
        }

        ensureLoaded();

        String value = q.toLowerCase();
        List<StationSearchResult> result = new ArrayList<>();

        for (StationSearchResult station : stations) {
            String code = safe(station.getStationCode()).toLowerCase();
            String name = safe(station.getStationName()).toLowerCase();

            if (code.contains(value) || name.contains(value)) {
                result.add(station);
            }
        }

        result.sort(Comparator
                .comparingInt((StationSearchResult s) -> score(s, value))
                .thenComparing(StationSearchResult::getStationName,
                        String.CASE_INSENSITIVE_ORDER));

        if (result.size() > 100) {
            return new ArrayList<>(result.subList(0, 100));
        }

        return result;
    }

    public synchronized void refresh() throws Exception {
        byte[] bytes = download();
        try (OutputStream out = new FileOutputStream(cacheFile)) {
            out.write(bytes);
        }
        stations = parse(new String(bytes, StandardCharsets.UTF_8));
    }

    public String getSourceDescription() {
        return "Public Indian railway station master data (GitHub)";
    }

    private void ensureLoaded() throws Exception {
        if (stations != null) {
            return;
        }

        synchronized (this) {
            if (stations != null) {
                return;
            }

            if (cacheFile.isFile() && cacheFile.length() > 0) {
                try {
                    stations = parse(read(cacheFile));
                    if (!stations.isEmpty()) {
                        return;
                    }
                } catch (Exception ignored) {
                    // A broken cache is replaced by a fresh copy below.
                }
            }

            refresh();
        }
    }

    private byte[] download() throws Exception {
        HttpURLConnection connection =
                (HttpURLConnection) URI.create(DATA_URL).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(20000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent", "RailSync/1.0");
        connection.setRequestProperty("Connection", "close");

        int status = connection.getResponseCode();
        if (status < 200 || status >= 300) {
            throw new IllegalStateException(
                    "Station catalog could not be downloaded (HTTP " + status + ").");
        }

        try (InputStream in = connection.getInputStream()) {
            return readBytes(in);
        } finally {
            connection.disconnect();
        }
    }

    private List<StationSearchResult> parse(String json) {
        List<StationSearchResult> result = new ArrayList<>();
        if (json == null || json.isEmpty()) {
            return result;
        }

        Matcher matcher = STATION_PATTERN.matcher(json);
        while (matcher.find()) {
            String code = matcher.group(1).trim();
            String name = matcher.group(2).trim();
            String city = matcher.group(3).trim();

            if (code.isEmpty() || name.isEmpty()) {
                continue;
            }

            result.add(new StationSearchResult(code, name, 0.0, 0.0, city));
        }

        return result;
    }

    private int score(StationSearchResult station, String query) {
        String code = safe(station.getStationCode()).toLowerCase();
        String name = safe(station.getStationName()).toLowerCase();
        if (code.equals(query)) return 0;
        if (name.equals(query)) return 1;
        if (code.startsWith(query)) return 2;
        if (name.startsWith(query)) return 3;
        return 4;
    }

    private String read(File file) throws Exception {
        try (InputStream in = new FileInputStream(file)) {
            return new String(readBytes(in), StandardCharsets.UTF_8);
        }
    }

    private byte[] readBytes(InputStream input) throws Exception {
        java.io.ByteArrayOutputStream buffer =
                new java.io.ByteArrayOutputStream();
        byte[] data = new byte[8192];
        int n;
        while ((n = input.read(data)) != -1) {
            buffer.write(data, 0, n);
        }
        return buffer.toByteArray();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
