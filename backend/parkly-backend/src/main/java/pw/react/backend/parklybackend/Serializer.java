package pw.react.backend.parklybackend;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Serializer extends JsonSerializer {

    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        final LocalDate date = (LocalDate) o;
        final String dateString = date.format(DateTimeFormatter.ISO_DATE);
        jsonGenerator.writeString(dateString);
    }
}
