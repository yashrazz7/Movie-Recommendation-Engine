import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;

public class RecommendationServer {

    private static Map<String, List<String>> movieDatabase = new HashMap<>();
    private static final String FILE_NAME = "movies.txt";

    private static void loadDatabaseFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty() || !line.contains("|")) continue;
                
                String[] parts = line.split("\\|");
                String movieTitle = parts[0].trim();
                List<String> tags = Arrays.stream(parts[1].split(","))
                                         .map(String::trim)
                                         .collect(Collectors.toList());
                
                movieDatabase.put(movieTitle, tags);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getRecommendations(String inputMovie) {
        List<String> inputTags = movieDatabase.get(inputMovie);
        if (inputTags == null) return Collections.emptyList();

        Map<String, Integer> similarityScores = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : movieDatabase.entrySet()) {
            String movie = entry.getKey();
            if (movie.equalsIgnoreCase(inputMovie)) continue;

            List<String> currentTags = entry.getValue();
            long matches = currentTags.stream().filter(inputTags::contains).count();
            
            if (matches > 0) {
                similarityScores.put(movie, (int) matches);
            }
        }

        return similarityScores.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .map(Map.Entry::getKey)
                .limit(3)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        loadDatabaseFromFile();
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/getMovies", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            List<String> sortedMovies = new ArrayList<>(movieDatabase.keySet());
            Collections.sort(sortedMovies);
            String response = String.join(",", sortedMovies);
            
            byte[] bytes = response.getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
            exchange.close();
        });

        server.createContext("/recommend", exchange -> {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            
            try (BufferedReader br = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), "UTF-8"))) {
                String inputMovie = br.readLine();
                List<String> recommendations = getRecommendations(inputMovie);
                String response = String.join(",", recommendations);
                
                byte[] bytes = response.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
            exchange.close();
        });

        server.start();
    }
}