import gnu.io.*;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Observable;

public class PICApplication extends Observable implements SerialPortEventListener{
	
	private int temperature;
	
	private CommPortIdentifier selectedPortIdentifier = null;
	private SerialPort serialPort = null; 
	
	private InputStream in = null;
	private OutputStream out = null;
	
	/**
	 * APP
	 */
	public PICApplication() {
		super();
	}
	
	
	/**
	 * 
	 */
	public void searchForPorts()
    {
		CommPortIdentifier serialPortId;
		
		Enumeration enumComm;
		
		enumComm = CommPortIdentifier.getPortIdentifiers();
		
		while(enumComm.hasMoreElements())
		{
			serialPortId = (CommPortIdentifier)enumComm.nextElement();
			if(serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				System.out.println(serialPortId.getName());
			}
		}
		
		
    }
	
	/**
	 * 
	 * @param portName
	 * @throws Exception
	 */
	public void connect(String portName) throws Exception {
		 CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
	        if ( portIdentifier.isCurrentlyOwned() )
	        {
	            System.out.println("Error: Port is currently in use");
	        }
	        else
	        {
	            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
	            System.out.println("port détecté ! ");
	            
	            if ( commPort instanceof SerialPort )
	            {
	                this.serialPort = (SerialPort) commPort;
	                serialPort.setSerialPortParams(2400,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);  
	                
	                this.in = serialPort.getInputStream();
	                this.out = serialPort.getOutputStream();              

	                serialPort.addEventListener(this);
	                serialPort.notifyOnDataAvailable(true);
	            }
	        } 
	}
	
	
	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	

	/**
	 * 
	 * @param b
	 */
	public void send(byte b) {
		System.out.println("Send to COM port: " + b);
		try {
			this.out.write(b>>>0);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		// TODO Auto-generated method stub
		switch (event.getEventType()) {
        case SerialPortEvent.DATA_AVAILABLE:
            System.out.println("Data available");
            break;
        }
    }

	
	/**
	 * main !!!!
	 * @param args
	 */
	public static void main ( String[] args )
    {
        try
        {
            (new PICApplication()).connect("COM1");
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

	
}
