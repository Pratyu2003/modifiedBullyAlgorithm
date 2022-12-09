import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class CoordinatorFailsCheck {
    synchronized static boolean CheckFailure(Process P, int total_processes) {
		boolean response = false;
		try {
			Election.electionLock.lock();
			if (Election.isElectionFlag() && !MyThread.isMessageFlag(P.getPid() - 1)
					&& P.getPid() >= Election.getElectionDetector().getPid()) {

				for (int i = P.pid + 1; i <= total_processes; i++) 
					try {
						Socket electionMessage = new Socket(InetAddress.getLocalHost(), 1000 + i);
						System.out.println("EC: P" + total_processes + " is Working fine! " + 
						"P" + P.getPid() + " notice is Incorrect!"); 
						System.out.println("EC: P" + total_processes + " is the current COORDINATOR");
						electionMessage.close();
						response = true;
						return response;
					} catch (IOException ex) {
						System.out.println("\nEC" + ": P" + i
								+ " didn't respond!");
								System.out.println();
								total_processes--;
								return response;
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					}
				
				
				MyThread.setMessageFlag(true, P.getPid() - 1);
				Election.electionLock.unlock();
				return false;
			} else {
				throw new Exception();
			}
		} catch (Exception ex1) {
			Election.electionLock.unlock();
			return true;
		}
	}
}
