
import java.util.TreeSet;
import java.util.regex.Pattern;

public class Raul_Traça1601 {

    public static void main(String[] args) {

        String ref = "confia en les accions i no en les raons";

        int n = ref.length();
        int ll, c = 0;

        for (int i = 0; i < n; i++) {
            if ((ll = ref.charAt(i)) == ' ') {
                ref += ".";
            } else if (i % 2 == 0) {
                ref += (char) (ll + 1);
            } else {
                ref += (char) (ll + 2);
            }
        }

        ref = ref.substring(ref.length() / 2);

        while (ref.contains(".")) {
            ref = ref.replaceFirst("[.]", "" + c++);
        }
        String resultat="";
        TreeSet<Character> set = new TreeSet<Character>();
        for (int i = 0; i < ref.length(); i++) {
            if (Pattern.matches("[\\Da-z&&[^d-t]]", "" + ref.charAt(i))) {
                set.add(ref.charAt(i));
                resultat += set.add(ref.charAt(i));
            }
        }

        String solucio = "[b,c]";
        System.out.println(resultat.equals(solucio));
        System.out.println(set);
  
    }
}

