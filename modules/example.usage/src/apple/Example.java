package apple;

import example.ann.GenerateFriend;

@GenerateFriend(name = "bob")
public class Example {
    public static void main(String[] args) throws Exception {
        System.out.println(Class.forName("apple.bob"));
    }
}
