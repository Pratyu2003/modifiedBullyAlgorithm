import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Coordinator {
   
    synchronized static void serve(Process P, int total_processes, ServerSocket[] sockets, Random r) {
		try {
			boolean done = false;
			Socket incoming = null;
			ServerSocket s = new ServerSocket(1234);
			Election.setPingFlag(true);
			int temp = r.nextInt(5) + 5;
			
			for (int counter = 0; counter < temp; counter++) {
				incoming = s.accept();
				if (Election.isPingFlag())
					System.out.println("P" + P.getPid() + ": Responding..");
				
				Scanner scan = new Scanner(incoming.getInputStream());
				PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);
				while (scan.hasNextLine() && !done) {
					String line = scan.nextLine();
					if (line.equals("EC is working on deciding the COORDINATOR")) {
						System.out.println("P" + P.getPid() + ": Me");
						out.println(P.getPid());
						out.flush();
					} else if (line.equals("Resign")) {
						P.setCoOrdinatorFlag(false);
						out.println("Successfully Resigned");
						out.flush();
						incoming.close();
						s.close();
						System.out.println("P" + P.getPid() + ": Successfully Resigned");
						return;
						
					} else if (line.equals("Don't Resign")) {
						done = true;
					}
				}
			}
			
			P.setCoOrdinatorFlag(false);
			P.setDownflag(true);
			try {
				incoming.close();
				s.close();
				sockets[P.getPid() - 1].close();
				Thread.sleep(20000);
				total_processes++;
		
				ElectionCommission.HP(P);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
