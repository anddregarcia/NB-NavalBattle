package Servidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jjunior
 */
public class ServidorMultithreadTCP extends Thread {

    private List clientes;
    private ServerSocket server;

    public ServidorMultithreadTCP(int porta) throws IOException {
        this.server = new ServerSocket(porta);
        this.clientes = new ArrayList();
        try {
            (new ServidorMultithreadTCP(6789)).start();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //threads
    @Override
    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = this.server.accept();
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                novoCliente(output);
                (new ServidorHandlerTCP(socket, output, this)).start();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public synchronized void novoCliente(PrintWriter output) throws IOException {
        clientes.add(output);
        System.out.println(output);
    }

    public synchronized void removerCliente(PrintWriter output) {
        clientes.remove(output);
        output.close();
    }

    public List getClientes() {
        return clientes;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.server.close();
    }
    public static void main(String[] args) {
        try {
            (new ServidorMultithreadTCP(6789)).start();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }   
    }
}
