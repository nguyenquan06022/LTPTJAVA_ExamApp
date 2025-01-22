import DB.CreateDB;
import Dao.*;
import Entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;

public class Runner {
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            em = CreateDB.createDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}