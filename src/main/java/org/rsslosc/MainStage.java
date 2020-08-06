package org.rsslosc;

import com.sun.imageio.plugins.common.ImageUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.rsslosc.Update;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainStage implements Initializable {
    @FXML public Text words;
    @FXML public Text okk;
    @FXML public Text description;
    @FXML public AnchorPane anchor;
    @FXML public Text total;
    @FXML public ImageView imgView;
    @FXML public MenuBar title;
    @FXML public Text wordsList;
    @FXML public StackPane wordsListPane;
    @FXML public Text leftText;
    @FXML public Text rightText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setVisible(false);
//        anchor.setBackground(new Background(new BackgroundImage(imageView.getImage() ,
//                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
//                new BackgroundSize(100, 100, true, true, true, true))));
        Update update = Update.getInstance();
        try {
            update.set(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
