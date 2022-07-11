package com.example.CRUDApp.Controller;

import com.example.CRUDApp.Model.Tutorial;
import com.example.CRUDApp.Repository.TutorialRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TutorialController {
  @Autowired
  TutorialRepository tutorialRepository;

  @GetMapping("/tutorial")
  public ResponseEntity<List<Tutorial>> getAllTutorials(String title){
  try{
    List<Tutorial> tutorials = new ArrayList<Tutorial>();
      tutorialRepository.findAll().forEach(tutorials::add);
    if (tutorials.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(tutorials, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  }
  @GetMapping("/tutorial/{id}")
  public ResponseEntity<Tutorial> getTutorialById(@PathVariable String id)
  {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/tutorial")
  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial)
  {
    try {
      Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));
      return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/tutorial/{id}")
  public ResponseEntity<Tutorial> updateTutorial(@PathVariable String id, @RequestBody Tutorial tutorial)
  {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);
    if (tutorialData.isPresent()) {
      Tutorial _tutorial = tutorialData.get();
      _tutorial.setTitle(tutorial.getTitle());
      _tutorial.setDescription(tutorial.getDescription());
      _tutorial.setPublished(tutorial.isPublished());
      return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
  @DeleteMapping("/tutorial")
  public ResponseEntity<HttpStatus> deleteTutorials(){
    try {
      tutorialRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @DeleteMapping("/tutorial/{id}")
  public ResponseEntity<HttpStatus> deleteTutorialById(@PathVariable String id){
    try {
      tutorialRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }
  @GetMapping("/tutorial/published")
  public ResponseEntity<List<Tutorial>> getAllPublished(){
    try {
      List<Tutorial> tutorials = tutorialRepository.findByPublished(true);
      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
