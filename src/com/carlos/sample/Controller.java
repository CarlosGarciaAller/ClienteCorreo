package com.carlos.sample;

import com.carlos.Reloj;
import com.sun.mail.util.MailSSLSocketFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.carlos.logica.Logica;
import com.carlos.model.Email;
import com.carlos.model.EmailAccount;

import javax.mail.*;
import java.io.*;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    TableView <Email> tablaEmail;

    @FXML
    TableColumn columnEmail = new javafx.scene.control.TableColumn("Email");

    @FXML
    TableColumn columnForm = new javafx.scene.control.TableColumn("Asunto");

    @FXML
    TableColumn columnText = new javafx.scene.control.TableColumn("Contenido");

    @FXML
    TreeView emailFolders = new TreeView();

    @FXML
    Reloj reloj = new Reloj();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            Calendar calendar =  Calendar.getInstance();
            reloj.setHoras(calendar.get(Calendar.HOUR_OF_DAY));
            reloj.setSegundos(calendar.get(Calendar.SECOND));
            reloj.setMinutos(calendar.get(Calendar.MINUTE));
            reloj.setCalendario(calendar);
            reloj.mostrarReloj();
            reloj.setFont(new Font(24));
            Properties properties = new Properties();

            String username = "carlos.garcia.aller@gmail.com";// change accordingly
            String password = "Hyperion-967";// change accordingly

            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.ssl.enable", "true");
            properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.pop3.socketFactory.port", "995");
            properties.put("mail.pop3.socketFactory.fallback", "false");
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            properties.put("mail.smtp.port", "587"); //TLS Port
            properties.put("mail.smtp.auth", "true"); //enable authentication
            properties.put("mail.imap.ssl.enable", "true");
            properties.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            properties.setProperty("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);

            MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
            socketFactory.setTrustAllHosts(true);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect("pop.gmail.com", username, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            for (int i = 0, n = 3; i < n; i++) {
                Message message = messages[i];
                //Multipart mp = (Multipart) message.getContent();
                //BodyPart bp = mp.getBodyPart(0);
                Logica.getInstance().addEmail(message.getFrom()[0].toString(),message.getSubject(),message.getContent().toString());
            }

            //TREEVIEW CARPETAS

            properties.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(properties, null);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);
            System.out.println(store);
            TreeItem root = new TreeItem(username);
            Folder[] f = store.getDefaultFolder().list();
            for(Folder fd:f)
                root.getChildren().add(new TreeItem(fd.getName()));
                //System.out.println(">> "+fd.getName());
            emailFolders.setRoot(root);
            ////

            //close the store and folder objects


            emailFolder.close(false);
            store.close();
        }
        catch (IOException | MessagingException | GeneralSecurityException e){
            e.printStackTrace();
        }

        columnEmail.setCellValueFactory(new PropertyValueFactory<Message, String>("Email"));
        columnForm.setCellValueFactory(new PropertyValueFactory<Message, String>("Asunto"));

        tablaEmail.setItems(Logica.getInstance().getListaCorreos());


    }

    public void loadEmail(String email, String pass){
        try{
            Properties properties = new Properties();

            String host = "pop.gmail.com";// change accordingly
            String username = email;// change accordingly
            String password = pass;// change accordingly

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, username, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();

            for (int i = 0, n = 5; i < n; i++) {
                Message message = messages[i];
                Multipart mp = (Multipart) message.getContent();
                BodyPart bp = mp.getBodyPart(0);
                Logica.getInstance().addEmail(message.getFrom()[0].toString(),message.getSubject(),bp.getContent().toString());
            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();
        }
        catch (IOException | MessagingException e){
            e.printStackTrace();
        }

        columnEmail.setCellValueFactory(new PropertyValueFactory<Message, String>("Email"));
        columnForm.setCellValueFactory(new PropertyValueFactory<Message, String>("Asunto"));
        columnText.setCellValueFactory(new PropertyValueFactory<Message, String>("Contenido"));

        tablaEmail.setItems(Logica.getInstance().getListaCorreos());
    }

    public void openLogin(ActionEvent actionEvent) {
        LoadStages("../com.carlos.views/loginEmail.fxml");
    }

    @FXML
    private Button altaEmail;

    @FXML
    public void loadNewCorreo(ActionEvent actionEvent) throws IOException {
        LoadStages("../com.carlos.views/altaEmail.fxml");
    }

    @FXML
    private TextField newEmail;

    @FXML
    private PasswordField newPass;

    public void altaCorreo(ActionEvent actionEvent) throws IOException {
        List<EmailAccount> lista = new ArrayList<EmailAccount>();
        String email = newEmail.getText();
        String pass = newPass.getText();
        lista.add(new EmailAccount(email,pass));
        File file = new File("listaEmail.txt");
        FileWriter fw = new FileWriter(file);
        if(!file.exists()){
            fw =  new FileWriter("listaEmail.txt",true);
        }
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(email+","+pass+"\n");
        bfw.close();
        fw.close();

        Stage stage = (Stage) altaEmail.getScene().getWindow();
        stage.close();

        loadEmail(email,pass);
    }

    private void LoadStages(String fxml){
        try{
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}