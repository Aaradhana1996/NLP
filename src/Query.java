import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Query {
	public String read() throws IOException {
		System.out.println("Enter a query: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		return line;
	}
}
