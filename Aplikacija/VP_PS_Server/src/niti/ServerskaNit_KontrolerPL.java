package niti;

import domen.Korisnik;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import transfer.KlijentskiZahtev;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ana
 */
public class ServerskaNit_KontrolerPL extends Thread{
    
    private ServerSocket serverSocket;
    List<ObradaKlijentskeNiti> klijenti;
    List<Korisnik> aktivniKorisnici;

    public ServerskaNit_KontrolerPL() throws IOException {
        serverSocket=new ServerSocket(9000);
        klijenti=new ArrayList<>();
        aktivniKorisnici=new ArrayList<>();
    }

    public List<Korisnik> getAktivniKorisnici() {
        return aktivniKorisnici;
    }
    
    
    
    @Override
    public void run() {
       
        while(!serverSocket.isClosed()){
            try {
                System.out.println("server ceka klijente...");

                Socket socket=serverSocket.accept();
                ObradaKlijentskeNiti klijent=new ObradaKlijentskeNiti(socket,this);
                klijent.start();
                klijenti.add(klijent);
                System.out.println("Server je povezan sa klijentom!");
                
            } catch (IOException ex ) {
               // ex.printStackTrace();
            }
            
        }
        
        prekidKlijentskihNiti();
        
    }
    
    public  void stopServer() throws IOException{
        serverSocket.close();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    private void prekidKlijentskihNiti(){
        for (ObradaKlijentskeNiti k : klijenti) {
            try {
                k.getSocket().close();
                klijenti.remove(k);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    

    public List<ObradaKlijentskeNiti> getKlijenti() {
        return klijenti;
    }

    void izbaciKlijenta(ObradaKlijentskeNiti k1) {
       int i=1;
        for (ObradaKlijentskeNiti k : klijenti) {
            
            try {
                if(k.equals(k1)) {
                    k.getSocket().close();
                    System.out.println("Klijent broj: "+i+" je izlogovan");
                    klijenti.remove(k);
                }i++;
            } catch (IOException ex) {
                //ex.printStackTrace();
            }
        }
      
    }
    
}
