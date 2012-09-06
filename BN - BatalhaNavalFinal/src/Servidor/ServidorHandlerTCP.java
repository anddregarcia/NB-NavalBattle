package Servidor;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import Classes.ClienteWindow;
import Classes.MatrizCampo;
import java.io.*;

/**
 *
 * @author jjunior
 */
public class ServidorHandlerTCP extends Thread {

    private Socket socket;
    private PrintWriter output;
    private ServidorMultithreadTCP caller;
    private BufferedReader input;
    private ClienteWindow varMW = new ClienteWindow();
    MatrizCampo mCampo0 = new MatrizCampo();
    MatrizCampo mCampo1 = new MatrizCampo();
    File _fileM0 = new File("M0.txt");
    File _fileM1 = new File("M1.txt");
    FileReader _readerM0;
    FileWriter _writerM0;
    BufferedReader _breaderM0;
    FileReader _readerM1;
    FileWriter _writerM1;
    BufferedReader _breaderM1;
    FileReader _reader;
    BufferedReader _breader;
    String sMatrizReaded = null;

    public ServidorHandlerTCP(Socket socket, PrintWriter output, ServidorMultithreadTCP caller) throws IOException {
        this.socket = socket;
        this.caller = caller;
        this.output = output;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.input.close();
        this.caller.removerCliente(this.output);
        this.socket.close();
    }

    public synchronized void messageDispatcher(String sMensagem) throws IOException {

        String sPlayer = sMensagem.substring(sMensagem.indexOf("N") + 1);
        String sRow = sMensagem.substring(sMensagem.indexOf("A") + 1, sMensagem.indexOf("A") + 2);
        String sColumn = sMensagem.substring(sMensagem.indexOf("A") + 2, sMensagem.indexOf("A") + 3);

        if (ValidaTiro(
                Integer.parseInt(sRow),
                Integer.parseInt(sColumn),
                Integer.parseInt(sPlayer))) {

            enviarPontos("P" + varMW.iPontos + "N" + sPlayer);
        } else {
            enviarErro("EN" + sPlayer);
        }
    }

    public void enviarPontos(String msg) {

        String sPlayer = msg.substring(msg.indexOf("N") + 1);
        int iPlayer = Integer.parseInt(sPlayer);
        
        String sMsgContra = "X" + msg; //Envia uma mensagem pro outro cliente informando os pontos do inimigo

        List clientes = this.caller.getClientes();
        PrintWriter out = null;
        Iterator iterator = clientes.iterator();
        while (iterator.hasNext()) {
            out = (PrintWriter) iterator.next();
            if (out.equals(clientes.get(iPlayer))) {
                out.println(msg);
                out.flush();
            }
            if (!out.equals(clientes.get(iPlayer))) {
                out.println(sMsgContra);
                out.flush();
            }            
        }
    }

    public void enviarErro(String msg) {
        String sPlayer = msg.substring(msg.indexOf("N") + 1);
        int iPlayer = Integer.parseInt(sPlayer);

        List clientes = this.caller.getClientes();
        PrintWriter out = null;
        Iterator iterator = clientes.iterator();
        while (iterator.hasNext()) {
            out = (PrintWriter) iterator.next();
            if (out.equals(clientes.get(iPlayer))) {
                out.println(msg);
                out.flush();
            }
        }
    }

    public synchronized void guardaCampo(String sCampo) throws IOException {

        String sPlayer = sCampo.substring(sCampo.indexOf("N") + 1, sCampo.indexOf("C"));
        String sMatriz = sCampo.substring(sCampo.indexOf("C") + 1);

        Gson gson = new Gson();
        int[][] iCampo;

        iCampo = gson.fromJson(sMatriz, int[][].class);

        if (sPlayer.equals("0")) {
            mCampo0.iMatrizCampo = iCampo;
            try {
                _fileM0.delete();
                _fileM0.createNewFile();
                _readerM0 = new FileReader(_fileM0);
                _writerM0 = new FileWriter(_fileM0, true);
                _breaderM0 = new BufferedReader(_readerM0);

                _writerM0.write(sMatriz);
                _writerM0.flush();

//                while ((sMatrizReaded = _breaderM0.readLine()) != null) {
//                }
//                _breaderM0.close();

            } catch (Exception e) {
            }

        } else {
            mCampo1.iMatrizCampo = iCampo;
            try {
                _fileM1.delete();
                _fileM1.createNewFile();
                _readerM1 = new FileReader(_fileM1);
                _writerM1 = new FileWriter(_fileM1, true);
                _breaderM1 = new BufferedReader(_readerM1);

                _writerM1.write(sMatriz);
                _writerM1.flush();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public synchronized boolean ValidaTiro(int iRow, int iColumn, int iPlayer) throws IOException {
        // Valida se o tiro foi um erro ou foi um acerto.
        int iValue = 0, iLinha = 0, iColuna = 0, iPosicao = 0;
        String sValue = null;
        
        //Configurando posições para buscar dados da matriz
        if (iRow == 0) {
            iLinha = 2;
        }
        if (iRow == 1) {
            iLinha = 18;
        }
        if (iRow == 2) {
            iLinha = 34;
        }
        if (iRow == 3) {
            iLinha = 50;
        }
        if (iRow == 4) {
            iLinha = 66;
        }
        if (iRow == 5) {
            iLinha = 82;
        }
        if (iRow == 6) {
            iLinha = 98;
        }

        if (iColumn == 0) {
            iPosicao = iLinha;
        }
        if (iColumn == 1) {
            iPosicao = iLinha + 2;
        }
        if (iColumn == 2) {
            iPosicao = iLinha + 4;
        }
        if (iColumn == 3) {
            iPosicao = iLinha + 6;
        }
        if (iColumn == 4) {
            iPosicao = iLinha + 8;
        }
        if (iColumn == 5) {
            iPosicao = iLinha + 10;
        }
        if (iColumn == 6) {
            iPosicao = iLinha + 12;
        }

        if (iPlayer == 0) {

            _reader = new FileReader(_fileM1);
            _breader = new BufferedReader(_reader);

            while ((sMatrizReaded = _breader.readLine()) != null) {
                sValue = sMatrizReaded.substring(iPosicao, iPosicao + 1);
            }
            if (sValue.equals("0")) {
                return false;
            } else {
                return true;
            }
        } else {

            _reader = new FileReader(_fileM0);
            _breader = new BufferedReader(_reader);

            while ((sMatrizReaded = _breader.readLine()) != null) {
                sValue = sMatrizReaded.substring(iPosicao, iPosicao + 1);
            }

            if (sValue.equals("0")) {
                return false;
            } else {
                return true;
            }
        }
    }

    //servidor
    @Override
    public void run() {
        String message = " ";
        while (message != null && !message.equals("")) {
            try {
                message = this.input.readLine();
                //TIRO
                if (message.substring(0, 1).equals("A")) {
                    messageDispatcher(message);
                }

                //CAMPO
                if (message.substring(0, 1).equals("N")) {
                    guardaCampo(message);
                }

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    MatrizCampo mMatrizCampo = new MatrizCampo();
}
