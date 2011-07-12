import java.io.*;
import java.net.*;

public class ClientHandler extends Thread{
	
	ServerSocket serverSocket;
	
	public ClientHandler(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	public void run(){
		try {
			Socket clientSocket = this.serverSocket.accept();
			BufferedReader in = new BufferedReader(
				new InputStreamReader(clientSocket.getInputStream()));
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());				
			String str = ".";
			int firsttime = 0;
			String filename = null;
			while (!str.equals("")) {
				str = in.readLine();
				if(firsttime == 0){
					if(str.indexOf("GET") != -1){
						if(str.indexOf("GET / ") != -1){
							printfromfile(out, "index.html");
						}else{
							filename = 
								str.substring(str.indexOf("GET /")+5,str.indexOf(" HTTP"));
							printfromfile(out, filename);
						}
					}else{
						error500(out);
					}
					firsttime = 1;
				}
				System.out.println(str);
			}
			
			
			clientSocket.close();
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
	}
	
	protected void printfromfile(PrintWriter out, String file){
		String filepath = "httpd/" + file;
		try {
			BufferedReader infile = new BufferedReader(new FileReader(filepath));
			String str;
			out.println("HTTP/1.0 200 OK\nContent-Type: text/html\nServer: Bot\n");
			while ((str = infile.readLine()) != null) {
				out.println(str);
			}
			infile.close();
		} catch (IOException e) {
		out.println("HTTP/1.0 200 OK\nContent-Type: text/html\nServer: Bot\n");
		out.println("<html>\n<head>\n<title>401 not found</title>\n</head>\n<body>"
			+"\n<h1>\n401 site not found\n</h1>\n</body>\n</html>");
		}
	out.flush();	
	}
	protected void error500(PrintWriter out){
		out.println("HTTP/1.0 200 OK\nContent-Type: text/html\nServer: Bot\n");
		out.println("<html>\n<head>\n<title>500 not found</title>\n</head>\n<body>"
		+"\n<h1>\nError 500 internal server error\n</h1>\n</body>\n</html>");
		out.flush();	
	}
}

