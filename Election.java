import java.util.concurrent.locks.ReentrantLock;

public class Election {

	public static ReentrantLock pingLock = new ReentrantLock(), 
			electionLock = new ReentrantLock();
	private static boolean isElection = false, isPing = true;
	public static Process electionDetector;

	public static Process getElectionDetector() {
		return electionDetector;
	}

	public static void setElectionDetector(Process electionDetector) {
		Election.electionDetector = electionDetector;
	}

	public static boolean isPingFlag() {
		return isPing;
	}

	public static void setPingFlag(boolean pingFlag) {
		Election.isPing = pingFlag;
	}

	public static boolean isElectionFlag() {
		return isElection;
	}

	public static void setElectionFlag(boolean electionFlag) {
		Election.isElection = electionFlag;
	}

	
}