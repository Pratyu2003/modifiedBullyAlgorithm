import java.net.ServerSocket;
import java.util.Random;

public class ElectionCommission {

    public static void setPriority(MyThread[] t) {
		Process temp = new Process(-1);
		for (int i = 0; i < t.length; i++)
			if (temp.getPid() < t[i].getProcess().getPid())
				temp = t[i].getProcess();
		
		t[temp.pid - 1].getProcess().isCoordinator = true;
	}

    public static boolean FD(Process P, int total_processes) {
      return CoordinatorFailsCheck.CheckFailure(P, total_processes);
    }

    public static void QUERY(Process process, int total_processes, ServerSocket[] sock, Random r){
        Coordinator.serve(process, total_processes, sock, r);
    }

    public static void HP(Process P){
        Recovery.recover(P);
    }
}
