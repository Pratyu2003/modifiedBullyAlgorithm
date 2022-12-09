import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Recovery {

  static synchronized void recover(Process P) {
    while (Election.isElectionFlag()) {
      // wait;
    }

    System.out.println("\nP" + P.getPid() + ": is working again!");

    try {
      Election.pingLock.lock();
      Election.setPingFlag(false);
      Socket outgoing = new Socket(InetAddress.getLocalHost(), 4000);
      Scanner scan = new Scanner(outgoing.getInputStream());
      PrintWriter out = new PrintWriter(outgoing.getOutputStream(), true);
      System.out.println("EC is working on deciding the COORDINATOR");
      out.println("EC is working on deciding the COORDINATOR");
      out.flush();

      String pid = scan.nextLine();
      if (P.getPid() > Integer.parseInt(pid)) {
        out.println("Resign");
        out.flush();
        System.out.println("P" + P.getPid() + ": Resign -> P" + pid);
        String resignStatus = scan.nextLine();
        if (resignStatus.equals("Successfully Resigned")) {
          P.setCoOrdinatorFlag(true);
          MyThread.sock[P.getPid() - 1] = new ServerSocket(1000 + P.getPid());
        }
      } else {
        out.println("Don't Resign");
        out.flush();
      }

      Election.pingLock.unlock();
      return;
    } catch (IOException ex) {
      System.out.println("EC is working on deciding the COORDINATOR..");
      System.out.println("\nP" + (P.getPid()-1) + ": resign from COORDINATOR");

      int c = 60;
      while (c > 0) {
        System.out.print("-");
        c--;
      }
   
	  System.out.println("\nP" + P.getPid() + ": is the COORDINATOR");

      c = 60;
      while (c > 0) {
        System.out.print("-");
        c--;
      }
     
      System.exit(1);
    }
  }
}
