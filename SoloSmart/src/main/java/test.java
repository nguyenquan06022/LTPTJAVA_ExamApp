import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
    private static DateTimeFormatter df= DateTimeFormatter.ofPattern("ddMMyyyyHHmmssSSS");
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("DT"+df.format(now));
    }
}
