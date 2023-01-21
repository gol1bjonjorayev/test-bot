package gol1bjon.developer.files;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gol1bjon.developer.db.DataBase;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.entity.Customer;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public interface WorkWithFiles {

    Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    String BASE_FOLDER = "src/main/resources/documents";
    File CUSTOMER_FILE = new File(BASE_FOLDER, "customer.json");
    File CANDIDATE_FILE = new File(BASE_FOLDER, "candidate.json");

    static void readCustomerList(){
        if(!CUSTOMER_FILE.exists()) return;

        try {
            List customers = GSON.fromJson(new BufferedReader(new FileReader(CUSTOMER_FILE)),
                    new TypeToken<List<Customer>>() {
                    }.getType());
            DataBase.customerList.clear();
            DataBase.customerList.addAll(customers);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void writeCustomerList(){
        try (PrintWriter writer = new PrintWriter(CUSTOMER_FILE)) {
            writer.write(GSON.toJson(DataBase.customerList));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void writeCandidateList() {
        try (PrintWriter writer = new PrintWriter(CUSTOMER_FILE)) {
            writer.write(GSON.toJson(DataBase.customerList));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void readCandidateList(){
        if(!CANDIDATE_FILE.exists()) return;

        try {
            List candidate = GSON.fromJson(new BufferedReader(new FileReader(CANDIDATE_FILE)),
                    new TypeToken<List<Candidate>>() {
                    }.getType());
            DataBase.candidateList.clear();
            DataBase.candidateList.addAll(candidate);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
