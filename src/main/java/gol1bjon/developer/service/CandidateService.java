package gol1bjon.developer.service;

import gol1bjon.developer.db.DataBase;
import gol1bjon.developer.entity.Candidate;
import gol1bjon.developer.files.WorkWithFiles;
//import gol1bjon.developer.files.WorkWithFiles;

import java.util.UUID;

public class CandidateService {
    public static Candidate getCandidateById(String id){
        return DataBase.candidateList.stream()
                .filter(candidate -> candidate.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Candidate addCandidate(Candidate reqCandidate){
        Candidate candidate = new Candidate(String.valueOf(UUID.randomUUID()),
                reqCandidate.getFileId(),
                reqCandidate.getFullName(),
                reqCandidate.getAge(),
                reqCandidate.getDescription());
        DataBase.candidateList.add(candidate);
        WorkWithFiles.writeCandidateList();
        return candidate;
    }
}
