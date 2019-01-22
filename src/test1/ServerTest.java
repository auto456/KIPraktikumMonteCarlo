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

	public static String ip = "http://192.168.43.160/";

	final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		ServerTest http = new ServerTest();

		http.drive(20);
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

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	private void sendCommand() throws IOException {
		System.out.println("Command eingeben. (fahren=0, links=1, rechts=2, sensor=3)");
		Scanner sc = new Scanner(System.in);
		int cmd = sc.nextInt();
		int wert;
		String url = null;
		if (cmd < 3) {
			System.out.println("Wert eingeben: ");
			wert = sc.nextInt();
			String command = null;
			switch (cmd) {
			case 0:
				command = "fahren";
				break;
			case 1:
				command = "links";
				break;
			case 2:
				command = "rechts";
				break;
			}
			url = ip + command + "X" + wert;
		}
		if (cmd == 3) {
			url = ip + "sensor";
		}

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
	}

	private String sendUrl(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return response.toString();
	}

	public void drive(int distance) throws IOException {
		for (int i = 0; i<distance/10; i++) {
			String url = ip + "fahren" + "X" + 10;
			sendUrl(url);

			String response = sendUrl(ip + "sensorXall");
			String[] liste = response.split(" ");
			Float distanceFront = Float.valueOf(liste[3]);
			Float distanceLeft = Float.valueOf(liste[6]);
			Float distanceRight = Float.valueOf(liste[9]);
			Float color = Float.valueOf(liste[11]);

			System.out.println("Front " + distanceFront);
			System.out.println("Colorcode " + color);

			int count = 0;
			while (color != 7) {
				if (count < 6) {
					url = ip + "linksX10";
					sendUrl(url);
					url = ip + "sensorXall";
					liste = sendUrl(url).split(" ");
					color = Float.valueOf(liste[11]);
				} if(count ==6) {
					url = ip + "rechtsX60";
					sendUrl(url);
					url = ip + "sensorXall";
					liste = sendUrl(url).split(" ");
					color = Float.valueOf(liste[11]);
				}if(count >6) {
					url = ip + "rechtsX10";
					sendUrl(url);
					url = ip + "sensorXall";
					liste = sendUrl(url).split(" ");
					color = Float.valueOf(liste[11]);
				}
				
				count ++;
			}

//		    if(distanceLeft < 0.15) {
//		    	String url2 = ip + "rechtsX20";
//		    	sendUrl(url2);
//		    	String url3 = ip + "fahrenX20";
//		    	sendUrl(url3);
//		    	String url4 = ip + "linksX20";
//		    	sendUrl(url4);
//		    	String url5 = ip + "fahrenX-13";
//		    	sendUrl(url5);
//		    }
//		    if(distanceRight < 0.10) {
//		    	String url2 = ip + "linksX20";
//		    	sendUrl(url2);
//		    	String url3 = ip + "fahrenX20";
//		    	sendUrl(url3);
//		    	String url4 = ip + "rechtsX20";
//		    	sendUrl(url4);
//		    	String url5 = ip + "fahrenX-13";
//		    	sendUrl(url5);
//		    }
		}
		
	}

	public void turnLeft(int angle) throws IOException {
		String url = ip + "links" + "X" + angle;
		sendUrl(url);
	}

	public void turnRight(int angle) throws IOException {
		String url = ip + "rechts" + "X" + angle;
		sendUrl(url);
	}

	public String readSensor(String sensor) throws IOException {
		String url = "null";

		if (sensor == "front") {
			url = ip + "sensor" + "X" + "front";

		}
		if (sensor == "right") {
			url = ip + "sensor" + "X" + "right";

		}
		if (sensor == "left") {
			url = ip + "sensor" + "X" + "left";

		}
		if (sensor == "color") {
			url = ip + "sensor" + "X" + "color";

		}
		if (sensor == "all") {
			url = ip + "sensor" + "X" + "all";
		}

		return sendUrl(url);
	}

	public int[] arrayFunctionTest(int p) {

		class Position {
			public int getX() {
				return x;
			}

			public void setX(int x) {
				this.x = x;
			}

			public int getY() {
				return y;
			}

			public void setY(int y) {
				this.y = y;
			}

			int x, y;

			// Constructor
			Position(int i, int j) {
				i = x;
				j = y;
			}

		}

		Position[] posArray = new Position[13];

		Position p0 = new Position(0, 0);
		Position p1 = new Position(0, 1);
		Position p2 = new Position(1, 0);
		Position p3 = new Position(0, 0);
		Position p4 = new Position(1, 1);
		Position p5 = new Position(1, 0);
		Position p6 = new Position(0, 1);
		Position p7 = new Position(1, 1);
		Position p8 = new Position(0, 0);
		Position p9 = new Position(1, 1);
		Position p10 = new Position(1, 0);
		Position p11 = new Position(0, 0);

		posArray[0] = p0;
		posArray[1] = p1;
		posArray[2] = p2;
		posArray[3] = p3;
		posArray[4] = p4;
		posArray[5] = p5;
		posArray[6] = p6;
		posArray[7] = p7;
		posArray[8] = p8;
		posArray[9] = p9;
		posArray[10] = p10;
		posArray[11] = p11;

		int[] myInt = { posArray[p].x, posArray[p].y };

		return myInt;
	}
}
