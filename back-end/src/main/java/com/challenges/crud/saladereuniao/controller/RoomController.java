package com.challenges.crud.saladereuniao.controller;

import com.challenges.crud.saladereuniao.exception.ResourceNotFoundException;
import com.challenges.crud.saladereuniao.model.Room;
import com.challenges.crud.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200") @RequestMapping("api/v1")
public class RoomController{

    @Autowired
    private RoomRepository _rep;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return _rep.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id)
    throws ResourceNotFoundException {
        Room room = _rep.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found for this id:: "+ id));
        return ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return _rep.save(room);
    }
    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Valid @RequestBody Room room)
    throws ResourceNotFoundException{
        Room modify = _rep.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found for this id:: " + id));
        modify.setNome(room.getNome());
        modify.setDate(room.getDate());
        modify.setStartHour(room.getStartHour());
        modify.setEndHour(room.getEndHour());
        var updated = _rep.save(modify);

        return ResponseEntity.ok().body(updated);
    }
    @DeleteMapping("/rooms/{id}")
    public Map<String, Boolean> deleteRoom(@PathVariable Long id)
            throws ResourceNotFoundException {
        Room room = _rep.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found:: "+ id));
        _rep.deleteById(id);

        Map<String , Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }
}
