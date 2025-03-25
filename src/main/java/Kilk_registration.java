
public class Kilk_registration {

    public static void main(String[] args) {
        String html = "";
        int position = html.indexOf("token");
        String token;
        String jt = "UxjFOf3KMxvZld41CMdtbfaV1RHO9cRxXziohiPM";
        if (position != 1) {
            token = html.substring(position + 9, position + 49);
            System.out.println(token);
            System.out.println(token.equals(jt));
        }
    }
}
