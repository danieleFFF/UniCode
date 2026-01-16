package it.unical.unicode.service;

import it.unical.unicode.dto.ExerciseCreationRequest;
import it.unical.unicode.model.Exercise;
import it.unical.unicode.model.TestCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Proxy pattern: aggiunge funzionalita caching per linguaggio/eseercizi (language '1' -> exercises 'N')
@Service
@Primary
public class ExerciseServiceProxy implements IExerciseService {
    private final IExerciseService realService;
    private final Map<String, List<Exercise>> exerciseCache = new ConcurrentHashMap<>();

    public ExerciseServiceProxy(@Qualifier("exerciseServiceImpl") IExerciseService realService) {
        this.realService = realService;
    }

    @Override
    public List<Exercise> findByLanguage(Integer idLanguage, String sortBy, String order){
        String cacheKey = createCacheKey(idLanguage, sortBy, order);

        if(exerciseCache.containsKey(cacheKey)){
            System.out.println("(Proxy) Cache HIT for language " + idLanguage + ", returning cached exercises");

            return exerciseCache.get(cacheKey);
        }

        System.out.println("Method…Cache MISS for language " + idLanguage + ", loading from database");
        List<Exercise> exercises = realService.findByLanguage(idLanguage, sortBy, order);
        exerciseCache.put(cacheKey, exercises);

        return exercises;
    }

    @Override
    public List<Exercise> findByLanguagePaged(Integer idLanguage, String sortBy, String order, int page, int size) {
        //le query con filtri non sono prese in considerazione per semplicità
        return realService.findByLanguagePaged(idLanguage, sortBy, order, page, size);
    }

    @Override
    public Exercise findById(Integer id) {
        return realService.findById(id);
    }

    @Override
    public List<TestCase> findTestsByExerciseId(Integer id) {
        return realService.findTestsByExerciseId(id);
    }

    @Override
    public List<Exercise> findAll(String sortBy, String order){
        String cacheKey = "ALL_" + sortBy + "_" + order;

        if(exerciseCache.containsKey(cacheKey)){
            System.out.println("(Proxy) Cache HIT for all exercises");

            return exerciseCache.get(cacheKey);
        }

        System.out.println("(Proxy) Cache MISS for all exercises - loading from database");
        List<Exercise> exercises = realService.findAll(sortBy, order);
        exerciseCache.put(cacheKey, exercises);

        return exercises;
    }

    @Override
    public void createExercise(ExerciseCreationRequest request){
        realService.createExercise(request);
        //pulisce cache perchè un nuovo esercizio è stato aggiunto
        clearCache();
    }

    public void invalidateCache(Integer idLanguage) {
        exerciseCache.entrySet().removeIf(entry -> entry.getKey().startsWith("LANG_" + idLanguage));
        System.out.println("(Proxy) Cache invalidated for language " + idLanguage);
    }

    public void clearCache(){
        exerciseCache.clear();
        System.out.println("(Proxy) Entire cache cleared");
    }

    private String createCacheKey(Integer idLanguage, String sortBy, String order){
        return "LANG_" + idLanguage + "_" + sortBy + "_" + order;
    }
}
