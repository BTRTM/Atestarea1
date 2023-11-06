import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class CalculatorClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți operația (add, subtract, multiply, divide): ");
        String operation = scanner.next();

        System.out.print("Introduceți primul număr: ");
        double first = scanner.nextDouble();

        System.out.print("Introduceți al doilea număr: ");
        double second = scanner.nextDouble();

        System.out.print("Introduceți al treilea număr: ");
        double third = scanner.nextDouble();

        String baseUrl = "http://localhost:8080/calculate";
        String url = baseUrl + "/" + operation + "/" + first + "/" + second + "/" + third;

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("PUT");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                InputStream responseStream = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(responseStream);
                int data = reader.read();
                StringBuilder response = new StringBuilder();
                while (data != -1) {
                    response.append((char) data);
                    data = reader.read();
                }
                reader.close();
                System.out.println("Succes: " + response);
            } else {
                System.out.println("Eroare: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
