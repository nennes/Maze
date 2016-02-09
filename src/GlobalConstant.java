public enum GlobalConstant {
    // declare enum constants
    INPUT_WALL(     '1'),
    INPUT_PASSAGE(  '0'),
    OUTPUT_WALL(    '#'),
    OUTPUT_PASSAGE( ' '),
    OUTPUT_PATH(    'X'),
    OUTPUT_START(   'S'),
    OUTPUT_END(     'E');

    private final char content;

    GlobalConstant(char c) {
        content = c;
    }

    public char getContent(){
        return content;
    }

    public static GlobalConstant getConstant(char search_c) throws Exception{
        for (GlobalConstant gc: GlobalConstant.values()) {
            if (gc.getContent() == search_c) {
                return gc;
            }
        }
        /*
         * If we reach this point, the constant has not been found.
         * Raise an exception
         */
        throw new Exception();
    }

    public String toString() {
        return String.valueOf(content);
    }
}
