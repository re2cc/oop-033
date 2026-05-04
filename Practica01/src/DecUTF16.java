void main() {
    String decString = IO.readln();
    int dec = Integer.parseInt(decString);
    String val = new String(Character.toChars(dec));

    IO.println("Character: \"" + val + "\"");
}
