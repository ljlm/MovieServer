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
    public enum result{
        SUCCESS(0),
        FAILURE(1);

        private int value;

        result(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }

        public result get(){
            return this;
        }

    }


}
