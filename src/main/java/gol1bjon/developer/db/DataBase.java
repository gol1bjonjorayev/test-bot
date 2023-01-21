package gol1bjon.developer.db;

import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public interface DataBase {
    List<Customer> customerList =  new ArrayList<>();
    List<Candidate> candidateList =  new ArrayList<>();

}
