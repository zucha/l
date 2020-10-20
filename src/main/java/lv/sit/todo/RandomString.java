package lv.sit.todo;

public class RandomString {
    /**
     *
     * @return random value
     */
    public static String generate ()
    {
        String data = "";

        for (int i = 0; i < 10; i++) {
            String value = Character.toString((char) random(65, 91));

            data = data.concat(value);
        }

/*        for (int i = 65; i < 91; i++) {
            String value =  Character.toString((char) i);
            data = data.concat(value);
        }*/

        return data;
    }

    /**
     *
     * @param from
     * @param to
     * @return
     */
    private static int random (int from, int to)
    {
        return (int) ((Math.random() * (to - from)) + from);
    }
}
