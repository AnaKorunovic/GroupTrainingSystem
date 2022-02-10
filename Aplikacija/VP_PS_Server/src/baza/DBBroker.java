
package baza;

import domen.OpstiDomenskiObjekat;

import domen.Termin;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguracija.KriterijumWhere;

/**
 *
 * @author Ana
 */
public class DBBroker {

    Connection conn = null;

    public boolean uspostaviKonekciju() throws FileNotFoundException, IOException {
        Properties properties=new Properties();
        properties.load(new FileInputStream("config/dbconfig.properties"));
        String url=properties.getProperty("url");
        String username=properties.getProperty("username");
        String pass=properties.getProperty("password");
        try {
            conn = DriverManager.getConnection(url,username,pass);
            conn.setAutoCommit(false);
            System.out.println("* Uspesno uspostavljene konekcije sa bazom. *");

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("* Neuspesno uspostavljanje konekcije sa bazom. *");
            return false;
        }

        return true;
    }

    public boolean potvrdiTransakciju() {
        try {
            conn.commit();
        } catch (SQLException esql) {
            return false;
        }
        return true;
    }

    public boolean ponistiTransakciju() {
        try {
            conn.rollback();
        } catch (SQLException esql) {
            return false;
        }

        return true;
    }

    public void raskiniKonekciju() {
        close(conn, null, null);
    }

    public void close(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean UbaciNoviObjekat(OpstiDomenskiObjekat odo) {
        try {
            String upit = "INSERT INTO " + odo.vratiNazivTabele() + " VALUES (" + odo.vratiVrednostiAtributa() + ")";
            System.out.println("UPIT: "+upit);
            Statement statement = conn.createStatement();
            statement.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
         
            if(odo.autoinkrement()){
                ResultSet rs=statement.getGeneratedKeys();
                if(rs.next()){
                    odo.postaviVrednostPK(rs.getInt(1));
                }
            }
           return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    public boolean obrisiObjekat(OpstiDomenskiObjekat odo) {
        String upit = "DELETE FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslovZaWhere(KriterijumWhere.PRIMARAN_KLJUC);
        System.out.println("UPIT: "+upit);
        return izvrsiUpit(upit);
    }

    public boolean azurirajObjekat(OpstiDomenskiObjekat odo,KriterijumWhere kriterijum) {
        String upit = "UPDATE " + odo.vratiNazivTabele() + " SET " + odo.postaviVrednostiAtributa() + " WHERE " + odo.vratiUslovZaWhere(kriterijum);
        System.out.println("UPIT: "+upit);
        return izvrsiUpit(upit);
    }

    public boolean daLiPostojiObjekat(OpstiDomenskiObjekat odo,KriterijumWhere kriterijum) {
        ResultSet rs = null;
        Statement statement = null;
        String upit = "SELECT * FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslovZaWhere(kriterijum);
        System.out.println("UPIT: "+upit);
        try {
            statement = conn.prepareStatement(upit);
            rs = statement.executeQuery(upit);

            if (rs.next() == false) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(null, statement, rs);
        }
        return true;

    }

    public List<OpstiDomenskiObjekat> vratiSveObjekteSaUslovom(OpstiDomenskiObjekat odo,KriterijumWhere kriterijum) {
        ResultSet rs = null;
        Statement statement = null;
        String upit = "SELECT * FROM " + odo.vratiNazivTabele() + " WHERE " + odo.vratiUslovZaWhere(kriterijum);
        System.out.println("UPIT: "+upit);
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        try {
            statement = conn.prepareStatement(upit);
            rs = statement.executeQuery(upit);
            // signal = rs.next(); // rs.next() vraca true ako ima postoji rezultat upita.
            

            if (rs != null) {

                lista = odo.vratiListuSvih(rs);

            }
            System.out.println(lista);

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(null, statement, rs);
        }
        return lista;
    }

    public List<OpstiDomenskiObjekat> vratiSveObjekteBezUslova(OpstiDomenskiObjekat odo) {
        ResultSet rs = null;
        Statement statement = null;
        String upit = "SELECT * FROM " + odo.vratiNazivTabele();
        System.out.println("UPIT: "+upit);
        List<OpstiDomenskiObjekat> lista = new ArrayList<>();
        try {
            statement = conn.prepareStatement(upit);
            rs = statement.executeQuery(upit);
            // signal = rs.next(); // rs.next() vraca true ako ima postoji rezultat upita.
            if (rs != null) {
                lista = odo.vratiListuSvih(rs);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(null, statement, rs);
        }
        return lista;
    }

    public boolean izvrsiUpit(String upit) {
        Statement statement = null;
        boolean signal = false;
        try {
            statement = conn.createStatement();
            int rowcount = statement.executeUpdate(upit, Statement.RETURN_GENERATED_KEYS);
            if (rowcount > 0) {
                signal = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            signal = false;
        } finally {
            close(null, statement, null);
        }
        return signal;
    }

    
   
}
