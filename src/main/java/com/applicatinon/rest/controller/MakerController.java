package com.applicatinon.rest.controller;

import com.applicatinon.rest.dto.MakerDTO;
import com.applicatinon.rest.entities.Maker;
import com.applicatinon.rest.service.impl.MakerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rest")
public class MakerController {

    @Autowired
    private MakerServiceImpl makerService;

    @GetMapping("/getAllMaker")
    public ResponseEntity<List<MakerDTO>> getAllMaker(){
        List<Maker> makerList = makerService.findAll();

        if (!makerList.isEmpty()){
            List<MakerDTO> response = makerList.stream()
                    .map(maker -> MakerDTO.builder()
                            .id(maker.getId())
                            .name(maker.getName())
                            .productsList(maker.getProductsList())
                            .build())
                    .toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<MakerDTO> getMakerById(@PathVariable Long id){
        Optional<Maker> maker = makerService.findById(id);
        if(maker.isPresent()) {
            Maker tmp = maker.get();
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(tmp.getId())
                    .name(tmp.getName())
                    .productsList(tmp.getProductsList())
                    .build();
            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDto) throws URISyntaxException {
        if(makerDto.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }
        //Maker maker = new Maker(null,makerDto.getName(),makerDto.getProductsList());
        makerService.save(Maker.builder()
                .name(makerDto.getName())
                .build());
        return ResponseEntity.created(new URI("/rest/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MakerDTO makerDto) throws URISyntaxException {
        Optional<Maker> tmp = makerService.findById(id);

        if(tmp.isPresent()){
            Maker maker = Maker.builder()
                    .id(tmp.get().getId())
                    .name(makerDto.getName())
                    .productsList(makerDto.getProductsList())
                    .build();
            makerService.save(maker);
            return ResponseEntity.ok("Successfully saved");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        if(id != null){
            makerService.deleteById(id);
            return ResponseEntity.ok("Delete successfully");
        }
       return ResponseEntity.notFound().build();
    }
}
