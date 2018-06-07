package ru.study.gamelexicon.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.study.gamelexicon.model.Setting;
import ru.study.gamelexicon.service.SettingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/settings/")
public class SettingRestController {

    private static final Logger logger = LoggerFactory.getLogger(SettingRestController.class);

    @Autowired
    SettingService settingService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Setting>> list(){
        List<Setting> settings = this.settingService.list();

        if (settings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(settings, HttpStatus.OK);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Setting> create(@RequestBody @Valid Setting setting){
        if (setting == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.settingService.save(setting);
        logger.info("Created setting", setting);
        return new ResponseEntity<>(setting, HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Setting> read(@PathVariable("id") Long id){
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Setting setting = this.settingService.getById(id);

        if (setting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(setting, HttpStatus.OK);
    }

    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Setting> update(@RequestBody @Valid Setting setting){
        if (setting == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.settingService.update(setting);

        logger.info("Updated setting", setting);
        return new ResponseEntity<>(setting, HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Setting> delete(@PathVariable("id") Long id){
        Setting setting = this.settingService.getById(id);

        if (setting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.settingService.remove(id);

        logger.info("Removed setting", setting);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
