package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logica.Logica;
import model.Email;
import model.EmailAccount;

import javax.mail.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TableView <Email> tablaEmail;

    @FXML
    TableColumn columnEmail = new javafx.scene.control.TableColumn("Email");

    @FXML
    TableColumn columnForm = new javafx.scene.control.TableColumn("Asunto");

    @FXML
    TableColumn columnText = new javafx.scene.control.TableColumn("Contenido");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*try{
            Properties properties = new Properties();

            String host = "pop.gmail.com";// change accordingly
            String username = "carlos.garcia.aller@gmail.com";// change accordingly
            String password = "Hyperion-967";// change accordingly

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

            for (int i = 0, n = 3; i < n; i++) {
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

        tablaEmail.setItems(Logica.getInstance().getListaCorreos());*/
    }

    public void openLogin(ActionEvent actionEvent) {
        LoadStages("../views/loginEmail.fxml");
    }

    public void loadNewCorreo(ActionEvent actionEvent) {
        LoadStages("../views/altaEmail.fxml");
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