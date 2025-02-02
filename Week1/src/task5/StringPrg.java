package task5;

public class StringPrg {
	public String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public int countOccurrences(String text, String sub) {
        int count = 0, index = 0;
        while ((index = text.indexOf(sub, index)) != -1) {
            count++;
            index += sub.length();
        }
        return count;
    }

    public String splitAndCapitalize(String str) {
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        StringPrg processor = new StringPrg();

        String input = "Welcome to java program";
        System.out.println("Reversed String: " + processor.reverseString(input));

        String text = "Hello lovely";
        String sub = "lo";
        System.out.println("Occurrences of '" + sub + "': " + processor.countOccurrences(text, sub));

        System.out.println("Capitalized String: " + processor.splitAndCapitalize(input));
    }

}
