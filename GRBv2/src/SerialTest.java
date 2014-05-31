import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialTest {

	static SerialPort serialPort;

	public void run() {
		serialPort = new SerialPort("COM15");
		try {
			//
			System.out.println("Port opened: " + serialPort.openPort());
			serialPort.setParams(SerialPort.BAUDRATE_9600,
					SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
			int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS
					+ SerialPort.MASK_DSR;// Prepare mask
			serialPort.setEventsMask(mask);// Set mask
			serialPort.addEventListener(new SerialPortReader());// Add
																// SerialPortEventListener
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}

	public void kill() {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static class SerialPortReader implements SerialPortEventListener {

		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {// If data is available
				// System.out.println(event.getEventValue());
				if (event.getEventValue() > 4) {// Check bytes count in the
												// input buffer

					// Read data
					try {
						byte buffer[] = serialPort.readBytes(4);
						int asInt = (buffer[0] & 0xFF)
								| ((buffer[1] & 0xFF) << 8)
								| ((buffer[2] & 0xFF) << 16)
								| ((buffer[3] & 0xFF) << 24);
						float asFloat = Float.intBitsToFloat(asInt);
						System.out.println(asFloat);
						GRBApps.setBerat(asFloat);
						// System.out.println(buffer[0]+" "+buffer[1]+" "+buffer[2]+" "+buffer[3]);
					} catch (SerialPortException ex) {
						System.out.println(ex);
					}
				}
			}
		}
	}
}
