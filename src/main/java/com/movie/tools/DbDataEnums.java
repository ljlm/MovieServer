package com.movie.tools;

/**
 * Created by lionelm on 1/14/2017.
 */
public class DbDataEnums {

    public enum Categories{
        CRIME(1),
        ADVENTURE(2),
        ACCION(3),
        DRAMA(4),
        DOCUMENTERY(5),
        ANIMATION(6),
        COMEDY(7),
        THRILLER(8),
        HORROR(9);

        private int value;

        Categories(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public Categories get(){
            return this;
        }

    }
    public static final int HORROR = 0;
    public static final int CRIME =1;
    public static final int ADVENTURE = 2;
    public static final int ACCION = 3;
    public static final int DRAMA = 4;
    public static final int DOCUMENTERY = 5;
    public static final int ANIMATION = 6;
    public static final int COMEDY = 7;
    public static final int THRILLER = 8;


}
