package test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ServerTest {

	public static  String ip = "http://192.168.43.160/";
	
	final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		ServerTest http = new ServerTest();

		http.drive(50);
		http.readSensor("front");
		http.turnLeft(180);
		http.drive(50);
		http.readSensor("all");
//		System.out.println("Testing 1 - Send Http GET request");
//		http.sendGet();
//		http.sendCommand();
	}

	// HTTP GET request
	private void sendGet() throws Exception {
		String url = "http://192.168.43.160/sensor";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}
	
	private void sendCommand() throws IOException {
		System.out.println("Command eingeben. (fahren=0, links=1, rechts=2, sensor=3)");
		Scanner sc = new Scanner(System.in);
		int cmd = sc.nextInt();
		int wert;
		String url = null;
		if(cmd < 3) { 
			System.out.println("Wert eingeben: ");
			wert = sc.nextInt();
			String command = null;
			switch (cmd) {
			case 0: command = "fahren";
			break;
			case 1: command = "links";
			break;
			case 2: command = "rechts";
			break;
			}
			url = ip + command + "X" + wert;
		}
		if(cmd == 3) {
			url = ip + "sensor";
		}
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
	}

	private void sendUrl(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
	}
	
	private void drive(int distance) throws IOException {
		String url = ip + "fahren" + "X" + distance;
		sendUrl(url);
		
	}
	
	private void turnLeft(int angle) throws IOException {
		String url = ip + "links" + "X" + angle;
		sendUrl(url);
	}
	
	private void turnRight(int angle) throws IOException {
		String url = ip + "rechts" + "X" + angle;
		sendUrl(url);
	}

	private void readSensor(String sensor) throws IOException {
		String url = "null";

		if(sensor == "front") {
			 url = ip + "sensor" + "X" + "front";

		} if(sensor == "right") {
			 url = ip + "sensor" + "X" + "right";

		} if (sensor == "left") {
			 url = ip + "sensor" + "X" + "left";

		} if(sensor == "color") {
			 url = ip + "sensor" + "X" + "color";

		} if (sensor == "all") {
			url = ip + "sensor" + "X" + "all";

		}
		
		sendUrl(url);
	}
}
