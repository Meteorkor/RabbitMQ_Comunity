import java.util.Scanner;

import com.meteor.module.Comunity;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("aaa");
		
		//Scanner sc ;
		String tex;
		
		
		final Comunity com = new Comunity(null);
		
		Runnable Consumer = new Runnable() {
			
			@Override
			public void run() {
				com.create_customer();
			}
		};
		

		Runnable Sender = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Name ют╥б");
				Scanner sc  = new Scanner(System.in);
				
				String name = sc.nextLine();				
				while(true){					
					sc  = new Scanner(System.in);
					com.create_sender(name,sc.next());
				}
				
			}
		};
		new Thread(Consumer).start();
		new Thread(Sender).start();
		
		
		/*
		Scanner sc  = new Scanner(System.in);
		System.out.println( sc.next() );
		*/
		
		
		
	}

}
