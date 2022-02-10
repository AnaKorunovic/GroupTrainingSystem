/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import domen.OpstiDomenskiObjekat;
import ekranske_forme.OpstaEkranskaForma;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler_KI.OpstiKKI;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Ana
 */
public class Komunikacija {
    private static Komunikacija instance;
    private Socket socket;
    
   
    private Komunikacija() throws IOException{
        Properties properties=new Properties();
        properties.load(new FileInputStream("config/serverConfig.properties"));
        String ipAdresa=properties.getProperty("IPAdresa");
        String txtport=properties.getProperty("port");
        int port=Integer.parseInt(txtport);
        //socket=new Socket("localhost", 9000);
        socket=new Socket(ipAdresa, port);
    }
    
    public static Komunikacija getInstance() throws IOException{
        if(instance==null){
            instance=new Komunikacija();
        }
        return instance;
    }
    
    
    public void posaljiZahtev(KlijentskiZahtev zahtev) throws IOException{
       ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(zahtev);
        out.flush();
       
    }
    
    public ServerskiOdgovor primiOdgovor() throws IOException, ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(socket.getInputStream());
        ServerskiOdgovor odgovor=(ServerskiOdgovor)in.readObject();
        return odgovor;
    }
}
