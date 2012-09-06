package Cliente;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import Classes.ClienteWindow;

/**
 *
 * @author jjunior
 */
public class TCPClientHandler extends Thread {

    private Socket socket;
    private ClienteWindow caller;
    private BufferedReader input;

    public TCPClientHandler(Socket socket, ClienteWindow caller) throws IOException {
        this.socket = socket;
        this.caller = caller;
        this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    @Override
    public void run() {
        String sMensagem = " ";
        while (true) {
            try {
                sMensagem = this.input.readLine();

                if (sMensagem.substring(0, 1).equals("P")) {
                    this.caller.escreverMensagem(sMensagem);
                }
                if (sMensagem.substring(0, 1).equals("E")) {
                    this.caller.escreverMensagem(sMensagem);
                }
                if (sMensagem.substring(0, 1).equals("X")) {
                    this.caller.escreverMensagem(sMensagem);
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
