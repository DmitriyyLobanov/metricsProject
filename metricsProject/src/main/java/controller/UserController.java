package controller;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.User;
import service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final MeterRegistry meterRegistry;

    @GetMapping
    public List<User> findAll(){
        meterRegistry.counter("request to findAll").increment();
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        meterRegistry.counter("request to findById").increment();
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    @PostMapping
    public User save(@RequestBody User user){
        meterRegistry.counter("request to save").increment();
        return userService.save(user);
    }
    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id){
        meterRegistry.counter("request to update").increment();
        user.setId(id);
        return userService.save(user);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        meterRegistry.counter("request to delete").increment();
        userService.deleteById(id);
    }
}
