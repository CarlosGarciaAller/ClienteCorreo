package logica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Email;

public class Logica {
    private static Logica INSTANCE = null;
    private ObservableList<Email> listaCorreos;

    public Logica() {
        listaCorreos = FXCollections.observableArrayList();
    }

    public static Logica getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Logica();
        }
        return INSTANCE;
    }

    public void addEmail(String subject, String from, String text){
        listaCorreos.add(new Email(subject,from,text));
    }

    public ObservableList<Email> getListaCorreos() {
        return listaCorreos;
    }

    /*private EmailTreeItem getFolders(EmailAccount emailAcount) throws MessagingException{
        EmailTreeItem treeItem = new EmailTreeItem(emailAcount.getAddress().emailAcount, null);
        Folder[] folders = emailAcount.getStore().getDefaultFolder().list;
        getFolders(folders,treeItem,emailAcount);
        return treeItem;
    }*/
    /*private cargarCarpetas(CuentaEmail c){

    }*/
}
