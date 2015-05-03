
package Styles;

import java.awt.Color;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Kunal
 */
public class CodeDocStyle {
             public static void addStylesToDocument(StyledDocument doc) {
        //Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");
        StyleConstants.setForeground(def, new Color(22,55,166));
        StyleConstants.setFontSize(def, 13);

        Style s = doc.addStyle("italic", def);
        StyleConstants.setItalic(s, false);

        Style infoMsg = doc.addStyle("infoMsg", def);
        StyleConstants.setForeground(infoMsg, Color.red);

        Style timeDate = doc.addStyle("timeDate", def);
        StyleConstants.setForeground(timeDate, Color.LIGHT_GRAY);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setFontSize(s, 26);
        StyleConstants.setForeground(s, Color.GRAY);

        s = doc.addStyle("button", regular);
        StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
    }
}