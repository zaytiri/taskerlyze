package personal.zaytiri.taskerlyze.ui.views.popups.interfaces;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import personal.zaytiri.taskerlyze.ui.logic.globals.Configuration;
import personal.zaytiri.taskerlyze.ui.logic.uifuncionality.Result;

import java.util.Map;

public abstract class Dialog<T> extends AnchorPane {
    protected final MFXGenericDialog dialogContent;
    private final Stage primaryStage;
    protected Result<T> result = new Result<>();
    protected int id;
    private MFXStageDialog dialog;

    protected Dialog(String dialogTitle) {
        this.primaryStage = Configuration.getInstance().getPrimaryStage();

        this.dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .get();

        dialogContent.getStylesheets().add("/css/dialogs.css");
        dialogContent.setHeaderText(dialogTitle);

        dialogContent.setMaxWidth(250);
    }

    public Result<T> getResult() {
        return this.result;
    }

    public void setDialogContentText(String text) {
        dialogContent.setContentText(text);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void showDialog() {
        setOptionsBeforeShow();
        show();
    }

    protected void addDialogAction(Node node, EventHandler<MouseEvent> event) {
        dialogContent.addActions(
                Map.entry(node, event));
    }

    protected void addDialogContentStyle(String styleClass) {
        dialogContent.getStyleClass().add(styleClass);
    }

    protected void closeDialog() {
        dialog.close();
    }

    protected void setDialogContentNode(Node node) {
        dialogContent.setContent(node);
    }

    protected abstract void setOptionsBeforeShow();

    protected void show() {
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(this.primaryStage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("title")
                .setOwnerNode(Configuration.getInstance().getMainPane())
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialog.setCenterInOwnerNode(true);
        dialog.showDialog();
    }
}
