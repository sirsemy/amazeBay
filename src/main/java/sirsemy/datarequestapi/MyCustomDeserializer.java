package sirsemy.datarequestapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MyCustomDeserializer extends StdDeserializer<LocalDate> {

    public MyCustomDeserializer() {
        this(null);
    }

    public MyCustomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
            throws DateTimeParseException, NullPointerException, IOException {
        if (jsonparser.getText() != null && !jsonparser.getText().equals("null")) {
            List<String> dateForms = new ArrayList<>();
            dateForms.add("M/d/yyyy");
            dateForms.add("MM/d/yyyy");
            dateForms.add("M/dd/yyyy");
            dateForms.add("MM/dd/yyyy");
            String goodPattern = null;
            for (String dateForm : dateForms) {
                if (dateForm.length() == jsonparser.getText().length()
                        && dateForm.indexOf("/") == jsonparser.getText().indexOf("/"))
                    goodPattern = dateForm;
            }
            LocalDate convertedDate = null;
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(goodPattern);
                convertedDate = LocalDate.parse(jsonparser.getText(), dtf);
            } catch (DateTimeParseException ex) {
                throw new IllegalStateException("Not proper form of the date field.");
            } catch (NullPointerException ex) {
                throw ex;
            }
            return convertedDate;
        } else
            return null;
    }
}
