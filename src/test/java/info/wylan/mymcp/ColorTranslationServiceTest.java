package info.wylan.mymcp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ColorTranslationServiceTest {

    @Autowired
    ColorTranslationService service = new ColorTranslationService();

    @Test
    void getWellknownColors() {
        // Arrange
        int initialSize = service.getWellknownColors().size();


        // Act
        List<HexColor> colors = service.getWellknownColors();

        // Assert
        assertNotNull(colors, "Farbliste sollte nicht null sein");
        assertEquals(9, colors.size(), "Es sollten 9 vordefinierte Farben existieren");

        // Überprüfe spezifische Farben
        assertTrue(colors.contains(new HexColor("schwarz", "#000000")), "Schwarz sollte enthalten sein");
        assertTrue(colors.contains(new HexColor("rot", "#FF0000")), "Rot sollte enthalten sein");
        assertTrue(colors.contains(new HexColor("grün", "#00FF00")), "Grün sollte enthalten sein");
        assertTrue(colors.contains(new HexColor("blau", "#0000FF")), "Blau sollte enthalten sein");

        // Überprüfe, dass keine Duplikate existieren
        assertEquals(initialSize, colors.stream().distinct().count(),
                "Liste sollte keine Duplikate enthalten");

        // Überprüfe das Format aller Hex-Werte
        assertTrue(colors.stream()
                        .allMatch(color -> color.hexValue().matches("^#[0-9A-Fa-f]{6}$")),
                "Alle Hex-Werte sollten dem Format #RRGGBB entsprechen");
    }

    @Test
    void getColorHex() {
        // Act & Assert

        // 1. Test: Existierende Farbe (Standardfall)
        HexColor rot = service.getColorHex("rot");
        assertEquals("rot", rot.colorName(), "Farbname sollte 'rot' sein");
        assertEquals("#FF0000", rot.hexValue(), "Hex-Wert für rot sollte #FF0000 sein");

        // 2. Test: Groß-/Kleinschreibung ignorieren
        HexColor blau1 = service.getColorHex("BLAU");
        HexColor blau2 = service.getColorHex("blau");
        HexColor blau3 = service.getColorHex("BlAu");
        assertEquals(blau1, blau2, "Groß-/Kleinschreibung sollte ignoriert werden");
        assertEquals(blau2, blau3, "Groß-/Kleinschreibung sollte ignoriert werden");
        assertEquals("#0000FF", blau1.hexValue(), "Hex-Wert für blau sollte #0000FF sein");

        // 3. Test: Nicht existierende Farbe sollte DEFAULT_COLOR zurückgeben
        HexColor nichtExistierendeFarbe = service.getColorHex("nichtExistierendeFarbe");
        assertEquals("schwarz", nichtExistierendeFarbe.colorName(),
                "Nicht existierende Farbe sollte DEFAULT_COLOR (schwarz) zurückgeben");
        assertEquals("#000000", nichtExistierendeFarbe.hexValue(),
                "Nicht existierende Farbe sollte DEFAULT_COLOR (#000000) zurückgeben");

        // 4. Test: null-Eingabe sollte DEFAULT_COLOR zurückgeben
        HexColor nullFarbe = service.getColorHex(null);
        assertEquals("schwarz", nullFarbe.colorName(),
                "Null-Eingabe sollte DEFAULT_COLOR (schwarz) zurückgeben");
        assertEquals("#000000", nullFarbe.hexValue(),
                "Null-Eingabe sollte DEFAULT_COLOR (#000000) zurückgeben");

        // 5. Test: Sonderzeichen in Farbnamen
        HexColor gruen = service.getColorHex("grün");
        assertEquals("grün", gruen.colorName(), "Umlaute sollten korrekt verarbeitet werden");
        assertEquals("#00FF00", gruen.hexValue(), "Hex-Wert für grün sollte #00FF00 sein");
    }

    @Test
    void addColor() {
        // Arrange
        int initialSize = service.getWellknownColors().size();

        // Act & Assert

        // 1. Test: Erfolgreiche Hinzufügung einer neuen Farbe
        boolean result1 = service.addColor("türkis", "#00CED1");
        assertTrue(result1, "Hinzufügen einer gültigen Farbe sollte erfolgreich sein");
        assertEquals(initialSize + 1, service.getWellknownColors().size(),
                "Liste sollte um eine Farbe gewachsen sein");
        HexColor addedColor = service.getColorHex("türkis");
        assertEquals("türkis", addedColor.colorName(), "Neue Farbe sollte auffindbar sein");
        assertEquals("#00CED1", addedColor.hexValue(), "Hex-Wert sollte korrekt gespeichert sein");

        // 2. Test: Ungültige Hex-Werte
        assertAll(
                () -> assertFalse(service.addColor("ungültig1", "000000"),
                        "Hex-Wert ohne # sollte abgelehnt werden"),
                () -> assertFalse(service.addColor("ungültig2", "#00000"),
                        "Zu kurzer Hex-Wert sollte abgelehnt werden"),
                () -> assertFalse(service.addColor("ungültig3", "#0000000"),
                        "Zu langer Hex-Wert sollte abgelehnt werden"),
                () -> assertFalse(service.addColor("ungültig4", "#GGGGGG"),
                        "Ungültige Zeichen sollten abgelehnt werden"),
                () -> assertFalse(service.addColor("ungültig5", null),
                        "Null Hex-Wert sollte abgelehnt werden")
        );

        // Überprüfe, dass keine ungültigen Farben hinzugefügt wurden
        assertEquals(initialSize + 1, service.getWellknownColors().size(),
                "Ungültige Farben sollten nicht hinzugefügt worden sein");

        // 3. Test: Groß-/Kleinschreibung im Hex-Wert
        assertTrue(service.addColor("hellblau", "#00ffFF"),
                "Hex-Wert mit Kleinbuchstaben sollte akzeptiert werden");
        HexColor hellblau = service.getColorHex("hellblau");
        assertEquals("#00ffFF", hellblau.hexValue(),
                "Hex-Wert sollte in Originalschreibweise gespeichert werden");

        // 4. Test: Leerer Farbname
        assertTrue(service.addColor("", "#CCCCCC"),
                "Leerer Farbname sollte akzeptiert werden");
        HexColor emptyName = service.getColorHex("");
        assertEquals("#CCCCCC", emptyName.hexValue(),
                "Farbe mit leerem Namen sollte auffindbar sein");
    }
}
