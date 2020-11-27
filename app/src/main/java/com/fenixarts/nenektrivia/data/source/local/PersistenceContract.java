package com.fenixarts.nenektrivia.data.source.local;

import android.provider.BaseColumns;

/**
 * NenekTrivia
 * Created by terry0022 on 24/01/18 - 12:21.
 */

class PersistenceContract {

    /* Inner class that defines the table contents */
    abstract static class BestPlayer implements BaseColumns {

        /**
         * id : asfdgf3rfedc
         * image : www.thisisexample.com/thephoto.png
         * username : terry
         * points : 10
         */

        static final String TABLE_NAME = "bestplayer";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_IMAGE = "image";
        static final String COLUMN_NAME_USERNAME = "username";
        static final String COLUMN_NAME_POINTS = "points";

    }

    public class Questions implements BaseColumns{

        /**
         * id : -L0MzzB1iq0ZmkSHS3xl
         * category : Geografia
         * answerbad01 : Tamazunchale
         * answerbad02 : Valles
         * answerbad03 : Tamasopo
         * answergood : Aquismon
         * question : ¿Dónde se ubica Tamul?
         */

        static final String TABLE_NAME = "questions";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_CATEGORY = "category";
        static final String COLUMN_NAME_ANSWER_BAD_01 = "answerbad01";
        static final String COLUMN_NAME_ANSWER_BAD_02 = "answerbad02";
        static final String COLUMN_NAME_ANSWER_BAD_03 = "answerbad03";
        static final String COLUMN_NAME_ANSWER_GOOD = "answergood";
        static final String COLUMN_NAME_QUESTION = "question";


    }
}
