import java.util.*;
import java.io.*;
import java.net.*;

public class MyThread implements Runnable {

	private Process process;
	public int total_processes;
	private static boolean messageFlag[];
	public static ServerSocket[] sock;
	Random r;

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public MyThread(Process process, int total_processes) {
		this.process = process;
		this.total_processes = total_processes;
		this.r = new Random();
		MyThread.sock = new ServerSocket[total_processes];
		MyThread.messageFlag = new boolean[total_processes];
		for (int i = 0; i < total_processes; i++)
			MyThread.messageFlag[i] = false;
	}

	@Override
	public void run() {
		try {
			sock[this.process.getPid() - 1] = new ServerSocket(1000 + this.process.getPid());
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		while (true) {
			if (process.isCoOrdinatorFlag()) {
				ElectionCommission.QUERY(process, total_processes, sock, r);
				
			} else {
				while (true) {

					executeJob();
					pingCoordinator.ping(this.process);

					if (Election.isElectionFlag()) {
							if (!ElectionCommission.FD(this.process, total_processes)) {		
							Election.setElectionFlag(false);
							System.out.println("EC is electing the next COORDINATOR..");
							int c=60;
							while(c>0){
							System.out.print("-"); c--;} System.out.println("");
								int id = this.process.getPid();
							System.out.println("EC: New COORDINATOR is P" + id);
							
							c=60;
							while(c>0){
							System.out.print("-"); c--;}System.out.println("");

							this.process.setCoOrdinatorFlag(true);
							for (int i = 0; i < total_processes; i++) {
								MyThread.setMessageFlag(false, i);
							}
							break;
						}
					}
				}
			}
		}
	}

	private void executeJob() {
		int temp = r.nextInt(15);
		for (int i = 0; i <= temp; i++) {
			try {
				Thread.sleep((temp + 1) * 100);
			} catch (InterruptedException e) {
				System.out.println("Error Executing Thread:" + process.getPid());
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static boolean isMessageFlag(int index) {
		return MyThread.messageFlag[index];
	}

	public static void setMessageFlag(boolean messageFlag, int index) {
		MyThread.messageFlag[index] = messageFlag;
	}

 	

}