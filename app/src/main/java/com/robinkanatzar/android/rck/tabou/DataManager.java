package com.robinkanatzar.android.rck.tabou;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DataManager {

    // ---------------------------------------------------------------------------------------------
    // Declarations for table
    // ---------------------------------------------------------------------------------------------
    private SQLiteDatabase db;
    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_FR_WORD = "fr_word";
    public static final String TABLE_ROW_TABOO_1 = "taboo_word_1";
    public static final String TABLE_ROW_TABOO_2 = "taboo_word_2";
    public static final String TABLE_ROW_TABOO_3 = "taboo_word_3";
    public static final String TABLE_ROW_TABOO_4 = "taboo_word_4";
    public static final String TABLE_ROW_TABOO_5 = "taboo_word_5";
    private static final String DB_NAME = "tabou_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_N_AND_A = "tabou_db";

    // ---------------------------------------------------------------------------------------------
    // Constructor for data manager
    // ---------------------------------------------------------------------------------------------
    public DataManager(Context context) {
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
        addDefaultRows();
    }

    // ---------------------------------------------------------------------------------------------
    // Method to get a new word from the db and return a cursor
    // ---------------------------------------------------------------------------------------------
    public Cursor getNewWord() {

        // -----------------------------------------------------------------------------------------
        // Find the number of rows that are currently in the table, save as int
        // -----------------------------------------------------------------------------------------
        String findNumRows = "SELECT COUNT(*) FROM " + TABLE_N_AND_A + ";";
        Cursor c = db.rawQuery(findNumRows, null);
        c.moveToFirst();
        int numRows = c.getInt(0);

        // -----------------------------------------------------------------------------------------
        // Produce a random number (to use to pick a random row)
        // -----------------------------------------------------------------------------------------
        Random randomGenerator = new Random();
        int randomNumber = randomGenerator.nextInt(numRows);

        // -----------------------------------------------------------------------------------------
        // Select the entire table into a cursor
        // -----------------------------------------------------------------------------------------
        String selectAll = "SELECT * FROM " + TABLE_N_AND_A + ";";
        Cursor c2 = db.rawQuery(selectAll, null);

        // -----------------------------------------------------------------------------------------
        // Set default values for string before reading cursor
        // -----------------------------------------------------------------------------------------
        String c1 = "";
        String C2 = "";
        String c3 = "";
        String c4 = "";
        String c5 = "";
        String c6 = "";

        // -----------------------------------------------------------------------------------------
        // read cursor until counter equals the random number
        // -----------------------------------------------------------------------------------------
        int counter = 0;
        while (c2.moveToNext()){
            c1 = c2.getString(1);
            C2 = c2.getString(2);
            c3 = c2.getString(3);
            c4 = c2.getString(4);
            c5 = c2.getString(5);
            c6 = c2.getString(6);
            if (counter == randomNumber) {
                break;
            }
            counter = counter + 1;
        }
        return c2;
    }

    // ---------------------------------------------------------------------------------------------
    // Method that adds original data to the database
    // ---------------------------------------------------------------------------------------------
    public void addDefaultRows() {

        // -----------------------------------------------------------------------------------------
        // Delete all rows from the table if it has any
        // -----------------------------------------------------------------------------------------
        String deleteRows = "DELETE FROM " + TABLE_N_AND_A + ";";
        db.execSQL(deleteRows);

        // -----------------------------------------------------------------------------------------
        // Declare string SQL query to add 100 rows of data
        // -----------------------------------------------------------------------------------------
        String fillTableQueryString =
                "INSERT INTO "
                        + TABLE_N_AND_A
                        + " ("
                        + TABLE_ROW_FR_WORD
                        + ","
                        + TABLE_ROW_TABOO_1
                        + ","
                        + TABLE_ROW_TABOO_2
                        + ","
                        + TABLE_ROW_TABOO_3
                        + ","
                        + TABLE_ROW_TABOO_4
                        + ","
                        + TABLE_ROW_TABOO_5
                        + ") VALUES ('petit','grand','minon','enfant','gross','bebe'),"
                        + "('grand','petit','gross','gigantesque','brute','mini-géant'),"
                        + "('femme','homme','fille','mere','soeur','féminin'),"
                        + "('deux','un','trois','nombre','compter','les maths'),"
                        + "('monde','terre','pays','terre','mer','des nuages'),"
                        + "('ami','copain','joue','partenaire','amusement','parler'),"
                        + "('fille','garcon','femme','petit','famille','souer'),"
                        + "('mal','bien','malade','mauvais','bon','terrible'),"
                        + "('bien','mal','bon','agréable','merviellue','simpa'),"
                        + "('garçon','fille','petit','homme','des sports','eleve'),"
                        + "('pere','mere','famille','grand-pere','mari','fils'),"
                        + "('mere','pere','soeur','grand-mere','mari','fille'),"
                        + "('soeur','frer','fille','fils','mere','mari'),"
                        + "('chat','chaton','animal de compagnie','cataire','les griffes','oiseaux'),"
                        + "('chien','puppy','animal de compagnie','bal','laisse','jouer chercher'),"
                        + "('bonjour','au revoir','salut','salutation','reunion','plaisanterie'),"
                        + "('au revoir','salut','a plus','a bientot','a demain','partir'),"
                        + "('maintenant','plus tard','ici','heure','temps','minute'),"
                        + "('dieu','ciel','des nuages','église','religion','roi'),"
                        + "('vrai','faux','menteur','confirmé','vérifié','fait'),"
                        + "('avant','apres','temps','tot','précéder','ordre des événements'),"
                        + "('apres','avant','temps','tard','suivre','ordre des événements'),"
                        + "('monsiur','madame','formel','pere','titre','salutation'),"
                        + "('madame','monsiur','formel','mere','madmoiselle','titre'),"
                        + "('bebe','enfant','jou','dormir','mere','petit'),"
                        + "('enfant','bebe','petit','jou','fille','garcon'),"
                        + "('heure','minute','seconde','horloge','montre','minuteur'),"
                        + "('beau','belle','mouche','mignon','jolie','plaisant'),"
                        + "('maison','apartement','toit','porte','salon','cour'),"
                        + "('nuit','jour','soir','noir','le coucher du soleil','dormir'),"
                        + "('nom','prenom','appel','bonjour','introduction','nom tag'),"
                        + "('peur','effrayant','horreur','foncé','pitre','content'),"
                        + "('probleme','solution','problème','bloc de route','faux','droite'),"
                        + "('argent','banc','factures','pièces de monnaie','portefeuille','compte bancaire'),"
                        + "('main','bras','pied','corps','des doigts','bague'),"
                        + "('vite','mort','mourir','née','bebe','age'),"
                        + "('tete','visage','cheveux','cou','épaules','corps'),"
                        + "('raison','faux','déraisonnable','argument','discussion','grain de raisin'),"
                        + "('manger','repas','nouriture','boir','dejuner','goute'),"
                        + "('nouveau','age',' vieulle','jeune','frais','récent'),"
                        + "('voiture','rue','conduire','course','rue','autoroute'),"
                        + "('demain','aujourd''hui','hier','semaine','jour','prochain'),"
                        + "('fou','délirante','psychotique','tete','sain','ordinaire'),"
                        + "('hier','demain','aujourd''hui','jour','semaine','maintenant'),"
                        + "('idee','pensée','inspiration','la créativité','ampoule','original'),"
                        + "('famille','mere','frere','soeur','pere','enfant de mêmes parents'),"
                        + "('marcher','courir','jambe','pied','chaussures','course'),"
                        + "('chance','malchanceux','jeux d''argent','carte','pari','jetons de poker'),"
                        + "('aujourd''hui','hier','demain','jour','maintenant','plus tard'),"
                        + "('tard','tot','soir','attendre','montre','temps'),"
                        + "('tot','tard','bientot','attendre','matin','horloge'),"
                        + "('histoire','livre','récit','page','imprimé','mots'),"
                        + "('minute','heure','horloge','montre','seconde','tard'),"
                        + "('boire','eau','verre','tasse','bouteille','manger'),"
                        + "('porte','maison','ouvre','ferme','poignée de porte','frapper'),"
                        + "('annee','mois','semaine','jour','365','année bissextile'),"
                        + "('mois','jour','semaine','30','12','annee'),"
                        + "('semaine','jour','sept','quatre','mois','annee'),"
                        + "('pret','improvisé','préparer','tard','tot','jour'),"
                        + "('ecrire','page','stylo','crayon','livre','lettre'),"
                        + "('eau','verre','bouteille','boir','pleut','des nuages'),"
                        + "('longtemps','bientot','récemment','heure','annee','temps'),"
                        + "('acheter','shopping','centre commercial','argent','vetements','échange'),"
                        + "('chambre','lit','dormir','maison','couverture','oreillers'),"
                        + "('lire','page','imprimé','mots','histoire','récit'),"
                        + "('mot','lettre','livre','phrase','parler','taper'),"
                        + "('matin','jour','nuit','bonjour','café','petit dejuner'),"
                        + "('soir','nuit','matin','le coucher du soleil','bonsoir','apres-midi'),"
                        + "('mari','marriage','femme','homme','camarade','petit ami'),"
                        + "('train','transport public','voies ferrées','corne','chemin de fer','billet'),"
                        + "('bus','voiture','rue','billet','transport public','arrêt'),"
                        + "('mauvais','bien','mal','méchant','terrible','perturbateur'),"
                        + "('corps','bras','jambe','torse','tout','pied'),"
                        + "('heureux','triste','rire','sourire','amusement','aise'),"
                        + "('triste','hereux','larmes','rire','la tragédie','décès'),"
                        + "('feu','fuelle','sain','ordinaire','anormal','bizarre'),"
                        + "('noir','nuit','colour','blanc','pâle','gris'),"
                        + "('blanc','noir','grey','pâle','lumière','colour'),"
                        + "('rouge','colour','rose','blanc','rose','lèvres'),"
                        + "('pied','les orteils','main','jambe','football','chaussures'),"
                        + "('photo','camera','portable','pellicule','image','cadre'),"
                        + "('ecole','élèves','professeur','instructeur','bureau','crayon'),"
                        + "('pays','France','Etas Unis','nation','drapeau','Allemagne'),"
                        + "('jeu','ballon','bal','cartes','football','rugby'),"
                        + "('lit','chambre','couverture','oreillers','dormir','nuit'),"
                        + "('roi','reine','royaume','château','chevalier','couronne'),"
                        + "('calme','somnolent','anxieux','silencieux','bruyant','tranquille'),"
                        + "('musique','instrument','sonner','piano','voix','notes'),"
                        + "('conduire','voiture','rue','feu de circulation','pédale d''accélérateur','pédale de frein'),"
                        + "('café','tasse','caféine','matin','lait','espresso'),"
                        + "('baguette','pain','fromage','boulangerie','burr','goute'),"
                        + "('espresso','café','lait','sucre','matin','caféine'),"
                        + "('facile','dificile','simple','vite','ardu','enfant'),"
                        + "('copain','ami','enfant de mêmes parents','coéquipier','jou','rire'),"
                        + "('malade','bien','maladie','fatigue','température','tousser'),"
                        + "('rue','nationale','voiture','feu de circulation','autoroute','scooter'),"
                        + "('lettre','stylo','papier','note','message','email'),"
                        + "('fete','birthday','anniversary','gâteau','bougies','habit'),"
                        + "('bras','jambe','main','coude','deux','des doigts'),"
                        + "('visage','tete','corps','nez','yeux','épaules');";

        // -----------------------------------------------------------------------------------------
        // Execute query above that adds 100 rows of data
        // -----------------------------------------------------------------------------------------
        db.execSQL(fillTableQueryString);
    }

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        // -----------------------------------------------------------------------------------------
        // on Create for creation of db
        // -----------------------------------------------------------------------------------------
        @Override
        public void onCreate(SQLiteDatabase db) {

            // -------------------------------------------------------------------------------------
            // Declare string SQL query for creating the table and column restrictions
            // -------------------------------------------------------------------------------------
            String newTableQueryString = "create table "
                    + TABLE_N_AND_A + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_FR_WORD
                    + " text not null,"
                    + TABLE_ROW_TABOO_1
                    + " text not null,"
                    + TABLE_ROW_TABOO_2
                    + " text not null,"
                    + TABLE_ROW_TABOO_3
                    + " text not null,"
                    + TABLE_ROW_TABOO_4
                    + " text not null,"
                    + TABLE_ROW_TABOO_5
                    + " text not null);";

            // -------------------------------------------------------------------------------------
            // Execute the SQL to create the table
            // -------------------------------------------------------------------------------------
            db.execSQL(newTableQueryString);
        }

        // -----------------------------------------------------------------------------------------
        // on Upgrade method for the db
        // -----------------------------------------------------------------------------------------
        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {
            // don't forget to update the onCreate method and the db version
        }
    }
}
