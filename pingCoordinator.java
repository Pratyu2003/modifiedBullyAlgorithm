import java.net.InetAddress;
import java.net.Socket;

public class pingCoordinator{
synchronized static void ping(Process P) {
		try {
			Election.pingLock.lock();
			if (Election.isPingFlag()) {
				System.out.println("P" + P.pid + ": Pinging to COORDINATOR");
				Socket outgoing = new Socket(InetAddress.getLocalHost(), 1234);
				outgoing.close();
			}
		} catch (Exception ex) {
			Election.setPingFlag(false);
			Election.setElectionFlag(true);
			Election.setElectionDetector(P);
            System.out.println("");
			System.out.println("P" + P.pid + ": COORDINATOR is not responding, I'm  sending the Election msg to EC..\n");
		} finally {
			Election.pingLock.unlock();
		}
	}
}