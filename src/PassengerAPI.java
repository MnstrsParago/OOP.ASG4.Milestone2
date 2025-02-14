import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.sql.*;
import org.json.JSONObject;

public class PassengerAPI {
    private static final String URL = "jdbc:postgresql://localhost:5432/ar_system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "AbdaNazar2006";

    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            server.createContext("/passenger", exchange -> {
                try (Connection conn = getConnection()) {
                    String method = exchange.getRequestMethod();

                    if (method.equals("GET")) {
                        String response = getPassengers(conn);
                        sendResponse(exchange, response);
                    } else if (method.equals("POST")) {
                        String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                                .lines().reduce("", (acc, line) -> acc + line);
                        JSONObject json = new JSONObject(requestBody);

                        insertPassenger(conn, json.getString("name"), json.getString("passport_number"),
                                json.getString("nationality"), json.getString("date_of_birth"));
                        sendResponse(exchange, "Passenger added successfully!");
                    }
                    else if (method.equals("PUT")) {
                        String query = exchange.getRequestURI().getQuery();
                        if (query != null && query.startsWith("id=")) {
                            int id = Integer.parseInt(query.split("=")[1]);
                            String requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                                    .lines().reduce("", (acc, line) -> acc + line);
                            JSONObject json = new JSONObject(requestBody);

                            updatePassenger(conn, id, json.getString("name"), json.getString("passport_number"),
                                    json.getString("nationality"), json.getString("date_of_birth"));
                            sendResponse(exchange, "Passenger updated successfully!");
                        } else {
                            sendResponse(exchange, "Invalid request.");
                        }
                    }
                    else if (method.equals("DELETE")) {
                        String query = exchange.getRequestURI().getQuery();
                        if (query != null && query.startsWith("id=")) {
                            int id = Integer.parseInt(query.split("=")[1]);
                            deletePassenger(conn, id);
                            sendResponse(exchange, "Passenger deleted.");
                        } else {
                            sendResponse(exchange, "Invalid request.");
                        }
                    } else {
                        sendResponse(exchange, "Unsupported method.");
                    }
                } catch (SQLException e) {
                    sendResponse(exchange, "Database error: " + e.getMessage());
                }
            });

            server.setExecutor(null);
            server.start();
            System.out.println("Server started at http://localhost:8080");
        } catch (IOException e) {
            System.err.println("Server could not start: " + e.getMessage());
        }
    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private static String getPassengers(Connection conn) {
        StringBuilder result = new StringBuilder();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Passenger")) {
            result.append("[");
            while (rs.next()) {
                result.append(String.format("{\"id\": %d, \"name\": \"%s\", \"passport\": \"%s\", \"nationality\": \"%s\", \"dob\": \"%s\"},",
                        rs.getInt("passenger_id"), rs.getString("name"), rs.getString("passport_number"), rs.getString("nationality"), rs.getDate("date_of_birth")));
            }
            if (result.length() > 1) {
                result.deleteCharAt(result.length() - 1);
            }
            result.append("]");
        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
        return result.toString();
    }

    private static void insertPassenger(Connection conn, String name, String passport, String nationality, String dob) {
        String sql = "INSERT INTO Passenger (name, passport_number, nationality, date_of_birth) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, passport);
            pstmt.setString(3, nationality);
            pstmt.setDate(4, Date.valueOf(dob));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting passenger", e);
        }
    }

    private static void updatePassenger(Connection conn, int id, String name, String passport, String nationality, String dob) throws SQLException {
        String sql = "UPDATE Passenger SET name = ?, passport_number = ?, nationality = ?, date_of_birth = ? WHERE passenger_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, passport);
            pstmt.setString(3, nationality);
            pstmt.setDate(4, Date.valueOf(dob));
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }


    private static void deletePassenger(Connection conn, int id) {
        String sql = "DELETE FROM Passenger WHERE passenger_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting passenger", e);
        }
    }

    private static void sendResponse(HttpExchange exchange, String response) {
        try {
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (IOException e) {
            System.err.println("Error sending response: " + e.getMessage());
        }
    }
}
