import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter the number of processes to start: ");
		int processes = in.nextInt();
		System.out.println("COORDINATOR is P" + processes+"\n");
		
		MyThread[] t = new MyThread[processes];

		for (int i = 0; i < processes; i++)
			t[i] = new MyThread(new Process(i+1), processes);
		
		ElectionCommission.setPriority(t);

		for (int i = 0; i < processes; i++)
			new Thread(t[i]).start();
	}
}
