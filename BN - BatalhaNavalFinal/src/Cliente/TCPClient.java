package Cliente;

/*
 * TCPClient.java
 *
 * Created on 24 de Julho de 2009, 10:57
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import Classes.ClienteWindow;

/**
 *
 * @author Administrador
 */
public class TCPClient {

    /**
     * Creates a new instance of TCPClient
     */
    public TCPClient(String serverAddress, int serverPort, ClienteWindow caller) throws UnknownHostException, IOException {
        this.socket = new Socket(serverAddress, serverPort);
        this.socket.setKeepAlive(true);
        (new TCPClientHandler(socket, caller)).start();
        this.output = new PrintWriter(this.socket.getOutputStream(), true);
    }

    public String readMessage() throws IOException {
        return this.input.readLine();
    }

    public void writeMessage(String outMessage) {
        this.output.println(outMessage);
    }

    public void sendAttack(String sRowColumn) {
        this.output.println(sRowColumn);
    }
    
    public void sendCampo(String sMatriz){
        this.output.println(sMatriz);
    }

    public void closeConnection() throws IOException {
        this.output.close();
        this.socket.close();
    }

    @Override
    public void finalize() {
        try {
            this.closeConnection();
        } catch (IOException e) {
        }
    }
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
}