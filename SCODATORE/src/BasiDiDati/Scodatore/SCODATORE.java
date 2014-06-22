package BasiDiDati.Scodatore;

import com.hascode.tutorial.client.SampleConsumer;
import com.hascode.tutorial.client.SampleProducer;

public class SCODATORE {

	public static void main(String[] args) {
		 // producing some messages
		 for (int i = 1; i < 6; i++) {
		 final String message = "This is message numero " + i;
		 SampleProducer producer = new SampleProducer(message);
		 new Thread(producer).start();
		 }
		 
		 Thread consumerThread = new Thread(new SampleConsumer());
		 consumerThread.start();
		 }

	}


