package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.scene.input.ClipboardContent;

public class Clipboard {
    private Clipboard() {
    }

    public static void addTo(String text) {
        ClipboardContent content = new ClipboardContent();
        content.putString(text);

        javafx.scene.input.Clipboard.getSystemClipboard().setContent(content);
    }
}
